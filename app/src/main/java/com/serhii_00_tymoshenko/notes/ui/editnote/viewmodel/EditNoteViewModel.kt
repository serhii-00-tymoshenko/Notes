package com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EditNoteViewModel(private val repository: NotesRepository) : ViewModel() {
    fun editNote(editedNote: Note) = viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
        repository.editNote(editedNote)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}