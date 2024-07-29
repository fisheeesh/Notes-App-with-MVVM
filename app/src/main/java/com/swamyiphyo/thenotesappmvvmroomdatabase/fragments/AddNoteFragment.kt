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
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.swamyiphyo.thenotesappmvvmroomdatabase.MainActivity
import com.swamyiphyo.thenotesappmvvmroomdatabase.R
import com.swamyiphyo.thenotesappmvvmroomdatabase.databinding.FragmentAddNoteBinding
import com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note
import com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {
    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel

        addNoteView = view
    }

    private fun saveNote(view : View){
        val noteTitle = binding.editTextTitle.text.toString().trim()
        val noteContent = binding.editTextContent.text.toString().trim()

        if(noteTitle.isEmpty() || noteContent.isEmpty()){
            Toast.makeText(addNoteView.context, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
        }
        else{
            val note = Note(0, noteTitle, noteContent)
            noteViewModel.addNote(note)

            Toast.makeText(addNoteView.context, "Noted Saved!", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_save, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.save ->{
                saveNote(addNoteView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }
}