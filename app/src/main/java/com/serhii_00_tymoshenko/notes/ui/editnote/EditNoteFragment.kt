package com.serhii_00_tymoshenko.notes.ui.editnote

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.databinding.FragmentEditNoteBinding
import com.serhii_00_tymoshenko.notes.repository.NotesRepository
import com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel.EditNoteViewModel
import com.serhii_00_tymoshenko.notes.ui.editnote.viewmodel.factory.EditNoteViewModelFactory
import com.serhii_00_tymoshenko.notes.ui.noteslist.NotesListFragment.Companion.NOTE_LIST_FRAGMENT_NAME
import com.serhii_00_tymoshenko.notes.utils.FileProvider

class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val vieModel by lazy {
        ViewModelProvider(
            this,
            EditNoteViewModelFactory(NotesRepository.getInstance())
        )[EditNoteViewModel::class.java]
    }

    private val note by lazy {
        arguments?.getParcelable(NOTE_ARGUMENT_KEY) ?: Note("Not found")
    }

    private val getPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            photoUri = uri
            Glide.with(requireContext()).load(uri).into(binding.editNote.image)
        }

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            Glide.with(requireContext()).load(tempPhotoUri).into(binding.editNote.image)
            photoUri = FileProvider.getGalleryUri(requireContext(), tempPhotoUri)
        }

    private var tempPhotoUri: Uri? = null
    private var photoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val context = requireContext()

        setContent(context)
        setupFileProvider(context)
        setListeners(activity)
    }

    private fun setupFileProvider(context: Context) {
        tempPhotoUri = FileProvider.getTempUri(context)
    }

    private fun setListeners(activity: FragmentActivity) {
        binding.editNote.apply {
            addPhoto.setOnClickListener {
                getPhotoLauncher.launch("image/*")
            }

            takePhoto.setOnClickListener {
                takePhotoLauncher.launch(tempPhotoUri)
            }

            save.setOnClickListener {
                val title = fieldTitle.editText?.text.toString()
                val content = fieldContent.editText?.text.toString()
                val uri = photoUri

                if (title.isNotEmpty()) {
                    val editedNote = note.copy(title = title, content = content, imageUri = uri)
                    vieModel.editNote(editedNote)
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
        fragmentManager.popBackStack(NOTE_LIST_FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun setContent(context: Context) {
        binding.editNote.apply {
            fieldTitle.editText?.setText(note.title)

            note.content?.let { text ->
                fieldContent.editText?.setText(text)
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
        private const val NOTE_ARGUMENT_KEY = "note_argument"

        @JvmStatic
        fun newInstance(note: Note) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NOTE_ARGUMENT_KEY, note)
                }
            }
    }
}