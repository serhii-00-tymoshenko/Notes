package com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.NotesListViewModel

class NotesListViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            return NotesListViewModel(repository) as T
        }

        throw Error("Wrong ViewModel")
    }
}