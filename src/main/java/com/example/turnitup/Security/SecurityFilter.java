package com.example.turnitup.Security;

import com.example.turnitup.Service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity

public class SecurityFilter   {

   CustomUserDetailService customUserDetailsService;

   JwtRequestFilter jwtRequestFilter;

    public SecurityFilter(CustomUserDetailService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

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

                .antMatchers("/message/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/message").hasAnyRole("ORGANISATION", "DJ")


                .antMatchers("/dj/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/dj").hasRole("DJ")
                .antMatchers(HttpMethod.PUT, "/dj/{id}").hasRole("DJ")

                .antMatchers("/organisation/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/organisation").hasRole("ORGANISATION")
                .antMatchers(HttpMethod.PUT,"/organisation/{id}").hasRole("ORGANISATION")

                .antMatchers("/mixtape/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/mixtape").hasRole("DJ")
                .antMatchers("/mixtape/playMixtape/{id}").hasAnyRole("ADMIN", "ORGANISATION", "DJ")
                .antMatchers("mixtape/downloadAsZip/{mixtapeName}").hasAnyRole("ADMIN", "ORGANISATION", "DJ")

                .antMatchers("/rating/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,  "rating").hasRole("ORGANISATION")
                .antMatchers(HttpMethod.PUT,  "rating").hasRole("ORGANISATION")
                .antMatchers("rating/{id}").hasRole("ORGANISATION")

                .antMatchers("/booking/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/booking/{id}").hasRole("ORGANISATION")

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
