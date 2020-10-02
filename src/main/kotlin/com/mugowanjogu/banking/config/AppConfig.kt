package com.mugowanjogu.banking.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@ComponentScan(basePackages = ["com.mugowanjogu.banking.service"])
@PropertySource("classpath:bg-app.properties")
@EnableWebMvc
@Import(SecurityConfig::class)
open class AppConfig