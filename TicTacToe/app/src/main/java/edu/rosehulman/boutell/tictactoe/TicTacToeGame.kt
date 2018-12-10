package edu.rosehulman.boutell.tictactoe

import android.content.Context

class TicTacToeGame() {

    // There are three options for initializing variables.
    // If you have a value, non-null initial value, use it (what we do here).
    // Otherwise, if you will have one later in the initialization, then you can
    // declare it as lateinit, which tells Kotlin that we promise to
    // give it a value soon.
    // The final option is to give it a nullable type (ending in ?).
    // That means that every time you use that variable, you need to append either
    // a ?, which means to only access the object's fields/methods if the object
    // isn't null, or a !!, which is the hammer to tell Kotlin you know it's not
    // null and it must proceed whether it wants to or not!
    private var board = Array(NUM_ROWS) { IntArray(NUM_COLUMNS) }
    var gameState = GameState.X_TURN

    private lateinit var context: Context

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
        // Don't like to explicitly use -1? Use "until" in your condition
        // or loop (see next function for an example)
        if (row in 0..(NUM_ROWS - 1) && column in 0..(NUM_COLUMNS - 1)) {
            if (board[row][column] == MARK_X) {
                return "X"
            } else if (board[row][column] == MARK_O) {
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
        if (board[row][column] != MARK_NONE) {
            return
        }

        if (gameState == GameState.X_TURN) {
            board[row][column] = MARK_X
            gameState = GameState.O_TURN
        } else if (gameState == GameState.O_TURN) {
            board[row][column] = MARK_O
            gameState = GameState.X_TURN
        }
        checkForWin()
    }

    private fun checkForWin() {
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

    private fun didPieceWin(mark: Int): Boolean {
        for (row in 0 until NUM_ROWS) {
            var winHere = true
            for (col in 0 until NUM_COLUMNS) {
                if (board[row][col] != mark) {
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
                if (board[row][col] != mark) {
                    winHere = false
                }
            }
            if (winHere) {
                return true
            }
        }

        var winHere = true
        for (row in 0 until NUM_ROWS) {
            if (board[row][row] != mark) {
                winHere = false
            }
        }
        if (winHere) {
            return true
        }

        winHere = true
        for (row in 0 until NUM_ROWS) {
            if (board[row][NUM_ROWS-row-1] != mark) {
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
                if (board[row][col] == MARK_NONE) {
                    return false
                }
            }
        }
        return true
    }

    enum class GameState {
        X_TURN,
        O_TURN,
        X_WIN,
        O_WIN,
        TIE_GAME
    }

    // Pulling strings from resources requires a context, so we need
    // an additional constructor.
    fun stringForGameState(): String {
        return when (gameState) {
            GameState.X_WIN -> context.getString(R.string.x_wins)
            GameState.O_WIN -> context.getString(R.string.o_wins)
            GameState.X_TURN -> context.getString(R.string.x_turn)
            GameState.O_TURN -> context.getString(R.string.o_turn)
            else -> context.getString(R.string.tie_game)
        }
    }

    companion object {
        val NUM_ROWS = 3
        val NUM_COLUMNS = 3
        // I probably should have used an enumeration for the marks too.
        private val MARK_NONE = 0
        private val MARK_X = 1
        private val MARK_O = 2
    }

}