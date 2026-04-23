package com.om.notebook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.om.notebook.data.TextStyleType
import com.om.notebook.navigation.getTextStyle
import com.om.notebook.utils.Prefs
import com.om.notebook.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
) {

    val notes = viewModel.notesList.value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchNotes()
    }

    Scaffold(

        // 🔥 TOP BAR
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        Prefs.clear(context)

                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                items(notes) { note ->

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

                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleLarge.merge(
                                    getTextStyle(style)
                                ),
                                color = Color(note.textColor.toInt())
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = note.description,
                                style = MaterialTheme.typography.bodyMedium.merge(
                                    getTextStyle(style)
                                ),
                                color = Color(note.textColor.toInt())
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                IconButton(
                                    onClick = {
                                        navController.navigate("editNote/${note.id}")
                                    }
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }

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

            FloatingActionButton(
                onClick = {
                    navController.navigate("addNote")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    }
}