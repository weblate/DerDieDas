package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.data.Noun
import com.machiav3lli.derdiedas.data.NounDatabase
import java.io.UnsupportedEncodingException

fun Context.createNounListFromAsset(): MutableList<Noun> {
    var nounsString: String? = null
    try {
        nounsString = FileUtils.getNounList(this)
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    }
    val nouns = FileUtils.getLines(nounsString!!)
    return nouns.map {
        val noun = it.split(",").toTypedArray()[0]
        val gender = it.split(",").toTypedArray()[1]
        Noun(noun, gender, 0)
    }.toMutableList()
}

fun Context.getNounsCount(): Long {
    val db = NounDatabase.getInstance(this)
    return db.nounDao.count
}
