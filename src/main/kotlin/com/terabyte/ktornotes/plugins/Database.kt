package com.terabyte.ktornotes.plugins

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.terabyte.ktornotes.models.Notes
import com.terabyte.ktornotes.models.Users


fun configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/notes_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "password"
    )

    transaction {
        SchemaUtils.create(Users, Notes)
    }
}