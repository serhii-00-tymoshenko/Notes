package com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel.AddNoteViewModel

class AddNoteViewModelProvider {
    companion object {
        fun getViewModel(owner: ViewModelStoreOwner, repository: NotesRepository) =
            ViewModelProvider(
                owner,
                AddNoteViewModelFactory(repository)
            )[AddNoteViewModel::class.java]
    }
}