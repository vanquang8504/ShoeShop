package com.example.shopgiayvip;

import com.example.shopgiayvip.Repository.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                                        .requestMatchers("/*").permitAll()
                                        .requestMatchers("/admin/**").hasAnyAuthority("Admin")
                .anyRequest().authenticated()
        ).formLogin(login -> login.loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("matKhau")
                .defaultSuccessUrl("/checkLogin", true)

        ).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"));
        return httpSecurity.build();
    }
    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers("/css/**","/img/**","/js/**","/lib/**","/scss/**");
    }
}
