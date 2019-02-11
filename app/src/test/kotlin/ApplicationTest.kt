package com.hyperanna

import io.kotlintest.shouldBe
import io.ktor.config.MapApplicationConfig
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.junit.jupiter.api.Test

@KtorExperimentalAPI
class ApplicationTest {

    @Test
    fun `sample integration test`() {
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                // Set here the properties
                put("jwt.issuer", "anna")
                put("jwt.secret", "ssssh!")
            }
            module()
        }) {
            handleRequest(HttpMethod.Get, "/").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "Hello from the domain module. Here is your personalized message: 'Don't know you, anonymous'"
            }
        }
    }
}
