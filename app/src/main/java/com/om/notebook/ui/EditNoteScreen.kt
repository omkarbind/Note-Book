package com.om.notebook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.data.Note
import com.om.notebook.data.TextStyleType
import com.om.notebook.navigation.getTextStyle
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

    var selectedColor by remember { mutableStateOf(Color.White) }
    var selectedTextColor by remember { mutableStateOf(Color.Black) }
    var selectedStyle by remember { mutableStateOf(TextStyleType.NORMAL) }

    var showColorPicker by remember { mutableStateOf(false) }
    var showStyleDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val colors = listOf(
        Color.White, Color.Yellow, Color.Cyan,
        Color.Green, Color.Magenta, Color.LightGray
    )

    val textColors = listOf(
        Color.Black, Color.Red, Color.Blue, Color.Green
    )

    // 🔥 Load note
    LaunchedEffect(noteId) {
        viewModel.getNoteById(noteId)
    }

    // 🔥 Set UI
    LaunchedEffect(note) {
        note?.let {
            title = it.title
            noteText = it.description
            selectedColor = Color(it.color.toInt())
            selectedTextColor = Color(it.textColor.toInt())

            selectedStyle = try {
                TextStyleType.valueOf(it.style)
            } catch (e: Exception) {
                TextStyleType.NORMAL
            }
        }
    }

    Scaffold(
        containerColor = selectedColor,

        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },

                actions = {

                    // 🎨 COLOR PICKER
                    IconButton(onClick = { showColorPicker = true }) {
                        Icon(Icons.Default.ColorLens, contentDescription = "Color")
                    }

                    // 🅰️ STYLE PICKER
                    IconButton(onClick = { showStyleDialog = true }) {
                        Text("Aa")
                    }

                    // 💾 SAVE
                    IconButton(
                        onClick = {
                            if (noteText.isNotEmpty()) {
                                viewModel.updateNote(
                                    Note(
                                        id = noteId,
                                        title = title,
                                        description = noteText,
                                        color = selectedColor.toArgb().toLong(),
                                        textColor = selectedTextColor.toArgb().toLong(),
                                        style = selectedStyle.name
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
                .padding(5.dp)
                .background(selectedColor)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },

                textStyle = MaterialTheme.typography.titleLarge.merge(
                    getTextStyle(selectedStyle)
                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = selectedColor,
                    unfocusedContainerColor = selectedColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Edit note...") },

                textStyle = MaterialTheme.typography.bodyMedium.merge(
                    getTextStyle(selectedStyle)
                ).copy(
                    color = selectedTextColor
                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = selectedColor,
                    unfocusedContainerColor = selectedColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),

                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // 🎨 COLOR PICKER DIALOG
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
                                        Icons.Default.Check,
                                        null,
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
                                    Icon(Icons.Default.Check, null, tint = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    // 🅰️ STYLE PICKER
    if (showStyleDialog) {
        AlertDialog(
            onDismissRequest = { showStyleDialog = false },
            confirmButton = {},
            title = { Text("Text Style") },
            text = {
                Column {
                    TextStyleType.values().forEach { style ->
                        Text(
                            text = style.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedStyle = style
                                    showStyleDialog = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        )
    }
}