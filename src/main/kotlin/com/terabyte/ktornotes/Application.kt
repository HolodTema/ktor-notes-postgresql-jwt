package com.terabyte.ktornotes

import com.terabyte.ktornotes.plugins.configureDatabase
import com.terabyte.ktornotes.plugins.configureJWT
import com.terabyte.ktornotes.routing.authRoutes
import com.terabyte.ktornotes.routing.noteRoutes
import com.terabyte.ktornotes.services.NoteService
import com.terabyte.ktornotes.services.UserService
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*


fun main(args: Array<String>) = EngineMain.main(args)


//this function will be called automatically with info from application.yaml
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
