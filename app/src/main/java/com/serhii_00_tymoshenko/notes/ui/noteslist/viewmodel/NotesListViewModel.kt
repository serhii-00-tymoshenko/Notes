package com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesListViewModel(private val repository: NotesRepository) : ViewModel() {
    private val coroutineContext = Dispatchers.IO + SupervisorJob()

    private val notesFlow = MutableStateFlow(emptyList<Note>())
    fun getNotes(): Flow<List<Note>> = notesFlow

    init {
        setupFlow()
    }

    private fun setupFlow() = viewModelScope.launch(coroutineContext) {
        repository.getNotes()
            .collect() { notes ->
                notesFlow.update { notes }
            }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(coroutineContext) {
        repository.deleteNote(note)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}