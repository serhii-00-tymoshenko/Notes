package com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NoteItemViewModel(private val repository: NotesRepository, private val noteId: String) : ViewModel() {
    private val note = MutableLiveData<Note>()
    fun getNote(): LiveData<Note> = note

    init {
        getNote(noteId)
    }

    private fun getNote(noteId: String) = viewModelScope.launch(Dispatchers.IO) {
        val currentNote = repository.getNote(noteId)
        note.postValue(currentNote)
    }
}