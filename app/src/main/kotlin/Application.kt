package com.hyperanna

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.hyperanna.adapters.AdapterModule
import com.hyperanna.servicetemplate.domain.DomainModule
import io.ktor.application.Application
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}

fun Application.module() {
    Guice.createInjector(MainModule(this), AdapterModule(), DomainModule())
}

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(Application::class.java).toInstance(application)
    }
}
