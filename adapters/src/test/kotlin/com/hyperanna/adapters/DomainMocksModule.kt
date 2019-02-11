package com.hyperanna.adapters

import com.google.inject.AbstractModule
import com.hyperanna.servicetemplate.ports.provided.Greeter
import org.mockito.Mockito.mock

class DomainMocksModule : AbstractModule() {
    override fun configure() {
        bind(Greeter::class.java).toInstance(mock(Greeter::class.java))
    }
}
