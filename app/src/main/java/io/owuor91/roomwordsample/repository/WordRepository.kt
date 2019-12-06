package io.owuor91.roomwordsample.repository

import androidx.lifecycle.LiveData
import io.owuor91.roomwordsample.sql.Word
import io.owuor91.roomwordsample.sql.WordDao

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word){
        wordDao.insertWord(word)
    }
}