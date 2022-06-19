package com.green.zarryrpg

data class User(
    var name: String = "Shubham",
    var xp: Long = 0L,
    var level: Int = 0,
    var nextXp: Long = 1250L,
    var money: Long = 0L,
    var stamina: Long = 100L,
    var gold: Int = 0,
    var lastOnline: Long = 0L,
    var lastOnlineStamina: Long = 0L,
    var cook: MutableList<Status> = mutableListOf(
        Status(),
        Status()
    ),
    var brew: MutableList<Status> = mutableListOf(
        Status(),
        Status()
    )
)


