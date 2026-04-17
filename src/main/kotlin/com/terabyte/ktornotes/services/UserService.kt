package com.terabyte.ktornotes.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.terabyte.ktornotes.models.RegisterRequest
import com.terabyte.ktornotes.models.User
import com.terabyte.ktornotes.models.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserService {

    fun register(request: RegisterRequest): User? = transaction {
        val passwordCharArray = request.password.toCharArray()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, passwordCharArray)

        val result = Users.insert {
            it[username] = request.username
            it[email] = request.email
            it[passwordHash] = hashedPassword
        }

        val userId = result[Users.id]
        getUserById(userId)  // этот вызов тоже внутри транзакции
    }

    fun login(username: String, password: String): User? = transaction {
        val user = getUserByUsername(username) ?: return@transaction null
        val isPasswordValid = BCrypt.verifyer()
            .verify(password.toCharArray(), user.passwordHash)
            .verified
        if (isPasswordValid) user else null
    }

    fun getUserById(id: Int): User? = transaction {
        Users.select { Users.id eq id }
            .map {
                User(
                    id = it[Users.id],
                    username = it[Users.username],
                    email = it[Users.email],
                    passwordHash = it[Users.passwordHash],
                    createdAt = it[Users.createdAt]
                )
            }.singleOrNull()
    }

    fun getUserByUsername(username: String): User? = transaction {
        Users.select { Users.username eq username }
            .map {
                User(
                    id = it[Users.id],
                    username = it[Users.username],
                    email = it[Users.email],
                    passwordHash = it[Users.passwordHash],
                    createdAt = it[Users.createdAt]
                )
            }.singleOrNull()
    }
}