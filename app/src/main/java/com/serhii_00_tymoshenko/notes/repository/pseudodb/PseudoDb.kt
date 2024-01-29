package com.serhii_00_tymoshenko.notes.repository.pseudodb

import android.content.Context
import android.net.Uri
import android.util.Log
import com.serhii_00_tymoshenko.notes.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking


class PseudoDb() {
    private val notes = mutableListOf<Note>()

    init {
        fillInitialData()
    }

    private fun fillInitialData() {
        val storedNotes: MutableList<Note> = mutableListOf(
            Note(
                "Test note 1",
                "Test content 1",
                Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-u9SUmkT0vZAogUIfxeXOhi6_uH6Gc_kwYmx8_6ZQrw&s")
            ),
            Note(
                "Test note 2",
                null,
                Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-u9SUmkT0vZAogUIfxeXOhi6_uH6Gc_kwYmx8_6ZQrw&s")
            ),
            Note(
                "Test note 3",
                "Test content 3",
                null
            ),
            Note(
                "Test note 4",
                null,
                null
            )
        )

         notes.addAll(storedNotes)
    }

    fun addNote(note: Note) {
        notes.add(note)
        Log.d("FUCK IT", notes.toString())
    }

    fun deleteNote(note: Note) {
        Log.d("FUCK IT", notes.toString())
        notes.remove(note)
    }

    fun editNote(editedNote: Note) {
        val oldNote = notes.filter { note -> note.id == editedNote.id }[0]
        val index = notes.indexOf(oldNote)

        notes.removeAt(index)
        notes.add(index, editedNote)

        Log.d("FUCK IT", notes.toString())
    }

    fun getNotes(): Flow<List<Note>> = callbackFlow {
        trySend(notes)

        awaitClose {
            close()
        }
    }

    fun getNote(noteId: String): Note {
        Log.d("FUCK IT", notes.toString())
        return notes.filter { note -> note.id == noteId }[0]
    }

    companion object {
        private var instance: PseudoDb? = null
        fun getInstance() = instance ?: PseudoDb().also { instance = it }
    }
}