package com.om.notebook.data

import com.google.firebase.firestore.FirebaseFirestore

class NoteRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addNote(note: Note) {
        db.collection("notes")
            .add(note)
    }

    fun getNotes(onResult: (List<Note>) -> Unit) {
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                val notes = result.map {
                    val note = it.toObject(Note::class.java)
                    note.id = it.id   // 🔥 ID store karna important
                    note
                }
                onResult(notes)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    fun deleteNote(note: Note) {
        db.collection("notes")
            .document(note.id)
            .delete()
    }

    fun updateNote(note: Note) {
        db.collection("notes")
            .document(note.id)
            .set(note)
    }
}