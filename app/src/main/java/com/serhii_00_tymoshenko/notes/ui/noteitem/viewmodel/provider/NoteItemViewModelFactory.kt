package com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel.NoteItemViewModel
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.NotesListViewModel

class NoteItemViewModelFactory(
    private val repository: NotesRepository,
    private val noteId: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NoteItemViewModel::class.java)) {
            return NoteItemViewModel(repository, noteId) as T
        }

        throw Error("Wrong ViewModel")
    }
}