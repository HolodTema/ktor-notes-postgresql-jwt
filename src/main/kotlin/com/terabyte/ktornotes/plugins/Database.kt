package com.terabyte.ktornotes.plugins

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.terabyte.ktornotes.models.Notes
import com.terabyte.ktornotes.models.Users
import io.ktor.server.application.Application


fun Application.configureDatabase() {
    Database.connect(
        url = environment.config.property("postgres.url").getString(),
        driver = "org.postgresql.Driver",
        user = environment.config.property("postgres.user").getString(),
        password = environment.config.property("postgres.password").getString()
    )

    transaction {
        SchemaUtils.create(Users, Notes)
    }
}