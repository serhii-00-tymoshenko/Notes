package com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.NotesListViewModel

class NotesListViewModelProvider {
    companion object {
        fun getViewModel(owner: ViewModelStoreOwner, repository: NotesRepository) =
            ViewModelProvider(
                owner,
                NotesListViewModelFactory(repository)
            )[NotesListViewModel::class.java]
    }
}