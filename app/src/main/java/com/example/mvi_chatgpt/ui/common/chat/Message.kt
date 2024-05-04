package com.softnet.temperature.view.ui.component.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.softnet.temperature.view.ui.theme.Grey

@Composable
fun ColumnScope.Message(chat: ChatMessage) {
    Box(
        modifier = Modifier
            .align(if (chat.isUser) Alignment.End else Alignment.Start)
            .clip(
                RoundedCornerShape(
                    topStart = 48f,
                    topEnd = 48f,
                    bottomStart = if (chat.isUser) 48f else 0f,
                    bottomEnd = if (chat.isUser) 0f else 48f
                )
            )
            .background(if (chat.isUser) MaterialTheme.colorScheme.primary else Grey)
            .padding(16.dp)
    ) {
        Text(
            text = chat.message,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W500,
            color = if (chat.isUser) Color.White else Color.Black
        )
    }
}