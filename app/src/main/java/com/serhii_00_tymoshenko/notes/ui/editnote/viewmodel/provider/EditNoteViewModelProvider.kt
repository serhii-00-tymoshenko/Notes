package com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel.EditNoteViewModel
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.NotesListViewModel

class EditNoteViewModelProvider {
    companion object {
        fun getViewModel(owner: ViewModelStoreOwner, repository: NotesRepository) =
            ViewModelProvider(
                owner,
                EditNoteViewModelFactory(repository)
            )[EditNoteViewModel::class.java]
    }
}