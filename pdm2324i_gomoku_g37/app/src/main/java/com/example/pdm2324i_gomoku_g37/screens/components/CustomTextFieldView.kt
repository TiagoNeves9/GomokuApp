package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.pdm2324i_gomoku_g37.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameTextFieldView(
    value: String,
    isError: Boolean = false,
    errorText: String = "",
    onClick: (String) -> Unit = {}
) =
    OutlinedTextField(
        value = value,
        onValueChange = onClick,
        label = { Text(text = stringResource(R.string.activity_login_sign_up_input_username)) },
        singleLine = true,
        isError = isError,
        supportingText = {
             if (isError)
                 Text(text = errorText, color = MaterialTheme.colorScheme.error)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(R.string.activity_login_sign_up_input_username)
            )
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
        }
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldView(
    value: String,
    isError: Boolean = false,
    errorText: String = "",
    passwordVisibility: Boolean,
    enablePasswordVisibility: (Boolean) -> Unit = {},
    isRepeatPassword: Boolean,
    onClick: (String) -> Unit
) =
    OutlinedTextField(
        value = value,
        onValueChange = onClick,
        label = {
            if (isRepeatPassword) Text(text = stringResource(R.string.activity_sign_up_input_pass_rep))
            else Text(text = stringResource(R.string.activity_login_sign_up_input_password))
        },
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError)
                Text(text = errorText, color = MaterialTheme.colorScheme.error)
        },
        visualTransformation = onVisualTransformation(passwordVisibility),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = stringResource(id = R.string.activity_login_sign_up_input_password)
            )
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isError)
                    Icon(
                        Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error
                    )
                PasswordVisibilityView(passwordVisibility, enablePasswordVisibility)
            }
        }
    )

private fun onVisualTransformation(passwordVisibility: Boolean) =
    if (passwordVisibility) VisualTransformation.None
    else PasswordVisualTransformation()

@Composable
fun PasswordVisibilityView(passwordVisibility: Boolean, onClick: (Boolean) -> Unit = {}) {
    val image = if (passwordVisibility) R.drawable.password_hide
                else R.drawable.password_show

    val descriptor = if (passwordVisibility) "Hide Password"
                     else "Show Password"
    IconButton(onClick = { onClick(!passwordVisibility) }) {
        Image(
            painter = painterResource(image),
            contentDescription = descriptor,
            modifier = Modifier.size(PASSWORD_SHOW_HIDE_ICON_SIZE)
        )
    }
}