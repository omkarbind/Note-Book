package com.om.notebook.data

import com.google.firebase.firestore.FirebaseFirestore

class NoteRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addNote(note: Note){
        db.collection("notes")
            .add(note)
    }
    fun getNotes(onResult: (List<Note>) -> Unit) {
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                val notes = result.map { it.toObject(Note::class.java) }
                onResult(notes)
            }
            .addOnFailureListener { exception ->
                // error handle karo
                onResult(emptyList())
            }
    }

}

