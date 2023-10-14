package com.example.pdm2324i_gomoku_g37.screens.authors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.authors
import com.example.pdm2324i_gomoku_g37.resourceMap
import com.example.pdm2324i_gomoku_g37.ui.theme.LightBlue
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import com.example.pdm2324i_gomoku_g37.ui.theme.SoftRed


@Composable
fun AuthorsScreen() {
    var index by remember { mutableStateOf(0) }

    GomokuTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                Modifier.fillMaxSize(),
                Arrangement.Center,
                Alignment.CenterHorizontally,
            ) {
                Title("Group 37")

                ElevatedCard(
                    modifier = Modifier.padding(25.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = LightBlue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        Arrangement.Center
                    ) {
                        LoadImageByName(authors[index].img)
                    }

                    AuthorDisplay(authors[index])
                    NavigationButtons(
                        { index = nextIndex(index) },
                        { index = prevIndex(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun Title(text: String) =
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 15.dp),
        fontSize = 35.sp
    )

@Composable
private fun AuthorDisplay(author: Author) =
    Text(
        text = "\t${author.number}\t-\t${author.name}\n" +
                "\t${author.desc}\n",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    )

@Composable
fun NavigationButtons(onNextClick: () -> Unit, onPrevClick: () -> Unit) =
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp),
        Arrangement.Center
    ) {
        AuthorNavigationButton("Prev", onPrevClick)
        AuthorNavigationButton("Next", onNextClick)
    }

@Composable
private fun AuthorNavigationButton(text: String, onClick: () -> Unit) =
    Button(
        onClick = onClick,
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SoftRed,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
        )
    }

@Composable
private fun LoadImageByName(imageName: String) {
    val resourceId = resourceMap[imageName]

    resourceId?.let { id ->
        val painter: Painter = painterResource(id = id)

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(200.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
    }
}

private fun nextIndex(index: Int): Int =
    if (index == authors.size - 1) 0 else index + 1

private fun prevIndex(index: Int): Int =
    if (index == 0) authors.size - 1 else index - 1

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthorsScreenPreview() = AuthorsScreen()