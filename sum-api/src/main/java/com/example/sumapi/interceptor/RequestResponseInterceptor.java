package com.example.sumapi.interceptor;

import com.example.sumapi.model.SumMessage;
import com.example.sumapi.service.SumPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RequestResponseInterceptor implements HandlerInterceptor {

    private final SumPublisher publisher;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            throw new IllegalStateException("Request must be wrapped with ContentCachingRequestWrapper");
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        String requestBody = getRequestBody(cachingRequest);
        String responseBody = getResponseBody(cachingResponse);
        LocalDateTime requestDate = LocalDateTime.now();
        int statusCode = response.getStatus();
        SumMessage message = new SumMessage(
                requestBody,
                responseBody,
                request.getRequestURI(),
                requestDate,
                statusCode
        );
        publisher.send(message);
        cachingResponse.copyBodyToResponse();
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        if (buf.length == 0) {
            return "";
        }
        return new String(buf, StandardCharsets.UTF_8);
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] buf = response.getContentAsByteArray();
        if (buf.length == 0) {
            return "";
        }
        return new String(buf, StandardCharsets.UTF_8);
    }
}
