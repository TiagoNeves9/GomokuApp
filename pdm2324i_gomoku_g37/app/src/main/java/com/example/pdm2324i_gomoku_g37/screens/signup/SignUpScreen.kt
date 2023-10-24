package com.example.pdm2324i_gomoku_g37.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers

const val SignUpScreenTestTag = "SignUpScreenTestTag"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigation: NavigationHandlers = NavigationHandlers(),
    onHomeRequested: () -> Unit = { }
){
    var textUsername by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(SignUpScreenTestTag),
        topBar = {
            CustomBar(text = stringResource(id = R.string.activity_authors_top_bar_title), navigation)
        },
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
                label = { Text("Password") },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {val image = if (passwordVisibility) Icons.Filled.Lock else Icons.Filled.Face
                    val descriptor = if (passwordVisibility) "Hide Password" else "Show Password"
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = image, contentDescription = descriptor )
                    }
                }
            )

            ElevatedButton(onClick = onHomeRequested) {
                Text(text = "Register")
            }
        }
    }
}

