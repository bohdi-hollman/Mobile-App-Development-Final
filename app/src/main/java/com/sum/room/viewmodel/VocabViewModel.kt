package com.sum.room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.room.data.VocabDatabase
import com.sum.room.repository.VocabRepository
import com.sum.room.model.Vocab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VocabViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Vocab>>
    private val repository: VocabRepository
    var tempList = MutableLiveData<List<Vocab>>()

    init {
        val vocabDao = VocabDatabase.getDatabase(application).vocabDao()
        repository = VocabRepository(vocabDao)
        readAllData = repository.readAllData
    }

    fun addVocab(vocab: Vocab){
        viewModelScope.launch( Dispatchers.IO){
            repository.addVocab(vocab)

        }
    }

    fun updateVocab(vocab: Vocab){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateVocab(vocab)
        }
    }

    fun deleteVocab(vocab: Vocab){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteVocab(vocab)
        }
    }


    fun deleteAllVocab(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllVocab()
        }
    }

    fun searchDatabase(searchQuery:String){
        viewModelScope.launch(Dispatchers.IO){

            tempList.postValue(repository.searchDatabase(searchQuery))
            Log.v("ViewModel",repository.searchDatabase(searchQuery).toString())

        }

    }



}