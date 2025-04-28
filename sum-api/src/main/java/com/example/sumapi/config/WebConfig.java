package com.example.sumapi.config;

import com.example.sumapi.interceptor.RequestResponseInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestResponseInterceptor requestResponseInterceptor;

    public WebConfig(RequestResponseInterceptor requestResponseInterceptor) {
        this.requestResponseInterceptor = requestResponseInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestResponseInterceptor)
                .addPathPatterns("/api/sum")
                .excludePathPatterns("/api/health/**", "/api/status/**");
    }

    @Bean
    public OncePerRequestFilter requestResponseCachingFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
                ContentCachingResponseWrapper wrappedResponse = null;
                if (!(response instanceof ContentCachingResponseWrapper)) {
                    wrappedResponse = new ContentCachingResponseWrapper(response);
                } else {
                    wrappedResponse = (ContentCachingResponseWrapper) response;
                }
                filterChain.doFilter(wrappedRequest, wrappedResponse);
                if (wrappedResponse != null) {
                    wrappedResponse.copyBodyToResponse();
                }
            }
        };
    }
}
