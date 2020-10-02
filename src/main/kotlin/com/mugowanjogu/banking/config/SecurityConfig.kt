package com.mugowanjogu.banking.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@Configuration
open class SecurityConfig: WebSecurityConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
            ?.inMemoryAuthentication()
            ?.withUser("test")
            ?.password("{noop}test")
            ?.roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        http
            ?.authorizeRequests()
            ?.anyRequest()?.authenticated()
            //?.antMatchers("/account/**")?.hasRole("USER")
            ?.and()
            ?.httpBasic()
            ?.and()
            ?.csrf()?.disable()
            ?.formLogin()?.disable()
    }
}