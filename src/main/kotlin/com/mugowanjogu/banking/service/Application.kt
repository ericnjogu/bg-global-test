package com.mugowanjogu.banking.service

import org.apache.catalina.startup.Tomcat
import org.apache.catalina.webresources.DirResourceSet
import org.apache.catalina.webresources.StandardRoot
import java.io.File

class Application

fun main(args: Array<String>) {
    val webDir = "src/main/webapp"
    val tomcat = Tomcat()

    val webPort = System.getenv("port") ?: "8008"
    tomcat.setPort(webPort.toInt())

    val ctx = tomcat.addWebapp("/", File(webDir).absolutePath)
    val resources = StandardRoot(ctx)
    resources.addPreResources(
        DirResourceSet(
            resources,
            File("target/classes").absolutePath,
            "/",
            "WEB-INF/classes"
        )
    )

    ctx.resources = resources
    tomcat.start()
    tomcat.server.await()
}