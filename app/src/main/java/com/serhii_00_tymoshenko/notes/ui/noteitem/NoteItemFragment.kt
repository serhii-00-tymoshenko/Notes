package com.serhii_00_tymoshenko.notes.ui.noteitem

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.serhii_00_tymoshenko.notes.R
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.databinding.FragmentNoteItemBinding
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.editnote.EditNoteFragment
import com.serhii_00_tymoshenko.notes.ui.noteitem.viewmodel.provider.NoteItemViewModelProvider

class NoteItemFragment : Fragment() {
    private var _binding: FragmentNoteItemBinding? = null
    private val binding get() = _binding!!

    private val note by lazy {
        arguments?.getParcelable(NOTE_ARGUMENT_KEY) ?: Note("Not found")
    }

    private val viewModel by lazy {
        NoteItemViewModelProvider.getViewModel(this, NotesRepository.getInstance(), note.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val context = requireContext()

        setContent(context, note)
        setListeners(activity)
        initObservers(context)
    }

    private fun initObservers(context: Context) {
        viewModel.getNote().observe(viewLifecycleOwner) { _note ->
            if (_note != note) {
                setContent(context, _note)
            }
        }
    }

    private fun setListeners(activity: FragmentActivity) {
        binding.apply {
            editNote.setOnClickListener {
                beginTransaction(activity)
            }
        }
    }

    private fun beginTransaction(activity: FragmentActivity) {
        val editNoteFragment = EditNoteFragment.newInstance(note)

        val fragmentManager = activity.supportFragmentManager
        val fragmentId = R.id.main_fragment

        fragmentManager.beginTransaction()
            .replace(fragmentId, editNoteFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setContent(context: Context, note: Note) {
        binding.apply {
            title.text = note.title

            note.content?.let { text ->
                content.text = text
            }

            note.imageUri?.let { uri ->
                Glide.with(context).load(uri).into(image)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val NOTE_ARGUMENT_KEY = "note_id_argument"

        @JvmStatic
        fun newInstance(note: Note) =
            NoteItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NOTE_ARGUMENT_KEY, note)
                }
            }
    }
}