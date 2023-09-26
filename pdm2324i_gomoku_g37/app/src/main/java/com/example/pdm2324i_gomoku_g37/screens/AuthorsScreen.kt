package com.example.pdm2324i_gomoku_g37.screens


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
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme
import com.example.pdm2324i_gomoku_g37.ui.theme.SoftRed


@Composable
fun AuthorsScreen() {
    Pdm2324i_gomoku_g37Theme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            var index by remember { mutableStateOf(0) }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Title("Group 37")

                ElevatedCard(
                    colors = CardDefaults.cardColors(containerColor = LightBlue),
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.padding(25.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp)
                    ) {
                        LoadImageByName(authors[index].img)
                    }

                    AuthorDisplay(authors[index])
                    NavigationButtons(index) { newIndex -> index = newIndex }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthorsScreenPreview() {
    AuthorsScreen()
}

@Composable
private fun Title(text: String) =
    Text(
        text = text,
        fontSize = 35.sp,
        modifier = Modifier.padding(bottom = 15.dp)
    )

@Composable
private fun AuthorDisplay(author: Author) =
    Text(
        text = "\t${author.number}\t-\t${author.name}\n" +
                "\t${author.desc}\n",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth()
    )

@Composable
fun NavigationButtons(currentIndex: Int, onNextClick: (Int) -> Unit) =
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
    ) {
        AuthorNavigationButton("Prev") {
            val newIndex = prevIndex(currentIndex)
            onNextClick(newIndex)
        }

        AuthorNavigationButton("Next") {
            val newIndex = nextIndex(currentIndex)
            onNextClick(newIndex)
        }
    }

@Composable
private fun AuthorNavigationButton(text: String, onClick: () -> Unit) =
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SoftRed,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.padding(5.dp)
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
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(200.dp),
        )
    }

}

private fun nextIndex(index: Int): Int =
    if (index == authors.size - 1) 0 else index + 1

private fun prevIndex(index: Int): Int =
    if (index == 0) authors.size - 1 else index - 1