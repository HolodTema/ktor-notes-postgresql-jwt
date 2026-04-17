package com.terabyte.ktornotes.routing

import com.terabyte.ktornotes.models.LoginRequest
import com.terabyte.ktornotes.models.LoginResponse
import com.terabyte.ktornotes.models.RegisterRequest
import com.terabyte.ktornotes.plugins.generateToken
import com.terabyte.ktornotes.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.authRoutes(userService: UserService) {
    route("/api/auth") {

        post("/register") {
            val request = call.receive<RegisterRequest>()

            // check if such a user already exists
            val existingUser = userService.getUserByUsername(request.username)
            if (existingUser != null) {
                call.respond(HttpStatusCode.Conflict, "Username already exists")
                return@post
            }

            // this username is free, create new user
            val user = userService.register(request)
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, "Unable to register new user")
            }
            else {
                val token = generateToken(user.id, user.username)
                call.respond(HttpStatusCode.Created, LoginResponse(token, user.username))
            }
        }

        post("/login") {
//            val body = call.receiveText()  // сырой JSON
//            println("Raw body: $body")
//            val request = kotlinx.serialization.json.Json.decodeFromString<LoginRequest>(body)

            val request = call.receive<LoginRequest>()
            val user = userService.login(request.username, request.password)

            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
            else {
                val token = generateToken(user.id, user.username)
                call.respond(LoginResponse(token, user.username))
            }
        }
    }
}

