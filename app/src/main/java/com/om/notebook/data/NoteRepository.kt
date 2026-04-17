package com.om.notebook.data

import com.google.firebase.firestore.FirebaseFirestore

class NoteRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addNote(note: Note) {
        db.collection("notes")
            .add(note)
    }

    fun getNoteById(id: String, onResult: (Note?) -> Unit) {
        db.collection("notes")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val note = document.toObject(Note::class.java)
                    note?.id = document.id
                    onResult(note)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun deleteNote(note: Note) {
        db.collection("notes")
            .document(note.id)
            .delete()
    }

    fun update(note: Note) {
        db.collection("notes")
            .document(note.id)
            .set(note)
    }

    fun getNotes(onResult: (List<Note>) -> Unit) {
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                val notes = mutableListOf<Note>()
                for (document in result) {
                    val note = document.toObject(Note::class.java)
                    note.id = document.id
                    notes.add(note)
                }
                onResult(notes)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }

    }

}