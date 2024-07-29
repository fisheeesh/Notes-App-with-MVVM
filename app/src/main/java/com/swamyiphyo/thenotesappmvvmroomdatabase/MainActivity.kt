package com.swamyiphyo.thenotesappmvvmroomdatabase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.swamyiphyo.thenotesappmvvmroomdatabase.database.NoteDatabase
import com.swamyiphyo.thenotesappmvvmroomdatabase.databinding.ActivityMainBinding
import com.swamyiphyo.thenotesappmvvmroomdatabase.repository.NoteRepository
import com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel.NoteViewModel
import com.swamyiphyo.thenotesappmvvmroomdatabase.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setUpViewModel()
    }
    private fun setUpViewModel(){
        val noteRepo = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepo)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}