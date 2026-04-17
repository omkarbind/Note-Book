package com.om.notebook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    var selectedColor by remember { mutableStateOf(Color.White) }
    var selectedTextColor by remember { mutableStateOf(Color.Black) }

    var showColorPicker by remember { mutableStateOf(false) }

    val colors = listOf(
        Color.White, Color.Yellow, Color.Cyan,
        Color.Green, Color.Magenta, Color.LightGray
    )

    val textColors = listOf(
        Color.Black, Color.Red, Color.Blue, Color.Green
    )

    Scaffold(
        containerColor = selectedColor,

        topBar = {
            TopAppBar(
                title = { Text("New Note") },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "")
                    }
                },

                actions = {

                    // 🎨 COLOR ICON
                    IconButton(
                        onClick = { showColorPicker = true }
                    ) {
                        Icon(Icons.Default.ColorLens, contentDescription = "Colors")
                    }

                    // 💾 SAVE
                    IconButton(
                        onClick = {
                            if (noteText.isNotEmpty()) {
                                viewModel.addNote(
                                    Note(
                                        title = if (title.isEmpty()) noteText.take(20) else title,
                                        description = noteText,
                                        color = selectedColor.value.toLong(),
                                        textColor = selectedTextColor.value.toLong()
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
                .imePadding()
                .padding(16.dp)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = selectedColor,
                    unfocusedContainerColor = selectedColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Start writing...") },
                textStyle = LocalTextStyle.current.copy(color = selectedTextColor),
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = selectedColor,
                    unfocusedContainerColor = selectedColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }

    // 🎨 POPUP DIALOG
    if (showColorPicker) {
        AlertDialog(
            onDismissRequest = { showColorPicker = false },
            confirmButton = {},

            title = { Text("Choose Color") },

            text = {

                Column {

                    Text("Background Color")

                    Row {
                        colors.forEach { color ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(4.dp)
                                    .background(color, CircleShape)
                                    .clickable { selectedColor = color }
                            ) {
                                if (color == selectedColor) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "",
                                        tint = if (color == Color.White) Color.Black else Color.White
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Text Color")

                    Row {
                        textColors.forEach { color ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(4.dp)
                                    .background(color, CircleShape)
                                    .clickable { selectedTextColor = color }
                            ) {
                                if (color == selectedTextColor) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}