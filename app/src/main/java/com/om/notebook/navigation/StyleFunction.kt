package com.om.notebook.navigation

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.om.notebook.data.TextStyleType

fun getTextStyle(style: TextStyleType): TextStyle {
    return when (style) {
        TextStyleType.NORMAL -> TextStyle.Default
        TextStyleType.BOLD -> TextStyle(fontWeight = FontWeight.Bold)
        TextStyleType.ITALIC -> TextStyle(fontStyle = FontStyle.Italic)
        TextStyleType.BOLD_ITALIC -> TextStyle(
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
        TextStyleType.LARGE -> TextStyle(fontSize = 22.sp)
        TextStyleType.SMALL -> TextStyle(fontSize = 12.sp)
        TextStyleType.HEADING -> TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        TextStyleType.MONOSPACE -> TextStyle(
            fontFamily = FontFamily.Monospace
        )
    }
}