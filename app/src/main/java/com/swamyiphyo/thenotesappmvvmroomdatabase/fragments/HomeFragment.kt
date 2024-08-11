package com.swamyiphyo.thenotesappmvvmroomdatabase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.swamyiphyo.thenotesappmvvmroomdatabase.MainActivity
import com.swamyiphyo.thenotesappmvvmroomdatabase.R
import com.swamyiphyo.thenotesappmvvmroomdatabase.adapter.NoteAdapter
import com.swamyiphyo.thenotesappmvvmroomdatabase.databinding.FragmentHomeBinding
import com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note
import com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()

        binding.fabAddNote.setOnClickListener(){
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private fun updateUI(notes: List<Note>){
        if(notes != null){
            if(notes.isNotEmpty()){
                binding.mainRV.visibility = View.VISIBLE
                binding.imageViewEmpty.visibility = View.GONE
            }
            else{
                binding.mainRV.visibility = View.GONE
                binding.imageViewEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpRecyclerView(){
        val staggeredLayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        noteAdapter = NoteAdapter()

        binding.mainRV.apply {
            layoutManager = staggeredLayout
            adapter = noteAdapter
            setHasFixedSize(true)
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner){
                    note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    private fun searchNote(query: String?){
        val searchQuery = "%$query%"

        noteViewModel.searchNote(searchQuery).observe(this){
            list ->
            noteAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_search, menu)

        val search = menu.findItem(R.id.search).actionView as SearchView
        search.isSubmitButtonEnabled = false
        search.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}