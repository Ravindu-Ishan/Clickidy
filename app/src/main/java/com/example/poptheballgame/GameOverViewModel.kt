package com.example.poptheballgame
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameOverViewModel:ViewModel() {

    private var _finalScore = MutableLiveData<Int>(0)
    private val _previousHighScore = MutableLiveData<Int>(0)

    fun setPreviousHighScore(highScore: Int){
        _previousHighScore.value = highScore
    }

    fun finalScore(): LiveData<Int> {
        return _finalScore
    }

    fun setFinalScore(fnlScore:Int) {
        _finalScore.value = fnlScore
    }

    fun previousHighScore():LiveData<Int>{
        return _previousHighScore
    }

    fun resetData(){
        _finalScore.value = 0
        _previousHighScore.value = 0
    }

}