package com.terabyte.ktornotes

import com.terabyte.ktornotes.plugins.configureDatabase
import com.terabyte.ktornotes.plugins.configureJWT
import com.terabyte.ktornotes.routing.authRoutes
import com.terabyte.ktornotes.routing.noteRoutes
import com.terabyte.ktornotes.services.NoteService
import com.terabyte.ktornotes.services.UserService
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}


fun Application.module() {
    // configure PostgreSQL and Exposed ORM
    configureDatabase()

    // configure JWT authentication
    configureJWT()

    // create service objects
    val userService = UserService()
    val noteService = NoteService()

    routing {
        authRoutes(userService)
        noteRoutes(noteService)
    }
}
