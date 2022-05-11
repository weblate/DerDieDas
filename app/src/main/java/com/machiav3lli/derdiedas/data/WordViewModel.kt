package com.machiav3lli.derdiedas.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordViewModel(private val nounDao: NounDao, application: Application) :
    AndroidViewModel(application) {
    var allNouns = MutableLiveData<MutableList<Noun>>()

    init {
        refreshList()
    }

    private fun refreshList() {
        viewModelScope.launch {
            allNouns.value = nounDao.all
        }
    }

    fun updateAllNouns(newList: List<Noun>) {
        viewModelScope.launch {
            allNouns.value = newList.toMutableList()
            updateList(newList)
        }
    }

    private suspend fun updateList(newList: List<Noun>) {
        withContext(Dispatchers.IO) {
            nounDao.deleteAll()
            nounDao.insert(newList)
        }
    }

    class Factory(
        private val database: NounDao,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
                return WordViewModel(database, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}