package com.mugowanjogu.banking

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class WebAppInitializer: AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getServletMappings(): Array<String> {
        return arrayOf("/")
    }

    override fun getRootConfigClasses(): Array<Class<*>>? {
        return null
    }

    override fun getServletConfigClasses(): Array<Class<*>>? {
        return arrayOf(AppConfig::class.java)
    }
}