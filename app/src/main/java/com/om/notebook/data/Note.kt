package com.om.notebook.data

data class Note(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val color: Long = 0xFFFFFFFF,
    val textColor: Long = 0xFF000000,
    val style: String = "NORMAL"
)