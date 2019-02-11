package com.hyperanna.adapters

import com.google.inject.AbstractModule
import com.hyperanna.adapters.db.InMemoryGreeterRepository
import com.hyperanna.adapters.routes.HelloRoutes
import com.hyperanna.servicetemplate.ports.required.GreetingsRepository
import io.ktor.util.KtorExperimentalAPI

class AdapterModule : AbstractModule() {

    @KtorExperimentalAPI
    override fun configure() {
        // N.B. - order matters, application config needs to be loaded first
        bind(ApplicationConfig::class.java).asEagerSingleton()
        bind(HelloRoutes::class.java).asEagerSingleton()
        bind(GreetingsRepository::class.java).to(InMemoryGreeterRepository::class.java)
    }
}
