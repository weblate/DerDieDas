package com.machiav3lli.derdiedas.data

import android.database.SQLException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NounDao {
    @Query("SELECT COUNT(*) FROM noun")
    fun count(): Long

    @Insert
    @Throws(SQLException::class)
    fun insert(vararg nouns: Noun)

    @Insert
    @Throws(SQLException::class)
    fun insert(nouns: List<Noun>)

    @get:Query("SELECT * FROM noun")
    val all: List<Noun>

    @Update
    fun update(noun: Noun)

    @Update
    fun update(nouns: List<Noun>)

    @Query("DELETE FROM noun")
    fun deleteAll()
}