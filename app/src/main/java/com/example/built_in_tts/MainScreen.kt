package com.example.built_in_tts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale
import java.util.Locale.KOREA
import java.util.Locale.US

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TextField(value = state.text, onValueChange = {
            viewModel.onTextFieldValueChange(it)
        })

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
            viewModel.textToSpeech(context)
        }, enabled = state.isButtonEnabled
        ) {
            Text(text = "TTS 재생")
        }

        Spacer(modifier = Modifier.height(12.dp))
        RadioGroups(viewModel)
        SeekBarSection(viewModel)
    }
}

@Composable
fun RadioGroups(viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RadioGroup(KOREA, "한국어", viewModel)
        RadioGroup(US, "영어", viewModel)
        voiceSetting(viewModel = viewModel)
    }
}

@Composable
fun RadioGroup(locale: Locale, label: String, viewModel: MainViewModel) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = viewModel.selectedLocale() == locale,
            onClick = {
                viewModel.onLocaleValueChange(locale)
            }
        )

        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun voiceSetting(viewModel: MainViewModel){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                viewModel.voiceSetting()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SeekBarSection(viewModel: MainViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(text = "스피치 속도\n" + viewModel.state.value.speechRate.toString())
        Slider(
            value = viewModel.state.value.speechRate,
            onValueChange = { value ->
                viewModel.onSpeechRateValueChange(value)
            },
            valueRange = 0.5f..4.0f,
            steps = 6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(text = "피치\n" + viewModel.state.value.pitch.toString())
        Slider(
            value = viewModel.state.value.pitch,
            onValueChange = { value ->
                viewModel.onPitchValueChange(value)
            },
            valueRange = 0.5f..4.0f,
            steps = 6
        )
    }
}