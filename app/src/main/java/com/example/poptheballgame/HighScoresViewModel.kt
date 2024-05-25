package com.example.poptheballgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HighScoresViewModel:ViewModel() {

    private var _highScore = MutableLiveData<Int>(0)

    fun highScore(): LiveData<Int> {
        return _highScore
    }

    fun setHighScore(highScore: Int){
        _highScore.value = highScore
    }
}