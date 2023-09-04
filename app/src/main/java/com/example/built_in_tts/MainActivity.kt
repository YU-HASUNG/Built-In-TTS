package com.example.built_in_tts

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.built_in_tts.ui.theme.Built_in_ttsTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            Built_in_ttsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
        setObserve()
    }

    private fun setObserve(){
        viewModel.voiceSetting.observe(this) {
            voiceSetting()
        }
    }

    private fun voiceSetting() {
        val intent = Intent()
        intent.action = "com.android.settings.TTS_SETTINGS";
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        startActivity(intent)
    }
}