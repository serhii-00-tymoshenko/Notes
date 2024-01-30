package com.serhii_00_tymoshenko.notes.repository.pseudodb


import android.database.Observable
import android.net.Uri
import com.serhii_00_tymoshenko.notes.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class PseudoDb : Observable<List<Note>>() {
    private val notes = MutableStateFlow<List<Note>>(emptyList())

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

        notes.update { it.toMutableList().apply { addAll(storedNotes) } }
    }

    fun addNote(note: Note) {
        notes.update { it.toMutableList().apply { add(note) } }
    }

    fun deleteNote(note: Note) {
        notes.update { it.toMutableList().apply { remove(note) } }
    }

    fun editNote(editedNote: Note) {
        notes.update { notes ->
            notes.toMutableList().apply {
                val oldNote = notes.filter { note -> note.id == editedNote.id }[0]
                val index = notes.indexOf(oldNote)

                removeAt(index)
                add(index, editedNote)
            }
        }
    }

    fun getNotes(): Flow<List<Note>> = notes

    companion object {
        private var instance: PseudoDb? = null
        fun getInstance() = instance ?: PseudoDb().also { instance = it }
    }
}