package com.machiav3lli.derdiedas.utils

import android.content.Context
import android.content.res.Resources

fun getStringByName(context: Context, name: String): String? {
    val resId = context.resources.getIdentifier(name, "string", context.packageName)
    return try {
        context.getString(resId)
    } catch (e: Resources.NotFoundException) {
        null
    }
}