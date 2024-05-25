package com.example.poptheballgame
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameplayDataViewModel:ViewModel() {


    private var _score = MutableLiveData<Int>(0)
    private var _misses = MutableLiveData<Int>(0)
    private var _didMiss = MutableLiveData<Boolean>()
    private var _delayValue = MutableLiveData<Long>(1000);

    //getters
    fun score():LiveData<Int>{
        return _score
    }
    fun didMiss():LiveData<Boolean>{
        return _didMiss
    }
    fun misses():LiveData<Int>{
        return _misses
    }
    fun delayValue():LiveData<Long>{
        return _delayValue
    }
    //setters and other methods
    fun updateScore() {
        _score.value = _score.value?.plus(20) ?: 20 // Update score safely
    }
    fun updateMisses() {
        _misses.value = _misses.value?.plus(1) ?: 1 // Update misses safely
    }
    fun resetMisses() {
        _misses.value = 0
    }
    fun setdidMiss(state : Boolean){
        _didMiss.value = state
    }

    fun setDelayLevel(delayVal : Long){
        _delayValue.value = delayVal // Update delay value safely
    }
    // Function to reset the game (optional)
    fun resetGame() {
        _score.value = 0
        _misses.value = 0
    }


}