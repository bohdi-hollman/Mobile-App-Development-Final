package com.sum.room.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.sum.room.data.VocabDao
import com.sum.room.model.Vocab

class VocabRepository(private val vocabDao: VocabDao) {
    val readAllData: LiveData<List<Vocab>> = vocabDao.readAllData()


    suspend fun addVocab(vocab: Vocab) {
        vocabDao.addVocab(vocab)
    }

    suspend fun updateVocab(vocab: Vocab) {
        vocabDao.updateVocab(vocab)
    }

    suspend fun deleteVocab(vocab: Vocab) {
        vocabDao.deleteVocab(vocab)
    }

    suspend fun deleteAllVocab() {
        vocabDao.deleteAllVocab()
    }

    suspend fun searchDatabase(searchQuery: String): List<Vocab> {
        Log.v("Repo", vocabDao.searchDatabase(searchQuery).toString())
        return vocabDao.searchDatabase(searchQuery)
    }

}