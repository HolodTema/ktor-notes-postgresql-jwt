package com.terabyte.ktornotes

import com.terabyte.ktornotes.plugins.configureDatabase
import com.terabyte.ktornotes.plugins.configureJWT
import com.terabyte.ktornotes.routing.authRoutes
import com.terabyte.ktornotes.routing.noteRoutes
import com.terabyte.ktornotes.routing.userRoutes
import com.terabyte.ktornotes.services.NoteService
import com.terabyte.ktornotes.services.UserService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json


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

    // to convert JSON to Kotlin classes via kotlinx.serialization
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true   // временно для диагностики
            isLenient = true
        })
    }

    routing {
        authRoutes(userService)
        noteRoutes(noteService)
        userRoutes(userService)
    }
}
