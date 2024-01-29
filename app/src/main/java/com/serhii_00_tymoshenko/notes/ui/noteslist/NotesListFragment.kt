package com.serhii_00_tymoshenko.notes.ui.noteslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.serhii_00_tymoshenko.notes.R
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.databinding.FragmentNotesListBinding
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.noteitem.NoteItemFragment
import com.serhii_00_tymoshenko.notes.ui.noteslist.adapters.NotesAdapter
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.provider.NotesListViewModelProvider

class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        NotesListViewModelProvider.getViewModel(this, NotesRepository())
    }

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val context = requireContext()

        initAdapter(activity)
        setupRecycler(context)
    }

    private fun initAdapter(activity: FragmentActivity) {
        notesAdapter = NotesAdapter { note ->
            beginTransaction(activity, note)
        }
    }

    private fun beginTransaction(activity: FragmentActivity, note: Note) {
        val noteFragment = NoteItemFragment.newInstance(note)

        val fragmentManager = activity.supportFragmentManager
        val fragmentId = R.id.main_fragment

        fragmentManager.beginTransaction()
            .replace(fragmentId, noteFragment)
            .commit()
    }

    private fun setupRecycler(context: Context) {
        val notesRecycler = binding.notesRecycler
        notesRecycler.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}