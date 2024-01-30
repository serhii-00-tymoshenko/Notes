package com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddNoteViewModel(private val repository: NotesRepository) : ViewModel() {
    fun addNote(editedNote: Note) = viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
        repository.addNote(editedNote)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}