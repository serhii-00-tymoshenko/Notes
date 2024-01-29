package com.serhii_00_tymoshenko.notes.repository

import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.pseudodb.PseudoDb

class NotesRepository {
    private val pseudoDb = PseudoDb.getInstance()

    suspend fun getNotes() = pseudoDb.getNotes()

    suspend fun getNote(noteId: String) = pseudoDb.getNote(noteId)

    suspend fun addNote(note: Note) = pseudoDb.addNote(note)

    suspend fun editNote(editedNote: Note) = pseudoDb.editNote(editedNote)

    suspend fun deleteNote(note: Note) = pseudoDb.deleteNote(note)

    companion object {
        private var instance: NotesRepository? = null

        fun getInstance() = instance ?: NotesRepository().also { instance = it }
    }
}