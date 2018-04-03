package edu.rosehulman.boutell.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mGame: TicTacToeGame = TicTacToeGame(this)
    private val mTicTacToeButtons: Array<Array<Button?>> = Array(3, { arrayOfNulls<Button>(3)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        new_game_button.setOnClickListener {
            mGame.resetGame()
            updateView()
        }

        for (row in 0..2) {
            for (col in 0..2) {
                var id = resources.getIdentifier("button" + row + col, "id", packageName)
                mTicTacToeButtons[row][col] = findViewById(id)
                mTicTacToeButtons[row][col]?.setOnClickListener {
                    mGame.pressedButtonAtLocation(row, col)
                    updateView()
                }
            }
        }



    }

    fun updateView() {
        message_text.text = mGame.stringForGameState()
        for (row in 0..2) {
            for (col in 0..2) {
                mTicTacToeButtons[row][col]?.text = mGame.stringForButtonAtLocation(row, col)
            }
        }
        Log.d(Constants.TAG, "updating")
    }

}
