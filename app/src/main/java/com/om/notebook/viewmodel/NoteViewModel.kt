package com.om.notebook.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.om.notebook.data.Note
import com.om.notebook.data.NoteRepository

class NoteViewModel: ViewModel() {
    private val repo = NoteRepository()
    val notesList = mutableStateOf<List<Note>>(emptyList())

    fun fetchNotes(){
        repo.getNotes{
            notesList.value = it
        }
    }

    fun addNote(note: Note){
        repo.addNote(note)
        fetchNotes()
    }
}