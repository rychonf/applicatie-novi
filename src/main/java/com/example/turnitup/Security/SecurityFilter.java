package com.example.turnitup.Security;

import com.example.turnitup.Controller.AuthenticationController;
import com.example.turnitup.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity

public class SecurityFilter   {

    @Autowired
   CustomUserDetailService customUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public UserDetailsService userDetailsService(){
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/message/create").hasRole("ORGANISATION")
                .antMatchers("/message/**").hasRole("ADMIN")
                .antMatchers("/dj/update/{id}").hasRole("DJ")
                .antMatchers("/dj/create").hasAnyRole("ADMIN", "DJ")
                .antMatchers("/dj/**").hasRole("ADMIN")
                .antMatchers("/organisation/update/{id}").hasRole("ORGANISATION")
                .antMatchers("/organisation/create").hasRole("ORGANISATION")
                .antMatchers("/organisation/**").hasRole("ADMIN")
                .antMatchers("/mixtape/uploadToDB").hasRole("DJ")
                .antMatchers("/mixtape/**").hasAnyRole("ADMIN", "ORGANISATION", "DJ")
                .antMatchers("/rating/**").hasAnyRole("ADMIN", "ORGANISATION")
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
