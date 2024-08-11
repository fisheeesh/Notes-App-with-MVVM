package com.swamyiphyo.thenotesappmvvmroomdatabase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.swamyiphyo.thenotesappmvvmroomdatabase.MainActivity
import com.swamyiphyo.thenotesappmvvmroomdatabase.R
import com.swamyiphyo.thenotesappmvvmroomdatabase.databinding.FragmentEditNoteBinding
import com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note
import com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args : EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note!!

        binding.editTextTitleUpdate.setText(currentNote.noteTitle)
        binding.editTextContentUpdate.setText(currentNote.noteContent)

        binding.fabEdit.setOnClickListener {
            val noteTitle = binding.editTextTitleUpdate.text.toString().trim()
            val noteContent = binding.editTextContentUpdate.text.toString().trim()

            if (noteTitle.isEmpty() || noteContent.isEmpty()) {
                Toast.makeText(context, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                val note = Note(currentNote.id, noteTitle, noteContent)
                noteViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment, false)
                Toast.makeText(context, "Note Updated!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note.")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Yes") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
                Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.delete -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}
