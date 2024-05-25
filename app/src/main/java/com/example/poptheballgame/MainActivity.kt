package com.example.poptheballgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlay: Button = findViewById<Button>(R.id.playbtn)
        val btnHighScore: Button = findViewById<Button>(R.id.highScorebtn)
        val btnExit: Button = findViewById<Button>(R.id.exitbtn)

        btnPlay.setOnClickListener{
            val intent = Intent(this,Gameplay::class.java)
            startActivity(intent)
        }

        btnHighScore.setOnClickListener{
            val intent = Intent(this,HighScores::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener{
            finishAffinity()
        }

    }



}