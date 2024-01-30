package com.serhii_00_tymoshenko.notes.ui.noteslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.serhii_00_tymoshenko.notes.R
import com.serhii_00_tymoshenko.notes.databinding.FragmentNotesListBinding
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.addnote.AddNoteFragment
import com.serhii_00_tymoshenko.notes.ui.noteitem.NoteItemFragment
import com.serhii_00_tymoshenko.notes.ui.noteslist.adapters.NotesAdapter
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.NotesListViewModel
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.factory.NotesListViewModelFactory
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            NotesListViewModelFactory(NotesRepository.getInstance())
        )[NotesListViewModel::class.java]
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
        initObservers()
        setListeners(activity)
    }

    private fun setListeners(activity: FragmentActivity) {
        binding.apply {
            addNote.setOnClickListener {
                val addNoteFragment = AddNoteFragment()
                beginTransaction(activity, addNoteFragment)
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.getNotes().collect { notes ->
                notesAdapter.submitList(notes)
            }
        }
    }

    private fun initAdapter(activity: FragmentActivity) {
        notesAdapter = NotesAdapter { note ->
            val noteFragment = NoteItemFragment.newInstance(note)
            beginTransaction(activity, noteFragment)
        }
    }

    private fun beginTransaction(activity: FragmentActivity, fragment: Fragment) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentId = R.id.main_fragment

        fragmentManager.beginTransaction()
            .replace(fragmentId, fragment)
            .addToBackStack(NOTE_LIST_FRAGMENT_NAME)
            .commit()
    }

    private fun setupRecycler(context: Context) {
        val notesRecycler = binding.notesRecycler
        notesRecycler.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        setTouchHelper(notesRecycler)
    }

    private fun setTouchHelper(recycler: RecyclerView) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val notesList = notesAdapter.currentList
                val noteToDelete = notesList[position]

                viewModel.deleteNote(noteToDelete)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NOTE_LIST_FRAGMENT_NAME = "notes_list"
    }
}