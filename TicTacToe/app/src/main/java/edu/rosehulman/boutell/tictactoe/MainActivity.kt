package edu.rosehulman.boutell.tictactoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val game = TicTacToeGame(this)
    private val tttButtons: Array<Array<Button?>> = Array(TicTacToeGame.NUM_ROWS, { arrayOfNulls<Button>(TicTacToeGame.NUM_COLUMNS)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        game.resetGame()
        for (row in 0 until TicTacToeGame.NUM_ROWS) {
            for (col in 0 until TicTacToeGame.NUM_COLUMNS) {
                val id = resources.getIdentifier("button$row$col", "id", packageName)
                tttButtons[row][col] = findViewById(id)
                tttButtons[row][col]?.setOnClickListener {
                    game.pressedButtonAt(row, col)
                    updateView()
                    Log.d(Constants.TAG, "Pressed button ($row,$col)")
                }
            }
        }

        new_game_button.setOnClickListener {
                game.resetGame()
                updateView()
        }
    }

    private fun updateView() {
        game_state_text_view.text = game.stringForGameState()
        for (row in 0 until TicTacToeGame.NUM_ROWS) {
            for (col in 0 until TicTacToeGame.NUM_COLUMNS) {
                tttButtons[row][col]?.text = game.stringForButtonAt(row, col)
            }
        }
    }
}
