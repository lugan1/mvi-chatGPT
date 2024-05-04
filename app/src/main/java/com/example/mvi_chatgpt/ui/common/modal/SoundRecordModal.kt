package com.softnet.temperature.view.ui.component.soundRecordModal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.softnet.temperature.view.ui.component.chat.ChatMessage
import com.softnet.temperature.view.ui.component.chat.ChatMessageType
import com.softnet.temperature.view.ui.component.chat.Message
import com.softnet.temperature.view.ui.theme.TemperatureTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundRecordModal(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    recordState: RecordUiState = RecordUiState(),
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        properties = ModalBottomSheetDefaults.properties(
            isFocusable = false,
            shouldDismissOnBackPress = false
        ),
        containerColor = Color.White
    ) {
        BottomSheetContent(
            modifier = modifier,
            recordState = recordState
        )
    }
}

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    recordState: RecordUiState
) {
    Column(
        modifier
            .fillMaxWidth()
            .heightIn(min = 600.dp)
            .padding(horizontal = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SoundAnimation(modifier = Modifier.size(200.dp))

        Spacer(Modifier.height(30.dp))

        Text(
            text = "[${recordState.systemMessage}]",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        if(recordState.userMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(30.dp))
            Message(chat = ChatMessage(message = recordState.userMessage, type = ChatMessageType.USER))
        }

        if(recordState.botMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Message(chat = ChatMessage(message = recordState.botMessage, type = ChatMessageType.BOT))
        }
    }
}

@Composable
fun SoundAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("sound_lottie.json")
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f
        )
    }

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Preview
@Composable
fun SoundRecordModalPreview() {
    TemperatureTheme {
        BottomSheetContent(
            recordState = RecordUiState(
                systemMessage = "음성을 듣고 있습니다.",
                userMessage = "안녕하세요",
                botMessage = "안녕하세요"
            )
        )
    }
}