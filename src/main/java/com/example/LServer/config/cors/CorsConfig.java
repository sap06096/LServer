package com.example.LServer.config.cors;

import com.example.LServer.model.setting.AllowedOriginEntity;
import com.example.LServer.repository.system.AllowedOriginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final AllowedOriginRepository allowedOriginRepository;
    private CorsConfiguration corsConfiguration;

    // CORS Filter 설정
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());  // 모든 엔드포인트에 대해 CORS 설정 적용
        return new CorsFilter(source);
    }

    // CORS Configuration을 반환하는 메서드
    public CorsConfiguration buildConfig() {
        // DB에서 허용된 출처 목록을 가져옵니다.
        List<String> allowedOrigins = allowedOriginRepository.findAllByUse(true).stream()
                .map(AllowedOriginEntity::getOrigin)
                .collect(Collectors.toList());

        System.out.print(allowedOrigins);
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Spring Boot 3.x에서 권장하는 방식: allowedOriginPatterns 사용
        corsConfig.setAllowedOriginPatterns(allowedOrigins);  // 모든 origin을 패턴으로 처리
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        corsConfig.setAllowedHeaders(Arrays.asList(
                "X-Requested-With", "Origin", "Content-Type", "Accept",
                "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods", "Access-Control-Allow-Origin", "Access-Control-Expose-Headers",
                "Access-Control-Max-Age", "Access-Control-Request-Headers", "Access-Control-Request-Method",
                "Age", "Allow", "Alternates", "Content-Range", "Content-Disposition", "Content-Description"));
        corsConfig.setAllowCredentials(true);  // 인증 정보 허용
        corsConfig.setMaxAge(3600L);  // 1시간 동안 캐시

        return corsConfiguration = corsConfig;
    }

    public CorsConfiguration getCorsConfiguration() { return corsConfiguration; }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**").combine(buildConfig());
            }
        };
    }
}
