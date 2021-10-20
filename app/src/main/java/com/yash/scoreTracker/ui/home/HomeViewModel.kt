package com.yash.scoreTracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _scoreone = MutableLiveData("0")
    val scoreone: LiveData<String> = _scoreone
    private val _scoretwo = MutableLiveData("1")
    val scoretwo : LiveData<String> = _scoretwo

    fun scoreoneInc(){
        var temp = scoreone.value?.toInt()
        temp = temp?.plus(1)
        _scoreone.value = temp.toString()
    }
    fun scoreoneDec(){
        var temp = scoreone.value?.toInt()
        temp = temp?.minus(1)
        _scoreone.value = temp.toString()
    }
    private fun scoretwoInc(){
        var temp = scoretwo.value?.toInt()
        temp = temp?.plus(1)
        _scoretwo.value = temp.toString()
    }
     fun resetScores(){
        _scoretwo.value = "0"
        _scoreone.value = "0"
    }
}