package com.example.poptheballgame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class HighScores : AppCompatActivity() {

    private lateinit var viewModel:HighScoresViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        val savedScoreview : TextView = findViewById(R.id.savedScore)

        //view model binding
        viewModel = ViewModelProvider(this)[HighScoresViewModel::class.java]

        //get high score from storage
        loadHighScore()

        viewModel.highScore().observe(this, Observer {
            savedScoreview.text = it.toString()

        })

        //back btn
        val btnBack : Button = findViewById(R.id.backbtnHighScores)
        btnBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loadHighScore(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedHighScore: Int = sharedPreferences.getInt("high_score",0)

        viewModel.setHighScore(savedHighScore)
    }
}