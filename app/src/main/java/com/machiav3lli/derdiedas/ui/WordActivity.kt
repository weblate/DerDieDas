package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.data.Noun
import com.machiav3lli.derdiedas.databinding.ActivityWordBinding
import com.machiav3lli.derdiedas.utils.DatabaseUtil

class WordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordBinding
    private lateinit var database: DatabaseUtil
    var currentNounList: MutableList<Noun> = mutableListOf()
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordBinding.inflate(layoutInflater)
        database = DatabaseUtil(this)
        Thread {
            currentNounList = database.allNouns
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, WordFragment()).commit()
        }.start()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.back.setOnClickListener { onBackPressed() }
    }

    fun replaceFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        fragmentTransaction.replace(R.id.fragment_container, WordFragment())
        fragmentTransaction.commit()
    }

    fun updateNounList(nounList: MutableList<Noun>) {
        currentNounList = nounList
    }

    override fun onPause() {
        super.onPause()
        Thread {
            DatabaseUtil(this).addAllNouns(currentNounList)
        }.start()
    }
}