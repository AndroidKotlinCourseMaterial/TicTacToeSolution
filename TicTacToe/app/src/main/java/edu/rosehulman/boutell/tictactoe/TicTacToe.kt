package edu.rosehulman.boutell.tictactoe


import android.content.Context
import android.content.res.Resources
import android.util.Log

class TicTacToeGame(private val context: Context) {

    private var gameState: GameState? = null

    private var boardArray: Array<IntArray>? = null
    private val isBoardFull: Boolean
        get() {
            for (row in 0 until NUM_ROWS) {
                for (col in 0 until NUM_COLUMNS) {
                    if (this.boardArray!![row][col] == MARK_NONE) {
                        Log.d("TicTacToeGame", "Empty at Row: $row Col: $col")
                        return false
                    }
                }
            }
            return true
        }

    private enum class GameState {
        X_TURN,
        O_TURN,
        X_WIN,
        O_WIN,
        TIE_GAME
    }

    init {
        resetGame()
    }

    fun resetGame() {
        this.boardArray = Array(NUM_ROWS) { IntArray(NUM_COLUMNS) }
        this.gameState = GameState.X_TURN
    }

    fun pressedButtonAtLocation(row: Int, column: Int) {
        if (row < 0 || row >= NUM_ROWS || column < 0 || column >= NUM_COLUMNS)
            return    // Not a valid square location
        if (this.boardArray!![row][column] != MARK_NONE)
            return    // Not empty

        if (this.gameState == GameState.X_TURN) {
            this.boardArray!![row][column] = MARK_X
            this.gameState = GameState.O_TURN
        } else if (this.gameState == GameState.O_TURN) {
            this.boardArray!![row][column] = MARK_O
            this.gameState = GameState.X_TURN
        }
        checkForWin()
    }

    private fun checkForWin() {
        if (!(this.gameState == GameState.X_TURN || this.gameState == GameState.O_TURN))
            return

        if (didPieceWin(MARK_X)) {
            this.gameState = GameState.X_WIN
        } else if (didPieceWin(MARK_O)) {
            this.gameState = GameState.O_WIN
        } else if (isBoardFull) {

            Log.d("TicTacToeGame", "The pattern is full!")
            this.gameState = GameState.TIE_GAME
        }
    }

    private fun didPieceWin(markType: Int): Boolean {
        var allMarksMatch = true
        // Check all the columns for a win
        for (col in 0 until NUM_COLUMNS) {
            allMarksMatch = true
            for (row in 0 until NUM_ROWS) {
                if (this.boardArray!![row][col] != markType) {
                    allMarksMatch = false
                    break
                }
            }
            if (allMarksMatch) return true
        }

        // Check all the rows for a win
        for (row in 0 until NUM_ROWS) {
            allMarksMatch = true
            for (col in 0 until NUM_COLUMNS) {
                if (this.boardArray!![row][col] != markType) {
                    allMarksMatch = false
                    break
                }
            }
            if (allMarksMatch) return true
        }

        // Check down right diagonal
        if (this.boardArray!![0][0] == markType && this.boardArray!![1][1] == markType && this.boardArray!![2][2] == markType)
            return true

        // Check up right diagonal
        return if (this.boardArray!![2][0] == markType && this.boardArray!![1][1] == markType && this.boardArray!![0][2] == markType) true else false

    }

    fun stringForButtonAtLocation(row: Int, column: Int): String {
        val label = ""
        if (row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS) {
            if (this.boardArray!![row][column] == MARK_X) {
                return "X"
            } else if (this.boardArray!![row][column] == MARK_O) {
                return "O"
            }
        }
        return label
    }

    fun stringForGameState(): String {
        var gameStateLabel = ""
        val r = this.context.resources
        when (this.gameState) {
            TicTacToeGame.GameState.X_TURN -> gameStateLabel = r.getString(R.string.x_turn)
            TicTacToeGame.GameState.O_TURN -> gameStateLabel = r.getString(R.string.o_turn)
            TicTacToeGame.GameState.X_WIN -> gameStateLabel = r.getString(R.string.x_win)
            TicTacToeGame.GameState.O_WIN -> gameStateLabel = r.getString(R.string.o_win)
            else -> gameStateLabel = r.getString(R.string.tie_game)
        }
        return gameStateLabel
    }

    companion object {

        val NUM_ROWS = 3
        val NUM_COLUMNS = 3

        private val MARK_NONE = 0
        private val MARK_X = 1
        private val MARK_O = 2
    }
}
