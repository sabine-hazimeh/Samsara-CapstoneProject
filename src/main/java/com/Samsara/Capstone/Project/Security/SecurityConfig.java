
package com.Samsara.Capstone.Project.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomLoginFailureHandler loginFailureHandler;

    @Autowired
    public SecurityConfig(CustomLoginSuccessHandler loginSuccessHandler, CustomLoginFailureHandler loginFailureHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;

    }
    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/static/**", "/home/**", "/css/**", "/fonts/**", "/Post/**", "/logIn/**", "/Register/**",
                            "/login/**", "/images/**", "/search/**", "/post/{postId}/image", "/js/**","/favicon.ico","/error","/user/**").permitAll();
                    auth.requestMatchers("/cart/**", "/Admin/**", "/wish/**", "/post/**", "/review/**",
                            "/Employee/**","/client/**","/predict/**").authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/logIn/display-logIn")
                            .loginProcessingUrl("/login")
                            .failureHandler(loginFailureHandler)
                            .successHandler(loginSuccessHandler)
                            .permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}






