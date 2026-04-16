package com.terabyte.ktornotes.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.terabyte.ktornotes.models.RegisterRequest
import com.terabyte.ktornotes.models.User
import com.terabyte.ktornotes.models.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserService {

    fun register(request: RegisterRequest): User? {
        val passwordCharArray = request.password.toCharArray()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, passwordCharArray)

        val result = Users.insert {
            it[username] = request.username
            it[email] = request.email
            it[passwordHash] = hashedPassword
        }

        val userId = result[Users.id]
        return getUserById(userId)
    }


    fun login(username: String, password: String): User? {
        val user = getUserByUsername(username) ?: return null

        // compare hash of the password from login-http-request and hash of the password in DB
        val isPasswordValid = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
            .verified

        return if (isPasswordValid) {
            user
        } else {
            null
        }
    }


    fun getUserById(id: Int): User? {
        return Users.select { Users.id eq id }
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


    fun getUserByUsername(username: String): User? {
        return Users.select { Users.username eq username }
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