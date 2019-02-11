package com.hyperanna.servicetemplate.domain

import com.hyperanna.servicetemplate.ports.required.GreetingsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class SimpleGreeterTest {

    private val greeterRepository: GreetingsRepository = mock()
    private val simpleGreeter = SimpleGreeter(greeterRepository)

    @Test
    fun `should look up the greeting and return it`() {
        whenever(greeterRepository.findGreetingForUser("bob"))
            .thenReturn("Oh hai")

        simpleGreeter.greet("bob") shouldBe "Hello from the domain module. Here is your personalized message: 'Oh hai'"
    }
}
