package com.lab.sensors.security.config;


import com.lab.sensors.security.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String USER_ROLE = "Viewer";
    public static final String ADMIN_ROLE = "Administrator";

    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)

                .and()

                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/sensors", "/sensors/*", "/sensors/**").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/sensors").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, "/sensors/*").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.PATCH, "/sensors/*").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/users/*").permitAll()

                .anyRequest().hasRole(ADMIN_ROLE)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
