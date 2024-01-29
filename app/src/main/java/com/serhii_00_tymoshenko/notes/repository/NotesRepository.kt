package com.serhii_00_tymoshenko.notes.repository

import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.pseudodb.PseudoDb
import java.util.concurrent.Flow

class NotesRepository {
    private val pseudoDb = PseudoDb.getInstance()

    fun getNotes() = pseudoDb.getNotes()

    fun addNote(note: Note) = pseudoDb.addNote(note)

    fun editNote(editedNote: Note) = pseudoDb.editNote(editedNote)

    fun deleteNote(note: Note) = pseudoDb.deleteNote(note)
}