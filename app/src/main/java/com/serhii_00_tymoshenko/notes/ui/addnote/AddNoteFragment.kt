package com.serhii_00_tymoshenko.notes.ui.addnote

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.databinding.FragmentAddNoteBinding
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel.AddNoteViewModel
import com.serhii_00_tymoshenko.notes.ui.addnote.viewmodel.factory.AddNoteViewModelFactory

class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val vieModel by lazy {
        ViewModelProvider(
            this,
            AddNoteViewModelFactory(NotesRepository.getInstance())
        )[AddNoteViewModel::class.java]
    }

    private var photoUri: Uri? = null

    private val getPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            photoUri = uri
            Glide.with(requireContext()).load(uri).into(binding.addNote.image)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        setListeners(activity)
    }

    private fun setListeners(activity: FragmentActivity) {
        binding.addNote.apply {
            addPhoto.setOnClickListener {
                getPhotoLauncher.launch("image/*")
            }

            save.setOnClickListener {
                val title = fieldTitle.editText?.text.toString()
                val content = fieldContent.editText?.text.toString()
                val uri = photoUri

                if (title.isNotEmpty()) {
                    val editedNote = Note(title, content, uri)
                    vieModel.addNote(editedNote)
                    beginTransaction(activity)
                } else {
                    Toast.makeText(activity, "Enter title", Toast.LENGTH_SHORT).show()
                }
            }

            removePhoto.setOnClickListener {
                photoUri = null
                Glide.with(activity).load(photoUri).into(image)
            }
        }
    }

    private fun beginTransaction(activity: FragmentActivity) {
        val fragmentManager = activity.supportFragmentManager
        fragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}