package isel.pdm.pdm2324i_gomoku_g37.screens.authors

import android.util.Log
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
import isel.pdm.pdm2324i_gomoku_g37.R
import isel.pdm.pdm2324i_gomoku_g37.domain.Author
import isel.pdm.pdm2324i_gomoku_g37.domain.LoadState
import isel.pdm.pdm2324i_gomoku_g37.domain.exceptionOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.getOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorCardTestTag
import isel.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorEmailButtonTestTag
import isel.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorFetchAuthorsErrorTag
import isel.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNextTestTag
import isel.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNoAuthorTestTag
import isel.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorPrevTestTag
import isel.pdm.pdm2324i_gomoku_g37.screens.components.AUTHOR_IMAGE_SIZE
import isel.pdm.pdm2324i_gomoku_g37.screens.components.BUTTON_DEFAULT_PADDING
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CARD_ELEVATION
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CARD_PADDING
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomBar
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.DEFAULT_CONTENT_PADDING
import isel.pdm.pdm2324i_gomoku_g37.screens.components.DEFAULT_RADIUS
import isel.pdm.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ICON_SIZE
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import isel.pdm.pdm2324i_gomoku_g37.screens.components.MediumCustomTitleView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ProcessError
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ROW_DEFAULT_PADDING
import isel.pdm.pdm2324i_gomoku_g37.service.GomokuAuthors
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import isel.pdm.pdm2324i_gomoku_g37.utils.resourceMap


data class AuthorsHandlers(
    val onNextRequested: (() -> Unit)? = null,
    val onPrevRequested: (() -> Unit)? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(
    authors: LoadState<List<Author>?> = idle(),
    index: Int = 0,
    authorsHandlers: AuthorsHandlers = AuthorsHandlers(),
    navigation: NavigationHandlers = NavigationHandlers(),
    onSendEmailRequested: () -> Unit = { },
    onDismiss: () -> Unit = { }
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_authors_top_bar_title),
                navigation = navigation
            )
        },
        bottomBar = { GroupFooterView() }
    ) { padding ->
        val customContainerModifier = Modifier
            .fillMaxSize()
            .padding(padding)

        CustomContainerView(modifier = customContainerModifier) {
            require(index >= 0) { "Index must be greater than or equal to 0" }

            MediumCustomTitleView(text = stringResource(R.string.activity_authors_group_number))

            val authorsList = authors.getOrNull()
            if (authorsList.isNullOrEmpty()) {
                val infoModifier = Modifier
                    .fillMaxWidth()
                    .testTag(AuthorNoAuthorTestTag)
                Text(
                    text = stringResource(R.string.activity_author_no_author_found),
                    modifier = infoModifier,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            } else {
                val userModifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(CARD_PADDING)
                        .testTag(AuthorCardTestTag)
                ElevatedCard(
                    modifier = userModifier,
                    shape = RoundedCornerShape(DEFAULT_RADIUS),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
                ) {
                    DisplayAuthor(
                        author = authorsList[index],
                        authorsHandlers = authorsHandlers,
                        onSendEmailRequested = onSendEmailRequested
                    )
                }
            }

            if (authors is isel.pdm.pdm2324i_gomoku_g37.domain.Loading)
                LoadingAlert(R.string.loading_authors_title, R.string.loading_authors_message)

            authors.exceptionOrNull()?.let { cause ->
                cause.message?.let { Log.v(AuthorFetchAuthorsErrorTag, it) }
                ProcessError(onDismiss, cause)
            }
        }
    }

@Composable
private fun DisplayAuthor(
    author: Author,
    authorsHandlers: AuthorsHandlers = AuthorsHandlers(),
    onSendEmailRequested: () -> Unit = { },
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(DEFAULT_CONTENT_PADDING)
    Row(modifier, Arrangement.Center) {
        LoadImageByNumber(author.number)
    }

    CustomContainerView {
        Text(
            text = "\t${author.number}\t-\t${author.name}\n",
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
                contentDescription = stringResource(R.string.activity_authors_email),
                modifier = Modifier.size(ICON_SIZE)
            )
        }

        NavigationButtons(authorsHandlers)
    }
}

@Composable
private fun NavigationButtons(authorsHandlers: AuthorsHandlers = AuthorsHandlers()) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = ROW_DEFAULT_PADDING, top = ROW_DEFAULT_PADDING)

    Row(modifier, Arrangement.Center) {
        authorsHandlers.onPrevRequested?.let { onPrev ->
            AuthorNavigationButton(Modifier.testTag(AuthorPrevTestTag), onPrev) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.activity_authors_previous)
                )
            }
        }
        authorsHandlers.onNextRequested?.let { onNext ->
            AuthorNavigationButton(Modifier.testTag(AuthorNextTestTag), onNext) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.activity_authors_next)
                )
            }
        }
    }
}

@Composable
private fun AuthorNavigationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
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
}

@Composable
private fun LoadImageByNumber(imageNumber: Int) {
    val resourceId = resourceMap[imageNumber]
    resourceId?.let { id ->
        val painter: Painter = painterResource(id)
        val modifier = Modifier
            .clip(CircleShape)
            .size(AUTHOR_IMAGE_SIZE)
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.activity_authors_image_desc),
            modifier = modifier,
            alignment = Center,
            contentScale = ContentScale.Crop,
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AuthorsScreenPreview() {
    GomokuTheme {
        val authors: LoadState<List<Author>?> =
            isel.pdm.pdm2324i_gomoku_g37.domain.loaded(Result.success(GomokuAuthors.authors))
        AuthorsScreen(
            authors = authors,
            index = 0,
            AuthorsHandlers({}, {}),
            NavigationHandlers(onBackRequested = {}, onInfoRequested = {})
        )
    }
}