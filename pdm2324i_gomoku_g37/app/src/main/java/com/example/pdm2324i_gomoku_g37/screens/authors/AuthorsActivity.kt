package com.example.pdm2324i_gomoku_g37.screens.authors

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.info.InfoActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class AuthorsActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<AuthorsScreenViewModel> {
        AuthorsScreenViewModel.factory(dependencies.gomokuService)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, AuthorsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.fetchAuthors()
        }

        setContent {
            val currentAuthors by viewModel.authorsList.collectAsState(initial = idle())
            GomokuTheme {
                AuthorsScreen(
                    authors = currentAuthors,
                    index = viewModel.index,
                    authorsHandlers = AuthorsHandlers(
                        { viewModel.nextIndex() },
                        { viewModel.prevIndex() }
                    ),
                    navigation = NavigationHandlers(
                        onBackRequested = { HomeActivity.navigateTo(origin = this) },
                        onInfoRequested = { InfoActivity.navigateTo(origin = this) }
                    ),
                    onSendEmailRequested = {
                        currentAuthors.getOrNull()?.let { authors ->
                            openSendEmail(authors[viewModel.index].email)
                        }
                    }
                )
            }
        }
    }

    private fun openSendEmail(email: String) =
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, R.string.activity_author_email_subject)
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Failed to send email", e)
            Toast
                .makeText(
                    this,
                    R.string.activity_info_no_suitable_app,
                    Toast.LENGTH_LONG
                )
                .show()
        }
}