package com.example.pdm2324i_gomoku_g37.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.utils.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.utils.TopBar


const val LoginScreenTestTag = "LoginScreenTestTag"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun LoginScreen (
    onBackRequested: () -> Unit = { },
    onHomeRequested: () -> Unit = { }
) {
    var textUsername by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LoginScreenTestTag),
        topBar = { TopBar(NavigationHandlers(onBackRequested = onBackRequested)) },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            Arrangement.Center,
            Alignment.CenterHorizontally
        )
        {
            Row {
                OutlinedTextField(
                    value = textUsername,
                    onValueChange = { textUsername = it },
                    label = { Text("Username") })
            }
            OutlinedTextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                label = { Text("Password") })
            ElevatedButton(onClick = onHomeRequested) {
                Text(text = "Login")
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen()
}