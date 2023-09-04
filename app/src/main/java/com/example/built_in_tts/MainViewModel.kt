package com.example.built_in_tts

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*


class MainViewModel:ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private val _voiceSetting = MutableLiveData<Unit>()
    val voiceSetting: LiveData<Unit> get() = _voiceSetting

    private var textToSpeech:TextToSpeech? = null


    fun onTextFieldValueChange(text:String){
        _state.value = state.value.copy(
            text = text
        )
    }

    fun onLocaleValueChange(locale: Locale){
        _state.value = state.value.copy(
            locale = locale
        )
    }

    fun onSpeechRateValueChange(speechRate: Float){
        _state.value = state.value.copy(
            speechRate = speechRate
        )
    }

    fun onPitchValueChange(pitch: Float){
        _state.value = state.value.copy(
            pitch = pitch
        )
    }

    fun voiceSetting(){
        _voiceSetting.value = Unit
    }

    fun selectedLocale(): Locale {
        return _state.value.locale
    }

    fun textToSpeech(context: Context){
        _state.value = state.value.copy(
            isButtonEnabled = false
        )
       textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->

                    txtToSpeech.language = _state.value.locale
                    txtToSpeech.setSpeechRate(_state.value.speechRate)
                    txtToSpeech.setPitch(_state.value.pitch)
                    txtToSpeech.speak(
                        _state.value.text,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
           _state.value = state.value.copy(
               isButtonEnabled = true
           )
        }
    }
}