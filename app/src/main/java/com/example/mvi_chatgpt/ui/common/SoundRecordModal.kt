package com.example.mvi_chatgpt.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundRecordModal(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    message: String = "",
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        properties = ModalBottomSheetDefaults.properties(
            isFocusable = false,
            shouldDismissOnBackPress = false
        ),
        containerColor = Color.White
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SoundAnimation(
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge
            )
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