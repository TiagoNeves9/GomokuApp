package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onHomeRequested: () -> Unit = {}) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar() }
    ) { padding ->
        CustomContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Title()
            Image(painter = painterResource(id = R.drawable.img_gomoku_icon), contentDescription = null)
            DescriptionContainer()
            ButtonsContainer(onHomeRequested)
        }
    }

@Composable
fun Title() =
    Text(
        text = "Gomoku",
        modifier = Modifier.padding(bottom = 15.dp),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )

@Composable
fun DescriptionContainer() =
    CustomContainer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp, start = 30.dp, end = 30.dp)
    ) {
        Text(
            text = stringResource(id = R.string.activity_main_description),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

@Composable
fun ButtonsContainer(onHomeRequested: () -> Unit = {}) =
    CustomContainer(
        modifier = Modifier
            .padding(top = 15.dp)
    ) {
        CustomButton(onClick = onHomeRequested) {
            Text(
                text = stringResource(id = R.string.activity_main_login),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        CustomButton {
            Text(
                text = stringResource(id = R.string.activity_main_signup),
                fontSize = 20.sp
            )
        }
    }

@Composable
fun BottomBar() =
    CustomContainer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.activity_main_footer),
            textAlign = TextAlign.Center
        )
    }

@Composable
fun CustomButton(onClick: () -> Unit = {}, content: @Composable () -> Unit = {}) {
    Button(
        shape = RoundedCornerShape(4.dp),
        onClick = onClick,
        modifier = Modifier
            .size(width = 200.dp, height = 70.dp)
    ) {
        content()
    }
}

@Composable
fun CustomContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) =
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        content()
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
