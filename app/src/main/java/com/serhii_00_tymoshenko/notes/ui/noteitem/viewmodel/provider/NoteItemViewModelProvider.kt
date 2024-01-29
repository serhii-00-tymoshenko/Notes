package com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel.NoteItemViewModel
import com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel.provider.NoteItemViewModelFactory

class NoteItemViewModelProvider {
    companion object {
        fun getViewModel(owner: ViewModelStoreOwner, repository: NotesRepository, noteId: String) =
            ViewModelProvider(
                owner,
                NoteItemViewModelFactory(repository, noteId)
            )[NoteItemViewModel::class.java]
    }
}