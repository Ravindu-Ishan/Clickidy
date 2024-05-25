package com.example.poptheballgame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class GameOver : AppCompatActivity() {

    //variables
    private lateinit var congratulatoryMsg: TextView
    private lateinit var viewModel:GameOverViewModel
    private lateinit var previousScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        //view model binding
        viewModel = ViewModelProvider(this)[GameOverViewModel::class.java]

        //load HighScore
        loadHighScore()

        //custom back navigation
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                val intentCustomBack = Intent(this@GameOver, MainActivity::class.java)
                startActivity(intentCustomBack)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        //--------------------------------

        val intent = intent // Get the Intent that started this activity

        //layout bindings
        val btnPlayAgain: Button = findViewById<Button>(R.id.playAgainbtn)
        val btnBackToMenu: Button = findViewById<Button>(R.id.backToMenubtn)
        congratulatoryMsg = findViewById<TextView>(R.id.newHighScore)
        previousScore = findViewById<TextView>(R.id.previousScore)

        //btn actions
        btnPlayAgain.setOnClickListener{
            viewModel.resetData()
            val intentRestart = Intent(this,Gameplay::class.java)
            startActivity(intentRestart)
        }

        btnBackToMenu.setOnClickListener{
            val intentBackToMenu = Intent(this,MainActivity::class.java)
            startActivity(intentBackToMenu)
        }

        congratulatoryMsg.visibility = View.GONE


        // Check if there's an extra with the score
        if (intent.hasExtra("finalScore")) {

            val finalScore = intent.getIntExtra("finalScore", 0) // Get score or default to 0
            viewModel.setFinalScore(finalScore) //set final score in viewModel

            //display final score
            val scoreDisplay: TextView = findViewById(R.id.finalScore)

            viewModel.finalScore().observe(this, Observer {
                scoreDisplay.text = it.toString()

            })

        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.previousHighScore().observe(this, Observer {
            previousScore.text = it.toString()
        })

        //compare current score and previous score from storage
        compareScoreAndSave()

    }

    private fun saveData(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putInt("high_score",viewModel.finalScore().value!!)
        }.apply()
    }

    private fun loadHighScore(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedHighScore: Int = sharedPreferences.getInt("high_score",0)

        viewModel.setPreviousHighScore(savedHighScore)
    }

    private fun compareScoreAndSave(){
        val finalScore = viewModel.finalScore().value ?: 0 // Get finalScore or default to 0
        val previousHighScore = viewModel.previousHighScore().value ?: 0 // Get previousHighScore or default to 0

        if (finalScore > previousHighScore) {
            saveData()
            congratulatoryMsg.visibility = View.VISIBLE
        }


    }

}