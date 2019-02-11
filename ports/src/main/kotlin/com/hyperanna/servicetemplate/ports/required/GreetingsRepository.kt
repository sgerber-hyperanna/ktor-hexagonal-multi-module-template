package com.hyperanna.servicetemplate.ports.required

interface GreetingsRepository {
    fun findGreetingForUser(user: String): String
}
