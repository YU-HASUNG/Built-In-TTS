package com.example.built_in_tts

import java.util.*


data class MainScreenState(
    val isButtonEnabled:Boolean = true,
    val text:String = "테스트 TTS를 재생하겠습니다",
    val locale: Locale = Locale.KOREA,
    val speechRate: Float = 1.0f,
    val pitch: Float = 1.0f,
)