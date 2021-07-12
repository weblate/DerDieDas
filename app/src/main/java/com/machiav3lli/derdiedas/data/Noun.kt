package com.machiav3lli.derdiedas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Noun(val noun: String, val gender: String, var timesAnswered: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}