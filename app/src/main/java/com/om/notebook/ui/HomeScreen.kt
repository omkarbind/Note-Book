package com.om.notebook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.data.TextStyleType
import com.om.notebook.navigation.getTextStyle
import com.om.notebook.viewmodel.NoteViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
) {

    val notes = viewModel.notesList.value

    // 🔥 Load data
    LaunchedEffect(Unit) {
        viewModel.fetchNotes()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {

            items(notes) { note ->

                // 🔥 Safe style parsing
                val style = try {
                    TextStyleType.valueOf(note.style)
                } catch (e: Exception) {
                    TextStyleType.NORMAL
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),

                    elevation = CardDefaults.cardElevation(6.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(note.color.toInt())
                    )
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        // 🔥 Title
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleLarge.merge(
                                getTextStyle(style)
                            ),
                            color = Color(note.textColor.toInt())
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // 🔥 Description
                        Text(
                            text = note.description,
                            style = MaterialTheme.typography.bodyMedium.merge(
                                getTextStyle(style)
                            ),
                            color = Color(note.textColor.toInt())
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 🔥 Actions
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            // ✏️ Edit
                            IconButton(
                                onClick = {
                                    navController.navigate("editNote/${note.id}")
                                }
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }

                            // 🗑 Delete
                            IconButton(
                                onClick = {
                                    viewModel.deleteNote(note)
                                }
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