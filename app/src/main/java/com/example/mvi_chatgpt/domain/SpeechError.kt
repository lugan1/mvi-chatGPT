package com.example.mvi_chatgpt.domain

import android.speech.SpeechRecognizer

enum class SpeechError(
    val code: Int,
    val message: String
) {
    ERROR_AUDIO(SpeechRecognizer.ERROR_AUDIO, "오디오 에러가 발생했습니다."),
    ERROR_CLIENT(SpeechRecognizer.ERROR_CLIENT, "클라이언트 에러가 발생했습니다."),
    ERROR_INSUFFICIENT_PERMISSIONS(SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS, "음성녹음 권한이 부족합니다."),
    ERROR_NETWORK(SpeechRecognizer.ERROR_NETWORK, "네트워크 에러가 발생했습니다."),
    ERROR_NETWORK_TIMEOUT(SpeechRecognizer.ERROR_NETWORK_TIMEOUT, "네트워크 타임아웃이 발생했습니다."),
    ERROR_NO_MATCH(SpeechRecognizer.ERROR_NO_MATCH, "일치하는 결과가 없습니다. 다시 시도해주세요."),
    ERROR_RECOGNIZER_BUSY(SpeechRecognizer.ERROR_RECOGNIZER_BUSY, "인식기가 바쁩니다. 다시 시도해주세요."),
    ERROR_SERVER(SpeechRecognizer.ERROR_SERVER, "서버 에러가 발생했습니다."),
    ERROR_SPEECH_TIMEOUT(SpeechRecognizer.ERROR_SPEECH_TIMEOUT, "음성 입력이 없습니다. 다시 시도해주세요."),
    ERROR_UNKNOWN(-1, "알 수 없는 에러가 발생했습니다.");
    companion object {
        fun fromCode(code: Int): SpeechError {
            return entries.find { it.code == code } ?: ERROR_UNKNOWN
        }
    }
}