package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.authors.Title


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onHomeRequested: () -> Unit = {}) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Title(text = "Gomoku")
            Image(painter = painterResource(id = R.drawable.img_gomoku_icon), contentDescription = null)
            Button(
                shape = RoundedCornerShape(4.dp),
                onClick = onHomeRequested,
                modifier = Modifier
                    .size(width = 200.dp, height = 70.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                shape = RoundedCornerShape(4.dp),
                onClick = {},
                modifier = Modifier
                    .size(width = 200.dp, height = 70.dp)
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}