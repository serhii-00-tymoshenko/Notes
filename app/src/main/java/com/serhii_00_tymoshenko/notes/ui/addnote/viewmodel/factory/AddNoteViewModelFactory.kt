package com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel.AddNoteViewModel

class AddNoteViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            return AddNoteViewModel(repository) as T
        }

        throw Error("Wrong ViewModel")
    }
}