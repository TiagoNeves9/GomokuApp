package com.example.pdm2324i_gomoku_g37.screens.authors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
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
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorCardTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorEmailButtonTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNextTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorPrevTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNoAuthorTestTag
import com.example.pdm2324i_gomoku_g37.resourceMap
import com.example.pdm2324i_gomoku_g37.screens.components.AUTHOR_IMAGE_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.CARD_ELEVATION
import com.example.pdm2324i_gomoku_g37.screens.components.CARD_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.DEFAULT_RADIUS
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.DEFAULT_CONTENT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.ICON_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.ROW_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

data class AuthorsHandlers(
    val onNextRequested: (() -> Unit)? = null,
    val onPrevRequested: (() -> Unit)? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(
    authors: List<Author>? = null,
    index: Int,
    authorsHandlers: AuthorsHandlers = AuthorsHandlers(),
    navigation: NavigationHandlers = NavigationHandlers(),
    onSendEmailRequested: () -> Unit = { },
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(text = stringResource(id = R.string.activity_authors_top_bar_title),navigation )
        },
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LargeCustomTitleView(text = stringResource(id = R.string.activity_authors_group_number))

            val author = authors?.get(index)

            if (author != null) {
                ElevatedCard(
                    modifier = Modifier
                        .padding(CARD_PADDING)
                        .testTag(AuthorCardTestTag),
                    shape = RoundedCornerShape(DEFAULT_RADIUS),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
                ) {
                    DisplayAuthor(
                        author = author,
                        authorsHandlers = authorsHandlers,
                        onSendEmailRequested = onSendEmailRequested
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.activity_author_no_author_found),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(AuthorNoAuthorTestTag),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DisplayAuthor(
    author: Author,
    authorsHandlers: AuthorsHandlers = AuthorsHandlers(),
    onSendEmailRequested: () -> Unit = { },
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(ROW_DEFAULT_PADDING),
        Arrangement.Center
    ) {
        LoadImageByName(author.img)
    }

    CustomContainerView {
        Text(
            text = "\t${author.number}\t-\t${author.name}\n" +
                    "\t${author.desc}\n",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = { onSendEmailRequested() },
            modifier = Modifier.testTag(AuthorEmailButtonTestTag)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(id = R.string.activity_authors_email),
                modifier = Modifier.size(ICON_SIZE)
            )
        }
        NavigationButtons(authorsHandlers)
    }
}

@Composable
fun NavigationButtons(authorsHandlers: AuthorsHandlers = AuthorsHandlers(),) =
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = ROW_DEFAULT_PADDING, top = ROW_DEFAULT_PADDING),
        Arrangement.Center
    ) {
        authorsHandlers.onPrevRequested?.let {
            AuthorNavigationButton(it, Modifier.testTag(AuthorPrevTestTag)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.activity_authors_previous)
                )
            }
        }
        authorsHandlers.onNextRequested?.let {
            AuthorNavigationButton(it, Modifier.testTag(AuthorNextTestTag)) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(id = R.string.activity_authors_next)
                )
            }
        }
    }

@Composable
private fun AuthorNavigationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) =
    Button(
        onClick = onClick,
        modifier = modifier.padding(BUTTON_DEFAULT_PADDING),
        shape = RoundedCornerShape(DEFAULT_RADIUS),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(DEFAULT_CONTENT_PADDING)
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
            contentDescription = stringResource(id = R.string.activity_authors_image_desc),
            modifier = Modifier
                .clip(CircleShape)
                .size(AUTHOR_IMAGE_SIZE),
            alignment = Center,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AuthorsScreenPreview() {
    val author = Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt")
    GomokuTheme {
        AuthorsScreen(
            authors = listOf(author),
            index = 0,
            AuthorsHandlers({}, {}),
            NavigationHandlers(onBackRequested = {}, onInfoRequested = {})
        )
    }
}
