package com.example.pdm2324i_gomoku_g37.screens.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.dtos.GameDto
import com.example.pdm2324i_gomoku_g37.domain.toGame
import com.example.pdm2324i_gomoku_g37.screens.common.GAME_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getGameExtra


/*data class GameActivity(
    val users: Pair<User, User>, val board: Board, val currentPlayer: Player, val rules: Rules
) {
    private fun switchTurn() =
        if (currentPlayer.first == users.first) users.second
        else users.first

    fun computeNewGame(cell: Cell): GameActivity {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(
                board = BoardWin(newBoard.positions, this.currentPlayer, this.rules.boardDim)
            )
        else if (newBoard.checkDraw(this.board.boardSize))
            this.copy(board = BoardDraw(newBoard.positions, this.rules.boardDim))
        else this.copy(
            board = newBoard,
            currentPlayer = Player(this.switchTurn(), this.currentPlayer.second.other())
        )
    }
}*/

class GameActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo, game: GameDto) {
            origin.startActivity(createIntent(origin, userInfo, game))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo, game: GameDto): Intent {
            Log.v("game_activity", game.toString())
            val intent = Intent(ctx, GameActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            intent.putExtra(GAME_EXTRA, game)
            return intent
        }
    }

    private val gameExtra: GameDto by lazy {
        checkNotNull(getGameExtra(intent))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GameScreen(game = gameExtra.toGame())
        }
    }
}