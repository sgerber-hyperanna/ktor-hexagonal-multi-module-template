package com.hyperanna.servicetemplate.domain

import com.hyperanna.servicetemplate.ports.provided.Greeter
import com.hyperanna.servicetemplate.ports.required.GreetingsRepository
import javax.inject.Inject

class SimpleGreeter @Inject constructor (private val greeterRepository: GreetingsRepository) : Greeter {
    override fun greet(username: String): String {
        val greetingForUser = greeterRepository.findGreetingForUser(username)
        return "Hello from the domain module. Here is your personalized message: '$greetingForUser'"
    }
}
