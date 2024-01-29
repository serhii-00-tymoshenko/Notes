package com.serhii_00_tymoshenko.notes.repository

import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.pseudodb.PseudoDb

class NotesRepository {
    private val pseudoDb = PseudoDb.getInstance()

    fun getNotes() = pseudoDb.getNotes()

    fun getNote(noteId: String) = pseudoDb.getNote(noteId)

    fun addNote(note: Note) = pseudoDb.addNote(note)

    fun editNote(editedNote: Note) = pseudoDb.editNote(editedNote)

    fun deleteNote(note: Note) = pseudoDb.deleteNote(note)

    companion object {
        private var instance: NotesRepository? = null

        fun getInstance() = instance ?: NotesRepository().also { instance = it }
    }
}