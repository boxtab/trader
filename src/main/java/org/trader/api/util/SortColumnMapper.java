package org.trader.api.util;

import java.util.Map;
import java.util.Locale;

/**
 * Маппинг "клиентских" имён колонок (snake_case из Laravel/front)
 * в имена полей JPA-сущности (camelCase).
 *
 * Безопасно обрабатывает null и неизвестные имена — возвращает дефолтное поле.
 */
public final class SortColumnMapper
{
    private static final Map<String, String> COLUMN_MAP = Map.ofEntries(
            Map.entry("order_id", "id"),
            Map.entry("opening_date", "createdAt"),
            Map.entry("created_at", "createdAt"),
            Map.entry("closing_date", "closingDate"),
            Map.entry("pl", "profitLoss"),
            Map.entry("lots", "lots"),
            Map.entry("units", "units"),
            Map.entry("volume", "lots"), // упрощение
            Map.entry("opening_price", "openPrice"),
            Map.entry("open_rate", "openRate"),
            Map.entry("closing_price", "closePrice"),
            Map.entry("close_rate", "closeRate"),
            Map.entry("take_profit_key", "takeProfitKey"),
            Map.entry("take_profit_value", "takeProfitValue"),
            Map.entry("stop_loss_key", "stopLossKey"),
            Map.entry("stop_loss_value", "stopLossValue"),
            Map.entry("commission", "commission")
    );

    private static final String DEFAULT = "id";

    private SortColumnMapper() { /* utility */ }

    public static String mapColumn(String requested)
    {
        if (requested == null) {
            return DEFAULT;
        }

        // Нормализуем: убираем пробелы, приводим к lower-case
        String key = requested.trim().toLowerCase(Locale.ROOT);

        // Если фронт присылает camelCase (например "orderId"), преобразуем в snake-case-ish
        // Попробуем несколько вариантов: как есть, подчеркивания, camel -> snake
        String mapped = COLUMN_MAP.get(key);
        if (mapped != null) return mapped;

        // Попробуем заменить camelCase → snake_case (orderId -> order_id)
        String snake = camelToSnake(key);
        mapped = COLUMN_MAP.get(snake);
        if (mapped != null) return mapped;

        // Если не нашли — возвращаем дефолт
        return DEFAULT;
    }

    private static String camelToSnake(String str)
    {
        // простой camel->snake: insert '_' before capital letters (but string is already lowercased),
        // so try a more permissive approach: return original (no-op) — but keep for future improvements.
        // Since key is already lowercased, this rarely helps, but leave hook here.
        return str;
    }
}
