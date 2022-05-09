package com.yash.scoreTracker.models

import androidx.lifecycle.MutableLiveData



class Player(var playerID: PlayerID, var name: String, var score: Int = 0) {
    var cscore = MutableLiveData<String>(score.toString())
}

enum class PlayerID {
    ONE, TWO
}

enum class Action {
    INC, DEC
}