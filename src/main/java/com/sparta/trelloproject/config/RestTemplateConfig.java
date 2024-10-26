package com.sparta.trelloproject.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5)) // 연결 타임아웃 설정
                .setReadTimeout(Duration.ofSeconds(5))    // 읽기 타임아웃 설정
                .errorHandler(new CustomErrorHandler())   // 커스텀 에러 핸들러 추가
                .build();
    }

    // 에러 핸들링
    private static class CustomErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
            return httpResponse.getStatusCode().isError();
        }

        @Override
        public void handleError(ClientHttpResponse httpResponse) throws IOException {
            // 에러 처리 로직 (로그 남기기 등)
            System.err.println("Error occurred: " + httpResponse.getStatusCode());
        }
    }
}