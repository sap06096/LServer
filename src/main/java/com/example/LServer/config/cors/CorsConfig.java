package com.example.LServer.config.cors;

import com.example.LServer.model.setting.AllowedOriginEntity;
import com.example.LServer.repository.system.AllowedOriginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final AllowedOriginRepository allowedOriginRepository; // DB에서 Origin 목록을 가져오는 Repository

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS 및 CSRF 설정
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가

                // CSRF 비활성화 (특히 POST 요청의 경우 CSRF를 비활성화할 수 있습니다)
                .csrf(csrf -> csrf.disable())

                // 모든 요청 허용 (테스트 환경용)
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest.anyRequest().permitAll()
                );
                
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // DB에서 허용된 Origin 목록 가져오기
        List<String> allowedOrigins = allowedOriginRepository.findAllByUse(true).stream()
                .map(AllowedOriginEntity::getOrigin)
                .collect(Collectors.toList());

        System.out.println("허용된 Origin 목록: " + allowedOrigins);


        CorsConfiguration configuration = getCorsConfiguration(allowedOrigins);

        // URL 기반 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    private static CorsConfiguration getCorsConfiguration(List<String> allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(allowedOrigins); // 허용된 Origin 목록 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")); // 허용된 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList(
                "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"
        )); // 허용된 Header
        configuration.setAllowCredentials(true); // 인증 정보 허용
        configuration.setMaxAge(3600L); // Pre-flight 요청 캐시 시간 (1시간)
        return configuration;
    }
}
