package io.owuor91.roomwordsample.repository

import androidx.lifecycle.LiveData
import io.owuor91.roomwordsample.sql.Word
import io.owuor91.roomwordsample.sql.WordDao

class WordRepository(private val wordDao: WordDao) {
    fun fetchAllWords(): LiveData<List<Word>> {
        return wordDao.getAlphabetizedWords()
    }

    suspend fun insert(word: Word) {
        wordDao.insertWord(word)
    }
}