package com.om.notebook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.data.Note
import com.om.notebook.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: String,
    viewModel: NoteViewModel = viewModel()
) {

    val note = viewModel.selectedNote.value

    var title by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    // 🔥 Load note
    LaunchedEffect(noteId) {
        viewModel.getNoteById(noteId)
    }

    // 🔥 Set data in UI
    LaunchedEffect(note) {
        note?.let {
            title = it.title
            noteText = it.description
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
                                        id = noteId,
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
                .fillMaxHeight()
                .padding(padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(10.dp)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )


            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Edit note...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusEvent {
                        if (it.isFocused) {
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
            )
        }
    }
}