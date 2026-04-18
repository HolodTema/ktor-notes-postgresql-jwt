package com.terabyte.ktornotes.routing

import com.terabyte.ktornotes.models.UserInfoResponse
import com.terabyte.ktornotes.plugins.getUserId
import com.terabyte.ktornotes.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.userRoutes(userService: UserService) {
    authenticate {
        route("/api/user") {

            // get current user info
            get {
                val userId = call.getUserId()
                val user = userService.getUserById(userId)

                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
                else {
                    val userInfoResponse = UserInfoResponse(
                        username = user.username,
                        email = user.email
                    )
                    call.respond(userInfoResponse)
                }
            }
        }
    }
}