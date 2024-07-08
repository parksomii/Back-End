package edu.allinone.sugang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // Spring Security의 웹 보안 지원을 활성화
@Slf4j // 로깅 위한 Log 객체 자동 생성
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    // 실제 보안상 위험하긴 하지만 저희가 따로 회원가입을 구현하지 않고 DB에 값을 직접 넣어주다 보니
    // 입력받은 비밀번호를 BCrypt 형식($2a$10$DowQ8u..)으로 인코딩한 후, 데이터 베이스에 저장할 수가 없기 때문에
    // 일단은 평문 비밀번호(password1)로 저장하도록 설정해주었습니다.

    @Bean
    // 어떤 URL 경로를 보안해야 하고 어떤 경로를 보안하지 않아야 하는지 정의
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().authenticated()
                        // 위에서 명시한 경로를 제외한 모든 요청에 대해 인증된 사용자만 접근할 수 있도록 설정

                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/", true)
                        // 성공 시 이동 경로 (임의로 작성, 나중에 구현 시 수정)
                        .usernameParameter("studentNumber") // 로그인 폼에서 사용자 이름 필드 이름 설정
                        .passwordParameter("studentPassword") // 로그인 폼에서 비밀번호 필드 이름 설정
                        .permitAll() // 로그인 폼에 모든 사용자 접근 가능
                )
                .logout(Customizer.withDefaults()); // 기본 값

        return http.build();
    }

}
