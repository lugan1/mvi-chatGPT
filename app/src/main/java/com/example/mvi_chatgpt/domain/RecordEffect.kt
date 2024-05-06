package com.example.mvi_chatgpt.domain

sealed interface RecordEffect {
    data object OnReadyForSpeech : RecordEffect
    data object OnBeginningOfSpeech : RecordEffect
    data class OnRmsChanged(val rmsdB: Float) : RecordEffect
    data class OnBufferReceived(val buffer: ByteArray?) : RecordEffect {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnBufferReceived

            if (buffer != null) {
                if (other.buffer == null) return false
                if (!buffer.contentEquals(other.buffer)) return false
            } else if (other.buffer != null) return false

            return true
        }

        override fun hashCode(): Int {
            return buffer?.contentHashCode() ?: 0
        }
    }

    data object OnEndOfSpeech : RecordEffect
    data class OnError(val error: SpeechError) : RecordEffect
    data class OnResults(val results: List<String>?) : RecordEffect
    data object OnPartialResults : RecordEffect
    data object OnEvent : RecordEffect
}