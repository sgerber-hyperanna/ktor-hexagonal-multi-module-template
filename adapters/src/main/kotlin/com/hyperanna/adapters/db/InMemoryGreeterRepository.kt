package com.hyperanna.adapters.db

import com.hyperanna.servicetemplate.ports.required.GreetingsRepository

class InMemoryGreeterRepository : GreetingsRepository {
    override fun findGreetingForUser(user: String): String {
        return when (user) {
            "alice" -> "Why hello!"
            "anna" -> "Nice to meet you!"
            "bob" -> "Salute"
            else -> "Don't know you, $user"
        }
    }
}
