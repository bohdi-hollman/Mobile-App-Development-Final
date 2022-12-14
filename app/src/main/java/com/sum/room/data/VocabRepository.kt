package com.sum.room.data

import androidx.lifecycle.LiveData

class VocabRepository(private val vocabDao: VocabDao) {

    val readAllData: LiveData<List<Vocab>> = vocabDao.readAllData()

    suspend fun addVocab(vocab: Vocab){
        vocabDao.addVocab(vocab)
    }
}