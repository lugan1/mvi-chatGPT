package com.example.mvi_chatgpt.ui.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mvi_chatgpt.R
import com.example.mvi_chatgpt.ui.common.Keyboard
import com.example.mvi_chatgpt.ui.common.PermissionActivity
import com.example.mvi_chatgpt.ui.common.chat.ChatMessageType
import com.example.mvi_chatgpt.ui.common.chat.UiChatMessage
import com.example.mvi_chatgpt.ui.common.chat.defaultMessages
import com.example.mvi_chatgpt.ui.common.keyboardAsState
import com.example.mvi_chatgpt.ui.theme.Grey
import com.example.mvi_chatgpt.ui.theme.MVIChatGPTTheme
import com.softnet.temperature.view.ui.component.soundRecordModal.SoundRecordModal
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatContract.State,
    effectFlow: Flow<ChatContract.Effect>?,
    onEvent: (ChatContract.Event) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        effectFlow?.collect { effect ->
            //todo: Side effect 처리
        }
    }

    if(state.isShowPermission) {
        PermissionActivity(permission = android.Manifest.permission.RECORD_AUDIO) { isGranted ->
            onEvent(ChatContract.Event.DismissPermission(isGranted))
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
                 CenterAlignedTopAppBar(
                     title = { Text(text = "챗봇") },
                     actions = {
                         IconButton(onClick = { onEvent(ChatContract.Event.RequestPermission) }) {
                             Icon(modifier= Modifier.size(20.dp),
                                 painter = painterResource(id = R.drawable.icon_microphone),
                                 contentDescription = "녹음"
                             )
                         }
                     }
                 )
        },
        containerColor = Color.White
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            val (messages, chatBox) = createRefs()
            ChatBotContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(messages) {
                        top.linkTo(parent.top)
                        bottom.linkTo(chatBox.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                    .padding(top = 33.dp)
                    .padding(horizontal = 24.dp),
                state = state,
                onEvent = onEvent
            )

            ChatBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(chatBox) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                message = state.inputMessage,
                onMessageChange = { onEvent(ChatContract.Event.InputChange(it)) },
                onSend = { onEvent(ChatContract.Event.Send) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotContent(
    modifier: Modifier = Modifier,
    state: ChatContract.State,
    onEvent: (ChatContract.Event) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val keyboardState = keyboardAsState()
    LaunchedEffect(state.messageList.size, keyboardState.value == Keyboard.Opened) {
        lazyListState.animateScrollToItem(state.messageList.size)
    }
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.robot),
                    contentDescription = "챗봇"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "고객님, 무엇을 도와드릴까요?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(44.dp))
                val defaultMessages = remember { defaultMessages() }
                defaultMessages.forEach {
                    Message(chat = it)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        items(state.messageList.size) { index ->
            val message = state.messageList[index]
            Column(modifier = Modifier.fillMaxWidth()) {
                Message(chat = message)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }

        item {
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
            if (state.isRecording) {
                SoundRecordModal(
                    modifier = Modifier.height(600.dp),
                    sheetState = sheetState,
                    recordState = state.recordState,
                    onDismissRequest = { onEvent(ChatContract.Event.DismissBottomSheet) },
                )
            }
        }
    }
}


@Composable
fun ColumnScope.Message(chat: UiChatMessage) {
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



@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    message: String = "",
    onMessageChange: (String) -> Unit = {},
    onSend: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            value = message,
            onValueChange = onMessageChange,
            placeholder = { Text("메시지를 입력하세요") },
        )
        IconButton(onClick = onSend) {
            Icon(
                painter = painterResource(id = R.drawable.icon_send),
                contentDescription = "보내기"
            )
        }
    }
}

@Preview
@Composable
fun MessagePreview() {
    val chat = defaultMessages()
    Column(modifier = Modifier.fillMaxWidth()) {
        Message(chat = chat[0])
        Spacer(modifier = Modifier.height(8.dp))
        Message(chat = chat[1])
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    MVIChatGPTTheme {
        ChatScreen(
            state = ChatContract.State(
                isLoading = false,
                messageList = listOf(
                    UiChatMessage("안녕 GPT야", ChatMessageType.USER),
                ),
            ),
            effectFlow = null,
            onEvent = {},
        )
    }
}