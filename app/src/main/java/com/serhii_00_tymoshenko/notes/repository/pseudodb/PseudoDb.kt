package com.serhii_00_tymoshenko.notes.repository.pseudodb


import android.net.Uri
import com.serhii_00_tymoshenko.notes.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class PseudoDb {
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

        val tempNotes = notes.value.toMutableList()
        tempNotes.addAll(storedNotes)
        notes.value = tempNotes
    }

    fun addNote(note: Note) {
        val tempNotes = notes.value.toMutableList()
        tempNotes.add(note)
        notes.value = tempNotes
    }

    fun deleteNote(note: Note) {
        val tempNotes = notes.value.toMutableList()
        tempNotes.remove(note)
        notes.value = tempNotes
    }

    fun editNote(editedNote: Note) {
        val tempNotes = notes.value.toMutableList()
        val oldNote = tempNotes.filter { note -> note.id == editedNote.id }[0]
        val index = tempNotes.indexOf(oldNote)

        tempNotes.removeAt(index)
        tempNotes.add(index, editedNote)

        notes.value = tempNotes
    }

    fun getNotes(): Flow<List<Note>> = notes

    companion object {
        private var instance: PseudoDb? = null
        fun getInstance() = instance ?: PseudoDb().also { instance = it }
    }
}