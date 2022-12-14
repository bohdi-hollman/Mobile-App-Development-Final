package com.sum.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sum.room.model.Vocab

@Dao
interface VocabDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVocab(vocab: Vocab)

    @Update
    suspend fun updateVocab(vocab: Vocab)

    @Delete
    suspend fun deleteVocab(vocab: Vocab)

    @Query("DELETE FROM vocab_table")
    suspend fun deleteAllVocab()

    @Query("SELECT * FROM vocab_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Vocab>>

    @Query("SELECT * FROM vocab_table WHERE vocabWord  like '%' || :searchQuery || '%'  ")
    suspend fun searchDatabase(searchQuery: String): List<Vocab>
}