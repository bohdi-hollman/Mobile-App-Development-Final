package com.sum.room.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VocabViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Vocab>>
    private val repository: VocabRepository

    init {
        val vocabDao = VocabDatabase.getDatabase(application).vocabDao()
        repository = VocabRepository(vocabDao)
        readAllData = repository.readAllData
    }

    fun addVocab(vocab: Vocab){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVocab(vocab)
        }
    }
}