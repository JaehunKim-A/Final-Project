package com.team1.team1project.config;

import com.team1.team1project.security.CustomUserDetailsService;
import com.team1.team1project.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // DB 사용자 인증 서비스 설정
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        // 더미 계정 추가 (인메모리 사용자)
        auth.inMemoryAuthentication()
                .withUser("guest1231")
                .password(passwordEncoder().encode("guest1234"))
                .authorities("ROLE_USER");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 정적 리소스 접근 허용
                .antMatchers("/assets/**", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/error/**","/img/**").permitAll()
                .antMatchers("/login", "/signup").permitAll()

                // 팀장만 접근할 수 있는 경로 설정 (hasAuthority 사용)
                .antMatchers(HttpMethod.POST, "/**").hasAuthority("ROLE_TEAMLEADER")
                .antMatchers(HttpMethod.PUT, "/**").hasAuthority("ROLE_TEAMLEADER")
                .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ROLE_TEAMLEADER")
                .antMatchers(HttpMethod.GET, "/**/delete/**").hasAnyAuthority("ROLE_TEAMLEADER")

                // 나머지 모든 요청은 인증된 사용자만 접근 가능
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login") // 로그인 처리 URL
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error")
                .usernameParameter("loginId") // 템플릿의 input name과 일치
                .passwordParameter("password") // 템플릿의 input name과 일치
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe() // 로그인 상태 유지 기능
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(86400) // 24시간
                .rememberMeParameter("remember-me") // 템플릿의 체크박스 name과 일치
                .and()
                .csrf().ignoringAntMatchers("/api/**")
        ; // CSRF 보호 활성화 (API 요청은 제외)

        // 디버깅을 위한 추가 설정
        http.exceptionHandling().accessDeniedPage("/access-denied");
    }
}