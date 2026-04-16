package com.om.notebook.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.data.Note
import com.om.notebook.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
) {

    var title by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    Scaffold(

        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("New Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (noteText.isNotEmpty()) {
                                viewModel.addNote(
                                    Note(
                                        title = if (title.isEmpty()) noteText.take(20) else title,
                                        description = noteText
                                    )
                                )
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔥 Title (no box feel)
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔥 Note Content (full editor)
            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Start writing...") },
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: String,
    oldTitle: String,
    oldDescription: String,
    viewModel: NoteViewModel = viewModel()
) {

    var title by remember { mutableStateOf(oldTitle) }
    var noteText by remember { mutableStateOf(oldDescription) }
    // 🔥 DATA LOAD HERE
    LaunchedEffect(noteId) {
        val note = viewModel.getNoteById(noteId)
        if (note != null) {
            title = note.title
            noteText = note.description
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                actions = {
                    IconButton(
                        onClick = {
                            if (noteText.isNotEmpty()) {
                                viewModel.updateNote(
                                    Note(
                                        id = noteId.toString(),
                                        title = title,
                                        description = noteText
                                    )
                                )
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Update")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Edit note...") },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}