package com.terabyte.ktornotes.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.principal
import java.util.Date


// JWT (json web token) is like digital user's passport
// after logging in, user gets token, which he/she show every API request
//
// usually, user put token in Authorization http-header.
// if there is no token in Authorization-header, there will be 401 (unauthorized) error

// after successful log-in-request we need to generate token and return the token to user
// in http-response

// then user (frontend, mobile app) stores the token.
// in every API request user puts the token into Authorization-http-header

// this backend app tries to find Authorization-http-header and get the token.
// after getting the token, the backend app verify and validate token
// (see verifier() and validate() functions)

// why do we sign the token with secret key?
// we sign the token to prevent userId changing in token.
// because if someone tries to change userId, the sign will become invalid

// SAFETY FEATURES:
// 1. token is signed with secret key. Nobody can change userId inside the token.
// 2. token can be expired in 24 hours. Even if the token is stolen, it will expire in 24 hours.
// 3. secret key to sign and verify tokens is stored in ENV-variable on our VDS, not in code.

// configure JWT auth
fun Application.configureJWT() {
    // secret key to sign and prove tokens
    val jwtSecret = System.getenv("JWT_SECRET") ?: "your-secret-key"

    // who gave the token
    val jwtIssuer = "ktor--notes-postgres-jwt-app"

    // in what system (application, server, location) such a token works
    val jwtRealm = "ktor-notes-postgres-jwt"

    // configure Authentication system
    install(Authentication) {
        // declare that we use JWT technology
        jwt {
            realm = jwtRealm
            // verifier is token-checker
            verifier(
                // use HMAC256 algorithm with secret key
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    // check that the token was given by our backend-app
                    .withIssuer(jwtIssuer)
                    .build()
            )

            // after token verification we check that the token is valid now
            validate { credential ->
                // check that token is for our realm
                if (credential.payload.audience.contains(jwtRealm)) {
                    // token is valid, we can handle the API request
                    JWTPrincipal(credential.payload)
                } else {
                    // token is invalid, reject request
                    null
                }
            }
        }
    }
}


//to generate JWT token
fun generateToken(userId: Int, username: String): String {
    val jwtSecret = System.getenv("JWT_SECRET") ?: "your-secret-key"
    val jwtIssuer = "ktor--notes-postgres-jwt-app"

    // every token contains jwtIssuer (who gave the token)
    // userId (the id of the user who uses the token)
    // username (name of the user who uses the token)
    // token expires every 24 hours
    // finally we sign the token with secret key and HMAC256 algorithm

    // example of generated token:
    //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQi
    //OjEyMywidXNlcm5hbWUiOiJqb2huIiwiZXhwIjoxNzAwMDAwMDAwfQ.signature

    return JWT.create()
        .withIssuer(jwtIssuer)
        .withClaim("userId", userId)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + 86_400_000))
        .sign(Algorithm.HMAC256(jwtSecret))
}


// get userId from token
fun ApplicationCall.getUserId(): Int {
    // principal is token owner, who have sent this token to us
    val principal = principal<JWTPrincipal>()

    // and try to get userId from principal object
    return principal?.payload?.getClaim("userId")?.asInt()
        ?: throw IllegalArgumentException("No userId in this token.")
}

