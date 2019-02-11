package com.hyperanna.adapters.routes

import com.google.inject.Inject
import com.hyperanna.servicetemplate.ports.provided.Greeter
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

class HelloRoutes @Inject constructor(application: Application, greeter: Greeter) {
    init {
        application.routing {
            get("/") {
                call.respondText(greeter.greet("anonymous"), contentType = ContentType.Text.Plain)
            }

            get("/json/gson") {
                call.respond(mapOf("hello" to "world"))
            }

            authenticate {
                route("/secure/hello") {
                    handle {
                        val principal = call.authentication.principal<JWTPrincipal>()
                        val subjectString = principal!!.payload.subject
                        call.respondText(greeter.greet(subjectString), contentType = ContentType.Text.Plain)
                    }
                }
            }
        }
    }
}
