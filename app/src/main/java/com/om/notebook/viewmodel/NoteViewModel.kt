package com.om.notebook.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.om.notebook.data.Note
import com.om.notebook.data.NoteRepository

class NoteViewModel : ViewModel() {

    private val repository = NoteRepository()


    val notesList = mutableStateOf<List<Note>>(emptyList())
    var selectedNote = mutableStateOf<Note?>(null)


    fun fetchNotes() {
        repository.getNotes { notes ->
            notesList.value = notes
        }
    }

    fun addNote(note: Note) {
        repository.addNote(note)
        fetchNotes()
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
        fetchNotes()
    }

    fun updateNote(note: Note) {
        // DB update
        repository.update(note)

        // 🔥 LIST REFRESH
        fetchNotes()
    }

    fun getNoteById(id: String) {
        val note = notesList.value.find { it.id == id }

        repository.getNoteById(id) { note ->
            selectedNote.value = note
        }
        if (note != null) {
            selectedNote.value = note
        } else {
            // 🔥 अगर list empty है → पहले fetch करो
            fetchNotes()

            // 🔥 retry after fetch
            selectedNote.value = notesList.value.find { it.id == id }
        }
    }
}