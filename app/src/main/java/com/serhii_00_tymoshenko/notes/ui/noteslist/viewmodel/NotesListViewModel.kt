package com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class NotesListViewModel(private val repository: NotesRepository) : ViewModel() {
    private val coroutineContext = Dispatchers.IO + SupervisorJob()

    private val notesFlow = MutableSharedFlow<List<Note>>()

    fun getNotes(): Flow<List<Note>> = notesFlow

    init {
        setupFlow()
    }

    private fun setupFlow() = viewModelScope.launch(coroutineContext) {
        repository.getNotes().collect { notes ->
            notesFlow.emit(notes)
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(coroutineContext) {
        repository.deleteNote(note)
    }

    fun addNote() = viewModelScope.launch(coroutineContext) {
        delay(1000)
        repository.addNote(Note("%#$%#$%#"))

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}