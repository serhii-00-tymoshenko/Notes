package com.serhii_00_tymoshenko.notes.ui.noteslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.serhii_00_tymoshenko.notes.data.Note
import com.serhii_00_tymoshenko.notes.databinding.ItemNoteCompactBinding

class NotesAdapter(
    private val callback: (Note) -> Unit
): ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteCompactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
    }

    inner class NoteViewHolder(private val binding: ItemNoteCompactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            setCallback(note)
            setContent(note)
        }

        private fun setCallback(note: Note) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    callback.invoke(note)
                }
            }
        }

        private fun setContent(note: Note) {
            binding.apply {
                textTitle.text = note.title

                note.content?.let { text ->
                    textContent.text = text
                }

                note.imageUri?.let { uri ->
                    Glide.with(root).load(uri).into(photo)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note) =
                oldItem == newItem
        }
    }
}