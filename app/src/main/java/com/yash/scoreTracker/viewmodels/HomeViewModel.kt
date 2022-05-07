package com.yash.scoreTracker.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yash.scoreTracker.ui.home.Event

class HomeViewModel : ViewModel() {
    private val adv = "AD"
    private val _scoreone = MutableLiveData("0")
    val scoreone: LiveData<String> = _scoreone
    private val _scoretwo = MutableLiveData("0")
    val scoretwo: LiveData<String> = _scoretwo
    private val _playerOneName = MutableLiveData("Yash")
    val playerOneName: LiveData<String> = _playerOneName
    private val _playerTwoName = MutableLiveData("Sam")
    val playerTwoName: LiveData<String> = _playerTwoName
    private val _MaxScore = MutableLiveData(2)
    val MaxScore: LiveData<Int> = _MaxScore
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage
    var tied = MutableLiveData<Boolean>(false)

    //Function to handle score increases
    private fun scoreChange(playerID: PlayerID, action: Action) {
        val currPlayer = when (playerID) {
            PlayerID.ONE -> _scoreone
            PlayerID.TWO -> _scoretwo
        }
        if (tied.value!!) {
            advantagePlay(playerID)
            return
        }
        var temp = currPlayer.value!!.toInt()
        temp = when (action) {
            Action.INC ->
                if (temp != MaxScore.value!!) {
                    temp + 1
                } else {
                    temp
                }
            Action.DEC -> temp - 1
        }

        if (hasWon(temp)) {
            showMessage(playerID = playerID)
        }
        showChanges(playerID, temp)
        checkTie(playerID)
    }

    private fun advantagePlay(playerID: PlayerID) {
        val currPlayer = when (playerID) {
            PlayerID.ONE -> _scoreone
            PlayerID.TWO -> _scoretwo
        }
        if (currPlayer.value.equals(adv)) {
            currPlayer.value = "WON"
            showMessage(playerID)
        } else {
            currPlayer.value = adv
        }
    }

    private fun showMessage(playerID: PlayerID) {
        val name = when (playerID) {
            PlayerID.ONE -> {
                playerOneName.value
            }
            PlayerID.TWO -> {
                playerTwoName.value
            }
        }
        statusMessage.value = Event("$name Has Won")
    }

    private fun showChanges(playerID: PlayerID, temp: Int) {
        val currPlayer = when (playerID) {
            PlayerID.ONE -> _scoreone
            PlayerID.TWO -> _scoretwo
        }
        currPlayer.value = temp.toString()
    }

    fun scoreoneInc() {
        scoreChange(playerID = PlayerID.ONE, Action.INC)
    }

    fun scoreoneDec() {
        scoreChange(playerID = PlayerID.ONE, Action.DEC)
    }

    fun scoretwoInc() {
        scoreChange(playerID = PlayerID.TWO, Action.INC)
    }

    fun scoretwoDec() {
        scoreChange(playerID = PlayerID.TWO, Action.DEC)

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

    //Function to check if the game is tied or not
    fun checkTie(playerID: PlayerID) {
        //Checking if the game is tied or not
        val currPlayer: MutableLiveData<String?> = when (playerID) {
            PlayerID.ONE -> _scoreone
            PlayerID.TWO -> _scoretwo
        }
        val otherPlayer: MutableLiveData<String?> = if (playerID == PlayerID.ONE) {
            _scoretwo
        } else {
            _scoreone
        }
        val s1 = currPlayer.value?.toInt()
        val s2 = otherPlayer.value?.toInt()
        val max = MaxScore.value
        if ((s1 == (max!! - 1) || s2 == (max - 1)) && s1 == s2) {
            tied.value = true
        }
    }

    //Function to check if the player has won or not
    fun hasWon(score: Int): Boolean = score == MaxScore.value
}

//Marking Player ID's for clarity
enum class PlayerID {
    ONE, TWO
}

//Marking Actions to scores
enum class Action {
    INC, DEC
}