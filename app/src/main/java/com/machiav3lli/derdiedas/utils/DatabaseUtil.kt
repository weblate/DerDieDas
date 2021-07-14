package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.data.Noun
import com.machiav3lli.derdiedas.data.NounDao
import com.machiav3lli.derdiedas.data.NounDatabase.Companion.getInstance

class DatabaseUtil(context: Context) {
    private val database: NounDao = getInstance(context).nounDao

    fun addAllNouns(nouns: List<Noun>) {
        database.deleteAll()
        database.insert(nouns)
    }

    val allNouns: MutableList<Noun>
        get() = database.all.toMutableList()

    val nounsCount: Long
        get() = database.count()
}