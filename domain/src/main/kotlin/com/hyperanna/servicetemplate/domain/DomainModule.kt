package com.hyperanna.servicetemplate.domain

import com.google.inject.AbstractModule
import com.hyperanna.servicetemplate.ports.provided.Greeter

class DomainModule : AbstractModule() {
    override fun configure() {
        bind(Greeter::class.java).to(SimpleGreeter::class.java)
    }
}
