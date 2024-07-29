package com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note
import com.swamyiphyo.thenotesappmvvmroomdatabase.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app : Application, private val noteRepo : NoteRepository) : AndroidViewModel(app){
    fun addNote(note : Note) =
        viewModelScope.launch {
            noteRepo.insertNote(note)
        }

    fun deleteNote(note : Note) =
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }

    fun updateNote(note : Note) =
        viewModelScope.launch {
            noteRepo.updateNote(note)
        }

    fun getAllNotes() = noteRepo.getAllNotes()

    fun searchNote(query : String?) = noteRepo.searchNotes(query)
}