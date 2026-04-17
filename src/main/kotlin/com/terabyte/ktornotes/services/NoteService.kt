package com.terabyte.ktornotes.services

import com.terabyte.ktornotes.models.Note
import com.terabyte.ktornotes.models.NoteRequest
import com.terabyte.ktornotes.models.Notes
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class NoteService {

    fun createNote(userId: Int, request: NoteRequest): Note? = transaction {
        val result = Notes.insert {
            it[Notes.userId] = userId
            it[Notes.title] = request.title
            it[Notes.content] = request.content
            it[Notes.createdAt] = LocalDateTime.now()
            it[Notes.updatedAt] = LocalDateTime.now()
        }
        val noteId = result[Notes.id]
        getNoteById(noteId)
    }

    fun updateNote(noteId: Int, userId: Int, request: NoteRequest): Boolean = transaction {
        val amountUpdatedRows = Notes.update(
            where = { (Notes.id eq noteId) and (Notes.userId eq userId) }
        ) {
            it[title] = request.title
            it[content] = request.content
            it[updatedAt] = LocalDateTime.now()
        }
        amountUpdatedRows > 0
    }

    fun deleteNote(noteId: Int, userId: Int): Boolean = transaction {
        val amountDeletedRows = Notes.deleteWhere {
            (Notes.id eq noteId) and (Notes.userId eq userId)
        }
        amountDeletedRows > 0
    }

    fun getNotesByUserId(userId: Int): List<Note> = transaction {
        Notes.select { Notes.userId eq userId }
            .map { row ->
                Note(
                    id = row[Notes.id],
                    userId = row[Notes.userId],
                    title = row[Notes.title],
                    content = row[Notes.content],
                    createdAt = row[Notes.createdAt],
                    updatedAt = row[Notes.updatedAt]
                )
            }
    }

    fun getNoteById(noteId: Int): Note? = transaction {
        Notes.select { Notes.id eq noteId }
            .map { row ->
                Note(
                    id = row[Notes.id],
                    userId = row[Notes.userId],
                    title = row[Notes.title],
                    content = row[Notes.content],
                    createdAt = row[Notes.createdAt],
                    updatedAt = row[Notes.updatedAt]
                )
            }.singleOrNull()
    }
}