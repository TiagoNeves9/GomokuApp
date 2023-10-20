package com.example.pdm2324i_gomoku_g37.screens.authors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.authors
import com.example.pdm2324i_gomoku_g37.resourceMap
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.CustomFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigateBackTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.TopBar
import com.example.pdm2324i_gomoku_g37.ui.theme.LightBlue
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import com.example.pdm2324i_gomoku_g37.ui.theme.SoftRed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(
    authors: List<Author>? = null,
    navigation: NavigationHandlers = NavigationHandlers()
) {
    var index by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box(
                contentAlignment = TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            ) {
                TopBar("Authors", navigation)
            }
        },
        bottomBar = {
            CustomFooterView {
                Text(
                    text = stringResource(id = R.string.activity_main_footer),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LargeCustomTitleView(text = stringResource(id = R.string.activity_authors_group_number))

            ElevatedCard(
                modifier = Modifier.padding(40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                authors?.let { authors ->
                    DisplayAuthor(
                        author = authors[index],
                        nextIndex = { index = nextIndex(index) },
                        prevIndex = { index = prevIndex(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayAuthor(
    author: Author,
    nextIndex: () -> Unit = {},
    prevIndex: () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(25.dp),
        Arrangement.Center
    ) {
        LoadImageByName(author.img)
    }

    CustomContainerView {
        Text(
            text = "\t${author.number}\t-\t${author.name}\n" +
                    "\t${author.desc}\n",
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = { TODO() },
            modifier = Modifier.testTag(NavigateBackTestTag) //CHANGE TAG
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(id = R.string.activity_authors_email),
                modifier = Modifier.size(30.dp)
            )
        }
        NavigationButtons(nextIndex, prevIndex)
    }
}

@Composable
fun Title(text: String) =
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 15.dp),
        fontSize = 35.sp,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )

@Composable
fun NavigationButtons(onNextClick: () -> Unit, onPrevClick: () -> Unit) =
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp, top = 15.dp),
        Arrangement.Center
    ) {
        AuthorNavigationButton(onPrevClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Prev"
            )
        }
        AuthorNavigationButton(onNextClick) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next"
            )
        }
    }

@Composable
private fun AuthorNavigationButton(onClick: () -> Unit, content: @Composable () -> Unit = {}) =
    Button(
        onClick = onClick,
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(10.dp)
    ) {
        content()
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
                .size(150.dp),
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
fun AuthorsScreenPreview() =
    GomokuTheme {
        AuthorsScreen(
            authors = authors,
            NavigationHandlers(onBackRequested = {}, onInfoRequested = {}))
    }