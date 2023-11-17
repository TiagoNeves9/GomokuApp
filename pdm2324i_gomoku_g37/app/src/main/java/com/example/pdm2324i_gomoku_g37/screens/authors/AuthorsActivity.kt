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
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.info.InfoActivity
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class AuthorsActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthorsScreenViewModel>()
    private val services = FakeGomokuService()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, AuthorsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onStart() {
        viewModel.fetchAuthors(services)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = viewModel.authors,
                    index = viewModel.index,
                    authorsHandlers = AuthorsHandlers(
                        { viewModel.nextIndex() },
                        { viewModel.prevIndex() }
                    ),
                    navigation = NavigationHandlers(
                        onBackRequested = { /*HomeActivity.navigateTo(origin = this)*/ },
                        onInfoRequested = { /*InfoActivity.navigateTo(origin = this)*/ }
                    ),
                    onSendEmailRequested = {
                        viewModel.authors?.get(viewModel.index)?.let { author ->
                            openSendEmail(author.email)
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