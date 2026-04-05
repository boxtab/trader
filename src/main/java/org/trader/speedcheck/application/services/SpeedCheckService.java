package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Service;
import org.trader.speedcheck.domain.model.ServerTarget;
import org.trader.speedcheck.domain.model.SpeedCheckResult;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
public class SpeedCheckService
{
    private final ServerListProvider serverListProvider;
    private final ServerChecker serverChecker;
    private final ExecutorService executor = Executors.newFixedThreadPool(64);

    public SpeedCheckService( ServerListProvider serverListProvider, ServerChecker serverChecker )
    {
        this.serverListProvider = serverListProvider;
        this.serverChecker      = serverChecker;
    }

    public List<SpeedCheckResult> getResults()
    {
        // 1. Получаем поток серверов
        Stream<ServerTarget> serverStream = serverListProvider.getServers().stream();

        // 2. Преобразуем каждый сервер в асинхронную задачу проверки
        Stream<CompletableFuture<SpeedCheckResult>> futureStream = serverStream
                .map( this::createCheckTask );

        // 3. Собираем задачи в список
        List<CompletableFuture<SpeedCheckResult>> futures = futureStream.toList();

        // 4. Ожидаем завершения всех задач
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture[0] )
        );
        allTasks.join();

        // 5. Преобразуем завершенные задачи в результаты
        return futures.stream()
                .map( CompletableFuture::join )
                .toList();
    }

    private CompletableFuture<SpeedCheckResult> createCheckTask( ServerTarget serverTarget )
    {
        return CompletableFuture.supplyAsync(
                () -> serverChecker.check( serverTarget ),
                executor
        );
    }
}
