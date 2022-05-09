package com.yash.scoreTracker.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yash.scoreTracker.models.Action
import com.yash.scoreTracker.models.Player
import com.yash.scoreTracker.models.PlayerID
import com.yash.scoreTracker.ui.home.Event

class HomeViewModel : ViewModel() {
    val playerOne: MutableLiveData<Player> =
        MutableLiveData<Player>(Player(playerID = PlayerID.ONE, "Yash"))
    val playerTwo: MutableLiveData<Player> =
        MutableLiveData<Player>(Player(playerID = PlayerID.TWO, "Ash"))
    private val _MaxScore = MutableLiveData(2)
    val MaxScore: LiveData<Int> = _MaxScore
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage
    var tied = MutableLiveData<Boolean>(false)
    private fun scoreChange(playerID: PlayerID, action: Action) {
        val currentPlayer = when (playerID) {
            PlayerID.ONE -> playerOne
            PlayerID.TWO -> playerTwo
        }
        val score = when (action) {
            Action.INC -> {
                currentPlayer.value?.score?.plus(1)!!
            }
            Action.DEC -> {
                currentPlayer.value?.score?.minus(1)!!
            }
        }
        if (tied.value!!) {
            advantagePlay(currentPlayer, action)
            currentPlayer.value?.score = score
            return
        }
        if (score <= MaxScore.value!! && score >= 0) {
            currentPlayer.value?.score = score
            Log.d("HVIEWMODEL", currentPlayer.value?.score.toString())
            showChanges(currentPlayer, score)
            if (hasWon(score)) {
                showMessage(player = currentPlayer.value!!)
            }
            checkTie(currentPlayer)
        } else {
            showMessage(player = currentPlayer.value!!)
        }
    }

    private fun advantagePlay(player: MutableLiveData<Player>, action: Action) {
        val otherPlayer = when (player.value?.playerID) {
            PlayerID.ONE -> playerTwo
            PlayerID.TWO -> playerOne
            else -> null
        }
        when (action) {
            Action.DEC -> {
                player.value?.cscore?.value = player.value?.score.toString()
            }
            Action.INC -> {
                if (player.value?.cscore?.value == "AD") {
                    showMessage(player.value!!)
                    player.value?.score = player.value?.score?.plus(1)!!
                    showChanges(player, player.value?.score!!)
                } else {
                    player.value?.score = player.value?.score?.plus(1)!!
                    if (otherPlayer?.value?.score == player.value?.score?.minus(1)) {
                        player.value?.cscore?.value = "AD"
                    } else {
                        if (otherPlayer != null) {
                            otherPlayer.value?.score?.let { showChanges(otherPlayer, it) }
                        }
                        showChanges(player, player.value?.score!!)
                    }
                }
            }
        }
    }

    private fun showMessage(player: Player) {
        statusMessage.value = Event("${player.name} Has Won")
    }

    private fun showChanges(player: MutableLiveData<Player>, score: Int) {
        player.value?.cscore?.postValue(score.toString())
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
        playerOne.value?.score = 0
        playerTwo.value?.score = 0
        playerOne.value?.cscore?.value = "0"
        playerTwo.value?.cscore?.value = "0"
        tied.value = false
    }

    fun saveNames(name1: String, name2: String) {
        playerOne.value?.name = name1
        playerTwo.value?.name = name2
    }

    fun setMaxScore(MaxScore: Int) {
        _MaxScore.value = MaxScore
    }

    fun checkTie(currentPlayer: MutableLiveData<Player>) {
        val otherPlayer = when (currentPlayer.value?.playerID) {
            PlayerID.ONE -> playerTwo
            PlayerID.TWO -> playerOne
            else -> {
                null
            }
        }
        val s1 = currentPlayer.value?.score
        val s2 = otherPlayer?.value?.score
        tied.value = s1 == s2 && (s1 == MaxScore.value?.minus(1) || s2 == MaxScore.value?.minus(1))
    }

    //Function to check if the player has won or not
    fun hasWon(score: Int): Boolean = score == MaxScore.value
}