package com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel.EditNoteViewModel
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.NotesListViewModel

class EditNoteViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(repository) as T
        }

        throw Error("Wrong ViewModel")
    }
}