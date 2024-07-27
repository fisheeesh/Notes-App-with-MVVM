package com.swamyiphyo.thenotesappmvvmroomdatabase.database

import androidx.room.Database
import com.swamyiphyo.thenotesappmvvmroomdatabase.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase {
    abstract fun getNoteDao() : NoteDao


    companion object{

    }
}