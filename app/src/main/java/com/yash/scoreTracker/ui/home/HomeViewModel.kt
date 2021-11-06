package com.yash.scoreTracker.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class HomeViewModel : ViewModel() {

    private val _scoreone = MutableLiveData("0")
    val scoreone: LiveData<String> = _scoreone
    private val _scoretwo = MutableLiveData("1")
    val scoretwo: LiveData<String> = _scoretwo
    private val _playerOneName = MutableLiveData("Yash")
    val playerOneName: LiveData<String> = _playerOneName
    private val _playerTwoName = MutableLiveData("Sam")
    val playerTwoName: LiveData<String> = _playerTwoName
    private val _MaxScore = MutableLiveData(21)
    val MaxScore: LiveData<Int> = _MaxScore
    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    fun scoreoneInc() {
        var temp = scoreone.value?.toInt()
        if (temp!! == MaxScore.value) {
            statusMessage.value = Event("${playerOneName.value.toString()} Has Won ")
            resetScores()
            return
        }
        temp = temp.plus(1)
        _scoreone.value = temp.toString()

    }

    fun scoreoneDec() {
        var temp = scoreone.value?.toInt()
        if (temp!! < 1) {
            return
        }
        temp = temp.minus(1)
        _scoreone.value = temp.toString()
    }

    fun scoretwoInc() {
        var temp = scoretwo.value?.toInt()
        if (temp!!.equals(MaxScore.value)) {
            statusMessage.value = Event("${playerTwoName.value.toString()} Has Won ")
            resetScores()
            return
        }
        temp = temp.plus(1)
        _scoretwo.value = temp.toString()
    }

    fun scoretwoDec() {
        var temp = scoretwo.value?.toInt()
        if (temp!! < 1) {
            return
        }
        temp = temp?.minus(1)
        _scoretwo.value = temp.toString()

    }

    fun resetScores() {
        _scoretwo.value = "0"
        _scoreone.value = "0"
    }

    fun saveNames(name1: String, name2: String) {
        _playerOneName.value = name1
        _playerTwoName.value = name2
    }

    fun setMaxScore(MaxScore: Int) {
        _MaxScore.value = MaxScore
    }
}