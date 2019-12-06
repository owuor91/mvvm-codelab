package io.owuor91.roomwordsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.owuor91.roomwordsample.repository.WordRepository
import io.owuor91.roomwordsample.sql.Word
import io.owuor91.roomwordsample.sql.WordDatabase
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {
    private val wordRepository: WordRepository

    init {
       val wordsDao = WordDatabase.getDatabase(application, viewModelScope).wordDao()
        wordRepository = WordRepository(wordsDao)
    }

    fun fetchAllWords(): LiveData<List<Word>>{
        return wordRepository.fetchAllWords()
    }

    fun insert(word: Word) = viewModelScope.launch { wordRepository.insert(word) }
}