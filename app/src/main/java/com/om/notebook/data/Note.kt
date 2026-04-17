package com.om.notebook.data

data class Note(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val color: Long = 0xFFFFFFFF, // 🔥 background color
    val textColor: Long = 0xFF000000 // 🔥 text color
)
