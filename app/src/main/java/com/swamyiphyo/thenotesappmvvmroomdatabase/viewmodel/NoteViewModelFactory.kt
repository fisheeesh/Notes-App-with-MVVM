package com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swamyiphyo.thenotesappmvvmroomdatabase.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(val app : Application, private val noteRepo : NoteRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(app, noteRepo) as T

    }
}