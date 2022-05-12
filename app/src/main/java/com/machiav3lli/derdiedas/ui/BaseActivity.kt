package com.machiav3lli.derdiedas.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.machiav3lli.derdiedas.utils.wrap

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.wrap())
    }
}