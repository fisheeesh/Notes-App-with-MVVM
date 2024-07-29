package com.swamyiphyo.thenotesappmvvmroomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.swamyiphyo.thenotesappmvvmroomdatabase.R
import com.swamyiphyo.thenotesappmvvmroomdatabase.databinding.ListNotesBinding


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val binding : ListNotesBinding) : RecyclerView.ViewHolder(binding.root){
    }
    private val differCallback = object : DiffUtil.ItemCallback<com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note>(){
        override fun areItemsTheSame(
            oldItem: com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note,
            newItem: com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteTitle == newItem.noteTitle &&
                    oldItem.noteContent == newItem.noteContent
        }

        override fun areContentsTheSame(
            oldItem: com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note,
            newItem: com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(ListNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.binding.textViewTitle.text = currentNote.noteTitle
        holder.binding.textViewContent.text = currentNote.noteContent

        holder.itemView.setOnClickListener (){
            it.findNavController().navigate(R.id.action_homeFragment_to_editNoteFragment)
        }
    }
}