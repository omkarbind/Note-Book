package com.om.notebook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.data.Note
import com.om.notebook.viewmodel.NoteViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
) {

    val notes = viewModel.notesList.value


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchNotes()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔹 Notes List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            items(notes) { note ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = note.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { viewModel.deleteNote(note) }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }

        // 🔥 Floating Button
        FloatingActionButton(
            onClick = {
                navController.navigate("addNote")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 50.dp, end = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Note")
        }
    }


}