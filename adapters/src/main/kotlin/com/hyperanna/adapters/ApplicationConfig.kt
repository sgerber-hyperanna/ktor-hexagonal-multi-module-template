package com.hyperanna.adapters

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import com.google.gson.GsonBuilder
import com.google.inject.Inject
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.util.KtorExperimentalAPI
import org.apache.commons.codec.binary.Base64
import org.slf4j.event.Level
import java.nio.charset.Charset

@KtorExperimentalAPI
class ApplicationConfig @Inject constructor(application: Application) {
    init {
        application.apply {
            install(CallLogging) {
                level = Level.INFO
                filter { call -> call.request.path().startsWith("/") }
            }

            install(Authentication) {
                val issuer = environment.config.property("jwt.issuer").getString()
                val secret = environment.config.property("jwt.secret").getString()

                val jwtVerifier = makeJwtVerifier(issuer, secret)

                jwt {
                    verifier(jwtVerifier)
                    validate { credential ->
                        JWTPrincipal(SubjectDecodingPayload(credential.payload))
                    }
                }
            }

            install(ContentNegotiation) {
                gson {
                }
            }

            install(StatusPages) {
                exception<AuthenticationException> {
                    call.respond(HttpStatusCode.Unauthorized)
                }
                exception<AuthorizationException> {
                    call.respond(HttpStatusCode.Forbidden)
                }
            }
        }
    }
}

private fun makeJwtVerifier(issuer: String, secret: String): JWTVerifier = JWT
    .require(Algorithm.HMAC256(secret))
    .withIssuer(issuer)
    .build()

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

class SubjectDecodingPayload(private val delegate: Payload) : Payload by delegate {
    private data class Provider(val providerID: String, val providerKey: String)

    private val decodedSubject: String by lazy {
        val encodedSubject = delegate.subject
        val decodedSubject = Base64.decodeBase64(encodedSubject).toString(Charset.forName("UTF-8"))
        val provider = GsonBuilder().create().fromJson<Provider>(decodedSubject, Provider::class.java)

        provider.providerKey
    }

    override fun getSubject(): String = decodedSubject
}

