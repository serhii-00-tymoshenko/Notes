package com.serhii_00_tymoshenko.notes.ui.noteslist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.serhii_00_tymoshenko.notes.R
import com.serhii_00_tymoshenko.notes.databinding.FragmentNotesListBinding
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.addnote.AddNoteFragment
import com.serhii_00_tymoshenko.notes.ui.noteitem.NoteItemFragment
import com.serhii_00_tymoshenko.notes.ui.noteslist.adapters.NotesAdapter
import com.serhii_00_tymoshenko.notes.ui.noteslist.viewmodel.provider.NotesListViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        NotesListViewModelProvider.getViewModel(this, NotesRepository.getInstance())
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
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getNotes().collect { notes ->
                notesAdapter.submitList(notes)
                Log.d("FUCK IT", notes.toString())
                viewModel.addNote()
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
            .addToBackStack(null)
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