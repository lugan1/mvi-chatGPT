package com.example.mvi_chatgpt.ui.activity

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mvi_chatgpt.domain.RecordEffect
import com.example.mvi_chatgpt.domain.RecordListener
import com.example.mvi_chatgpt.ui.common.PermissionActivity
import com.example.mvi_chatgpt.ui.navigation.AppNavigation
import com.example.mvi_chatgpt.ui.theme.MVIChatGPTTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNot

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIChatGPTTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun Test() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        var isShowPermission by remember { mutableStateOf(false) }
        var isGranted by remember { mutableStateOf(false) }
        var isRecording by remember { mutableStateOf(false) }
        var result by remember { mutableStateOf<String?>(null) }
        var recordStatus by remember { mutableStateOf<RecordEffect?>(null) }

        val listener = remember { RecordListener() }
        val speechRecognizer = remember {
            SpeechRecognizer.createSpeechRecognizer(context)
                .apply { setRecognitionListener(listener) }
        }


        LaunchedEffect(key1 = Unit) {
            listener.flow
                .filterNot { effect -> effect is RecordEffect.OnRmsChanged }
                .collect { effect ->
                    recordStatus = effect

                    when(effect) {
                        is RecordEffect.OnResults -> {
                            result = effect.results?.firstOrNull() ?: ""
                        }

                        is RecordEffect.OnError -> {
                            Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                        }

                        is RecordEffect.OnEndOfSpeech -> {
                            isRecording = false
                        }

                        else -> Unit
                    }
                }
        }

        if(isShowPermission) {
            PermissionActivity(permission = android.Manifest.permission.RECORD_AUDIO) {
                isShowPermission = false
                isGranted = it
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "isRecording: $isRecording")

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "record status: $recordStatus")

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "result: $result")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            isShowPermission = true
        }) {
            Text("Request Permission")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if(isGranted && isRecording.not()) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
                speechRecognizer.startListening(intent)
                isRecording = true
            }
        }) {
            Text(text = "Started Recording")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if(isRecording) {
                speechRecognizer.stopListening()
                isRecording = false
            }
        }) {
            Text(text = "Stop Recording")
        }
    }
}