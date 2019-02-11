package com.hyperanna.adapters

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestEngine
import io.ktor.server.testing.createTestEnvironment
import io.ktor.util.KtorExperimentalAPI
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.util.concurrent.TimeUnit

open class IntegrationTestBase {

    protected lateinit var engine: TestApplicationEngine

    @BeforeAll
    @KtorExperimentalAPI
    fun setupTestServer() {
        engine = TestEngine.create(createTestEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load("jwt.conf"))
        }) {}
        engine.start()
        engine.application.module()

        testInjector.injectMembers(this)
    }

    @AfterAll
    fun stopServer() {
        engine.stop(0L, 0L, TimeUnit.MILLISECONDS)
    }

    protected fun generateToken(issuer: String = "Anna"): String {
        return JWT
            .create()
            .withIssuer(issuer)
            .withSubject("eyJwcm92aWRlcklEIjoiY3JlZGVudGlhbHMiLCJwcm92aWRlcktleSI6ImFubmEifQ==")
            .sign(Algorithm.HMAC256("1234-1234-1234-1234-1234-1234-1234"))
    }
}

private lateinit var testInjector: Injector
fun Application.module() {
    testInjector = Guice.createInjector(MainModule(this), AdapterModule(), DomainMocksModule())
}

// Main module, binds application and routes
class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(Application::class.java).toInstance(application)
    }
}
