package com.example.mvi_chatgpt.domain

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableSharedFlow

class RecordListener: RecognitionListener {
    val flow = MutableSharedFlow<RecordEffect>()

    override fun onReadyForSpeech(params: Bundle?) {
        flow.tryEmit(RecordEffect.OnReadyForSpeech)
    }

    override fun onBeginningOfSpeech() {
        // 사용자가 말하기 시작
        flow.tryEmit(RecordEffect.OnBeginningOfSpeech)
    }

    override fun onRmsChanged(rmsdB: Float) {
        // 사용자의 음성의 크기가 변경될 때 호출
        flow.tryEmit(RecordEffect.OnRmsChanged(rmsdB))
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        // 말을 시작하고 인식이 된 단어를 buffer에 담음
        flow.tryEmit(RecordEffect.OnBufferReceived(buffer))
    }

    override fun onEndOfSpeech() {
        // 말하기를 중지하면 호출
        // 인식 결과에 따라 onError 또는 onResults 호출
        flow.tryEmit(RecordEffect.OnEndOfSpeech)
    }

    override fun onError(error: Int) {
        when(error) {
            SpeechRecognizer.ERROR_AUDIO -> {
                // 오디오 에러
                flow.tryEmit(RecordEffect.OnError("오디오 에러"))
            }
            SpeechRecognizer.ERROR_CLIENT -> {
                // 클라이언트 에러
                flow.tryEmit(RecordEffect.OnError("클라이언트 에러"))
            }
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> {
                // 권한 부족
                flow.tryEmit(RecordEffect.OnError("권한 부족"))
            }
            SpeechRecognizer.ERROR_NETWORK -> {
                // 네트워크 에러
                flow.tryEmit(RecordEffect.OnError("네트워크 에러"))
            }
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                // 네트워크 타임아웃
                flow.tryEmit(RecordEffect.OnError("네트워크 타임아웃"))
            }
            SpeechRecognizer.ERROR_NO_MATCH -> {
                // 일치하는 결과 없음
                // 녹음을 오래하거나 speechRecognizer.stopListening()을 호출하면 발생하는 에러
                // speechRecognizer를 다시 생성하여 녹음 재개 필요
                flow.tryEmit(RecordEffect.OnError("일치하는 결과 없음"))

            }
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                // 인식기가 바쁨
                flow.tryEmit(RecordEffect.OnError("인식기가 바쁨"))
            }
            SpeechRecognizer.ERROR_SERVER -> {
                // 서버 에러
                flow.tryEmit(RecordEffect.OnError("서버 에러"))
            }
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                // 음성 입력이 없음
                flow.tryEmit(RecordEffect.OnError("음성 입력이 없음"))
            }
            else -> {
                // 기타 에러
                flow.tryEmit(RecordEffect.OnError("기타 에러"))
            }
        }
    }

    override fun onResults(results: Bundle?) {
        // 인식 결과
        val key = SpeechRecognizer.RESULTS_RECOGNITION
        val result = results?.getStringArrayList(key)
        flow.tryEmit(RecordEffect.OnResults(result))
    }

    override fun onPartialResults(partialResults: Bundle?) {
        // 부분 인식 결과를 사용할 수 있을 때 호출
        // 사용자가 말하기를 중지하고 인식이 완료되지 않은 경우 호출
        flow.tryEmit(RecordEffect.OnPartialResults)
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        // 향후 이벤트를 추가하기 위해 예약
        flow.tryEmit(RecordEffect.OnEvent)
    }
}