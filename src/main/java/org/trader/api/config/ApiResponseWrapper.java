package org.trader.api.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter; // <-- Добавить импорт
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.trader.api.dto.ApiResponse;

@ControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object>
{
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        // Оборачиваем только, если:
        // 1. Возвращаемый тип НЕ ApiResponse (чтобы избежать двойного оборачивания)
        boolean isNotApiResponse = !returnType.getParameterType().equals(ApiResponse.class);

        // 2. Метод НЕ помечен @NoWrap
        boolean hasNoWrap = returnType.hasMethodAnnotation(NoWrap.class);

        // 3. Конвертер НЕ StringHttpMessageConverter (чтобы не оборачивать чистый текст/HTML)
        boolean isStringConverter = converterType.equals(StringHttpMessageConverter.class);

        // Вернуть true, только если ВСЕ три условия для НЕ-исключения соблюдены.
        return isNotApiResponse && !hasNoWrap && !isStringConverter;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // Этот код выполнится, только если supports() вернул true
        return ApiResponse.ok(body);
    }
}
