package com.green.zarryrpg

import android.content.SharedPreferences
import com.google.gson.Gson

object UserFunctions {
    fun fetchUser(data: SharedPreferences): User {
        val gson = Gson()
        val json: String? = data.getString("User", "")
        return gson.fromJson(json, User::class.java)
    }

    fun saveUser(user: User, data: SharedPreferences) {
        val prefsEditor: SharedPreferences.Editor = data.edit()
        val gson = Gson()
        val json: String = gson.toJson(user)
        prefsEditor.putString("User", json)
        prefsEditor.apply()
    }

    fun calculateLevel(user: User): User {

        val levelXp = listOf<Long>(
            0,
            1250,
            3750,
            7250,
            11750,
            17750,
            25250,
            34250,
            45250,
            58250,
            71399,
            98983,
            115359,
            133634,
            154028,
            176787,
            202186,
            230531,
            262164,
            297466,
            336863,
            380830,
            429897,
            484655,
            545764,
            613961,
            690068,
            775003,
            869790,
            975572,
            1093624,
            1225370,
            1372398,
            1536481,
            1700564,
            1883680,
            2088037,
            2316099,
            2570616,
            2854656,
            3171644,
            3525402,
            3920195,
            4360783,
            4852479,
            5401211,
            6013595,
            6697015,
            7459711,
            8310879,
            9260782,
            10320873,
            11503934,
            12824230,
            14297680,
            15942050,
            17777166,
            19825155,
            22110710,
            24661389,
            27507946,
            30684703,
            34229963,
            38186473,
            42601938,
            47529596,
            53666776,
            60515868,
            68159454,
            76689695,
            86209443,
            96833481,
            108689907,
            121921678,
            136688334,
            153167922,
            171559142,
            192083743,
            214989197,
            240551683,
            269079417,
            300916368,
            336446405,
            376097926,
            420349023,
            469733247,
            524846040,
            586351916,
            654992473,
            731595334,
            817084126,
            912489617,
            1018962144,
            1137785484,
            1270392331,
            1418381572,
            1583537564,
            1767851651,
            2000000000
        )
        if (user.level < 99) {
            if (user.xp >= user.nextXp) {
                for (i in 0..levelXp.size) {
                    if (user.xp >= levelXp[i]) {
                        user.nextXp = levelXp[i + 1]
                        user.level = i
                    } else break
                }
            }
        }
        return user
    }
}