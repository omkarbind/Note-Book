package com.om.notebook.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.data.Note
import com.om.notebook.viewmodel.NoteViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
){
    val notes = viewModel.notesList.value

    LaunchedEffect(Unit){
        viewModel.fetchNotes()
    }
    Column {
        Button(onClick = {
            viewModel.addNote(Note(title = "New Note", description = "This is a new note"))
        }) {
            Text(text = "Add Note")
        }
        LazyColumn{
            items(notes){ note ->
                Text(text = note.title)
            }
        }

    }
}