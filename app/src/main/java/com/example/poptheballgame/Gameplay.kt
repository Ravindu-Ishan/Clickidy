package com.example.poptheballgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.Random

class Gameplay : AppCompatActivity() {

    private lateinit var target: ImageButton
    private lateinit var handler : Handler
    private lateinit var scoreDisplay : TextView
    private lateinit var missesDisplay: TextView
    private var screenWidth = 0
    private var screenHeight = 0
    private lateinit var viewModel:GameplayDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)

        scoreDisplay =findViewById(R.id.updateScore)
        missesDisplay = findViewById(R.id.updateMisses)

        viewModel = ViewModelProvider(this)[GameplayDataViewModel::class.java]

        viewModel.score().observe(this, Observer {
            scoreDisplay.text = it.toString()
        })

        viewModel.misses().observe(this, Observer {
            missesDisplay.text = it.toString()
        })

        //set context to handler
        handler = Handler(applicationContext.mainLooper)

        //get button
        target = findViewById(R.id.targetbtn)

        //initialize button hidden
        target.visibility = View.GONE

        // Get screen size using getDisplayMetrics
        val displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

    }


    override fun onStart() {

        super.onStart()
        // Schedule button movement with a delay
        handler.postDelayed({
            target.visibility = View.VISIBLE // Make button visible after delay
        }, 1000) // Delay for 2 seconds

        moveButton()

        target.setOnClickListener{
            viewModel.updateScore() // Increase score on click
            viewModel.resetMisses() // reset misses
            viewModel.setdidMiss(false) //set missed state to false

            //setLevel
            when(viewModel.score().value)
            {
                100 -> viewModel.setDelayLevel(800)
                200 -> viewModel.setDelayLevel(680)
                460 -> viewModel.setDelayLevel(580)
                1000 -> viewModel.setDelayLevel(480)
                1500 -> viewModel.setDelayLevel(380)

            }
        }

    }

    private fun moveButton() {

            viewModel.setdidMiss(true) //initialize missed stated

            val random = Random()

            // Calculate random positions within screen bounds

            val dx = random.nextFloat() * (screenWidth - target.width)
            val dy = random.nextFloat() * (screenHeight - target.height)

            // Animate button position
            target.animate()
                .x(dx)
                .y(dy)
                .setDuration(0) // Instantaneous movement
                .start()


            // After animation completes, reschedule moveButton with a delay
            handler.postDelayed({
                //before animating the next button, check if player missed
                if(viewModel.misses().value!! < 3)
                {
                    if(viewModel.didMiss().value!!)
                    {
                        viewModel.updateMisses()
                        moveButton()
                    }
                    else{
                        moveButton()
                    }
                }
                else{
                    val finalScore = viewModel.score().value!!
                    val intent = Intent(this,GameOver::class.java)
                    intent.putExtra("finalScore", finalScore)
                    startActivity(intent)
                }


            }, viewModel.delayValue().value!!) // Repeat every 1 second

    }

}
