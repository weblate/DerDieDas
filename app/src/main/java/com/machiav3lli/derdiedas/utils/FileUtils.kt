package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException

object FileUtils {
    @JvmStatic
    @Throws(UnsupportedEncodingException::class)
    fun getNounList(context: Context): String {
        val inputStream = context.resources.openRawResource(R.raw.list_nouns)
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        try {
            while (inputStream.read(buffer).also { length = it } != -1) {
                result.write(buffer, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result.toString("UTF-8")
    }

    @JvmStatic
    fun getLines(string: String): Array<String> {
        return string.split("\n").toTypedArray()
    }

    @JvmStatic
    @Throws(UnsupportedEncodingException::class)
    fun getNounsCount(context: Context): Long {
        return getLines(
            getNounList(context)
        ).size.toLong()
    }
}