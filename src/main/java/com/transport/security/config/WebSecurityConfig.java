package com.transport.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.transport.security.utils.SecurityConstants.TRANSPORTER_ENDPOINTS;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final AccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/home")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/transportations/**", "/api/addresses/**", "/api/deliveries/**", "/api/payments/**").hasRole("FORWARDER")
                .antMatchers(HttpMethod.PUT, "/api/transportations/**", "/api/addresses/**", "/api/deliveries/**", "/api/payments/**").hasRole("FORWARDER")
                .antMatchers(HttpMethod.DELETE, "/api/transportations/**", "/api/addresses/**", "/api/deliveries/**", "/api/payments/**").hasRole("FORWARDER")
                .antMatchers(HttpMethod.POST, "/api/cargos/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/api/cargos/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/api/cargos/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/api/cargos/**", "/api/payments/current").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, TRANSPORTER_ENDPOINTS).hasRole("TRANSPORTER")
                .antMatchers(HttpMethod.POST, "/api/emails/sendReport").hasRole("TRANSPORTER")
                .antMatchers("/api/users/**", "/api/emails/sendCustomerReminder").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/**").hasRole("FORWARDER")
                .antMatchers(HttpMethod.POST, "/api/emails/sendCustomerReminder").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/emails/send", "/api/emails/sendWithAttachment").hasAnyRole("FORWARDER", "CUSTOMER", "ADMIN", "TRANSPORTER");

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
