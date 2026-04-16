package com.terabyte.ktornotes.routing

import com.terabyte.ktornotes.models.NoteRequest
import com.terabyte.ktornotes.plugins.getUserId
import com.terabyte.ktornotes.services.NoteService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route


fun Route.noteRoutes(noteService: NoteService) {
    // these request will pass authentication plugin
    // Authorization-http-header with JWT token inside will be checked
    authenticate {
        route("/api/notes") {

            // create note
            post {
                val userId = call.getUserId()
                val noteRequest = call.receive<NoteRequest>()

                val note = noteService.createNote(userId, noteRequest)

                if (note == null) {
                    call.respond(HttpStatusCode.BadRequest, "Unable to create note")
                }
                else {
                    call.respond(HttpStatusCode.Created, note)
                }
            }

            // update existing note by its id
            put("/{id}") {
                val noteId = call.parameters["id"]?.toIntOrNull()
                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@put
                }

                val userId = call.getUserId()
                val noteRequest = call.receive<NoteRequest>()
                val isUpdated = noteService.updateNote(noteId, userId, noteRequest)

                if (isUpdated) {
                    call.respond(HttpStatusCode.OK, "Note was updated successfully")
                }
                else {
                    call.respond(HttpStatusCode.NotFound, "Note not found or access denied")
                }
            }

            // delete note by its id
            delete("/{id}") {
                val userId = call.getUserId()
                val noteId = call.parameters["id"]?.toIntOrNull()

                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Note ID")
                    return@delete
                }

                val isDeleted = noteService.deleteNote(noteId, userId)
                if (isDeleted) {
                    call.respond(HttpStatusCode.OK, "Note deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found or access denied")
                }
            }

            // get all notes of the user
            get {
                // using function from plugins/Security.kt
                // to get userId from JWT token
                val userId = call.getUserId()
                val notes = noteService.getNotesByUserId(userId)
                call.respond(notes)
            }

            // get one note by its id
            get("/{id}") {
                val userId = call.getUserId()
                val noteId = call.parameters["id"]?.toIntOrNull()

                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@get
                }

                val note = noteService.getNoteById(noteId)
                if (note != null && note.userId == userId) {
                    call.respond(note)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found")
                }
            }
        }
    }
}