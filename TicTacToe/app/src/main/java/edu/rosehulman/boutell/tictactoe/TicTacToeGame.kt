package edu.rosehulman.boutell.tictactoe

import android.content.Context

class TicTacToeGame() {

    // Demonstrates two options.
    // (1) Since we must give the array a value here, we make the array nullable
    private var board: Array<IntArray>? = null
    var gameState: GameState? = null
    // (2) Or we can use lateinit, which tells Kotlin that we promise to
    // give it a value later.
    private lateinit var context: Context

    enum class GameState {
        X_TURN,
        O_TURN,
        X_WIN,
        O_WIN,
        TIE_GAME
    }

    init {
        resetGame()
    }

    constructor(context: Context): this() {
        this.context = context
    }

    fun resetGame() {
        gameState = GameState.X_TURN
        board = Array(NUM_ROWS) { IntArray(NUM_COLUMNS) }
    }

    fun stringForButtonAt(row: Int, column: Int): String {
        if (row in 0..(NUM_ROWS - 1) && column in 0..(NUM_COLUMNS - 1)) {
            if (board!![row][column] == MARK_X) {
                return "X"
            } else if (board!![row][column] == MARK_O) {
                return "O"
            }
        }
        return ""
    }

    fun pressedButtonAt(row: Int, column: Int) {
        // If already taken, do nothing
        // Otherwise, set the mark
        if (row !in 0 until NUM_ROWS || column !in 0 until NUM_COLUMNS) {
            return
        }
        if (board!![row][column] != MARK_NONE) {
            return
        }

        if (gameState == GameState.X_TURN) {
            board!![row][column] = MARK_X
            gameState = GameState.O_TURN
            checkForWin()
        } else if (gameState == GameState.O_TURN) {
            board!![row][column] = MARK_O
            gameState = GameState.X_TURN
            checkForWin()
        }
    }

    fun checkForWin() {
        if (gameState != GameState.X_TURN && gameState != GameState.O_TURN) {
            return
        }
        // Check if either piece won.
        if (didPieceWin(MARK_X)) {
            gameState = GameState.X_WIN
        } else if (didPieceWin(MARK_O)) {
            gameState = GameState.O_WIN
        } else if (isBoardFull()) {
            gameState = GameState.TIE_GAME
        }
    }

    fun didPieceWin(mark: Int): Boolean {
        for (row in 0 until NUM_ROWS) {
            var winHere = true
            for (col in 0 until NUM_COLUMNS) {
                if (board!![row][col] != mark) {
                    winHere = false
                }
            }
            if (winHere) {
                return true
            }
        }

        for (col in 0 until NUM_COLUMNS) {
            var winHere = true
            for (row in 0 until NUM_ROWS) {
                if (board!![row][col] != mark) {
                    winHere = false
                }
            }
            if (winHere) {
                return true
            }
        }

        var winHere = true
        for (row in 0 until NUM_ROWS) {
            if (board!![row][row] != mark) {
                winHere = false
            }
        }
        if (winHere) {
            return true
        }
        for (row in 0 until NUM_ROWS) {
            if (board!![row][NUM_ROWS-row-1] != mark) {
                winHere = false
            }
        }
        if (winHere) {
            return true
        }
        return false
    }

    fun isBoardFull(): Boolean {
        for (row in 0 until NUM_ROWS) {
            for (col in 0 until NUM_COLUMNS) {
                if (board!![row][col] == MARK_NONE) {
                    return false
                }
            }
        }
        return true
    }

    companion object {
        val NUM_ROWS = 3
        val NUM_COLUMNS = 3
        private val MARK_NONE = 0
        private val MARK_X = 1
        private val MARK_O = 2
    }

    fun stringForGameState(): String {
        return when (gameState) {
            GameState.X_WIN -> context.getString(R.string.x_wins)
            GameState.O_WIN -> context.getString(R.string.o_wins)
            GameState.X_TURN -> context.getString(R.string.x_turn)
            GameState.O_TURN -> context.getString(R.string.o_turn)
            else -> context.getString(R.string.tie_game)
        }
    }
}