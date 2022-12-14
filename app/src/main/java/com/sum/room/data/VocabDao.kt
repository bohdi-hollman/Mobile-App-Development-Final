package com.sum.room.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface VocabDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVocab(vocab: Vocab)

    @Query("SELECT * FROM vocab_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Vocab>>

}