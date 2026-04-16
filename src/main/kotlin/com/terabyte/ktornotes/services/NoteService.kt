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
import java.time.LocalDateTime

class NoteService {

    fun createNote(userId: Int, request: NoteRequest): Note? {
        val result = Notes.insert {
            it[Notes.userId] = userId
            it[Notes.title] = request.title
            it[Notes.content] = request.content
            it[Notes.createdAt] = LocalDateTime.now()
            it[Notes.updatedAt] = LocalDateTime.now()
        }

        val noteId = result[Notes.id]
        return getNoteById(noteId)
    }


    fun updateNote(noteId: Int, userId: Int, request: NoteRequest): Boolean {
        val amountUpdatedRows = Notes.update(
            where = { (Notes.id eq noteId) and (Notes.userId eq userId)}
        ) {
            it[title] = request.title
            it[content] = request.content
            it[updatedAt] = LocalDateTime.now()
        }

        return amountUpdatedRows > 0
    }


    fun deleteNote(noteId: Int, userId: Int): Boolean {
        val amountDeletedRows = Notes.deleteWhere {
            (Notes.id eq noteId) and (Notes.userId eq userId)
        }
        return amountDeletedRows > 0
    }


    fun getNotesByUserId(userId: Int): List<Note> {
        return Notes.select { Notes.userId eq userId }
            .map { resultRow ->
                Note(
                    id = resultRow[Notes.id],
                    userId = resultRow[Notes.userId],
                    title = resultRow[Notes.title],
                    content = resultRow[Notes.content],
                    createdAt = resultRow[Notes.createdAt],
                    updatedAt = resultRow[Notes.updatedAt]
                )
            }
    }


    fun getNoteById(noteId: Int): Note? {
        return Notes.select { Notes.id eq noteId }
            .map { resultRow ->
                Note(
                    id = resultRow[Notes.id],
                    userId = resultRow[Notes.userId],
                    title = resultRow[Notes.title],
                    content = resultRow[Notes.content],
                    createdAt = resultRow[Notes.createdAt],
                    updatedAt = resultRow[Notes.updatedAt]
                )
            }.singleOrNull()
    }
}