package com.hyperanna.adapters.routes

import com.google.inject.Inject
import com.hyperanna.adapters.IntegrationTestBase
import com.hyperanna.servicetemplate.ports.provided.Greeter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HelloRoutesTest : IntegrationTestBase() {

    @Inject
    private lateinit var mockGreeter: Greeter

    @Before
    fun resetMocks() {
        reset(mockGreeter)
    }

    @Test
    fun `sample adapter test`() {

        whenever(mockGreeter.greet(any()))
            .thenReturn("Mocks FTW!")

        engine.handleRequest(HttpMethod.Get, "/").apply {
            response.status() shouldBe HttpStatusCode.OK
            response.content shouldBe "Mocks FTW!"
        }
    }

    @Test
    fun `should return 401 for an authenticated endpoint if no token provided`() {
        engine.handleRequest(HttpMethod.Get, "/secure/hello").apply {
            response.status() shouldBe HttpStatusCode.Unauthorized
        }
    }

    @Test
    fun `should return 401 if issuer is incorrect`() {
        engine.handleRequest(HttpMethod.Get, "/secure/hello") {
            addHeader("Authorization", "Bearer ${generateToken(issuer = "not Anna")}")
        }.apply {
            response.status() shouldBe HttpStatusCode.Unauthorized
        }
    }

    @Test
    fun `should greet the logged in user if properly authenticated`() {
        whenever(mockGreeter.greet("anna"))
            .thenReturn("Greetings, Anna!")

        engine.handleRequest(HttpMethod.Get, "/secure/hello") {
            addHeader("Authorization", "Bearer ${generateToken()}")
        }.apply {
            response.status() shouldBe HttpStatusCode.OK
            response.content shouldBe "Greetings, Anna!"
        }
    }
}
