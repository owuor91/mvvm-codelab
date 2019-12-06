package io.owuor91.roomwordsample

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.owuor91.roomwordsample.sql.Word
import io.owuor91.roomwordsample.sql.WordDao
import io.owuor91.roomwordsample.sql.WordDatabase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    @get:Rule val instantTaskExecutorRule= InstantTaskExecutorRule()
    private lateinit var wordDao: WordDao
    private lateinit var db: WordDatabase

    @Before fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(context, WordDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        wordDao = db.wordDao()
    }

    @After @Throws(IOException::class) fun closeDb(){
        db.close()
    }

    @Test @Throws(IOException::class) fun insertAndGetWord() = runBlocking{
        val word = Word("word")
        wordDao.insertWord(word)
        val allWords = wordDao.getAlphabetizedWords().waitForValue()
        assertEquals(allWords[0].word, word.word)
    }

    @Test @Throws(IOException::class) fun getAllWords()= runBlocking{
        wordDao.insertWord(Word("Alain"))
        val word = Word("Kounkou")
        wordDao.insertWord(word)
        wordDao.insertWord(Word("Djibril Cisse"))

        val allWords = wordDao.getAlphabetizedWords().waitForValue()
        assertEquals(allWords[2].word, word.word)
        assert(allWords.size==3)
    }

    @Test @Throws(IOException::class) fun deleteAll() = runBlocking {
        wordDao.insertWord(Word("Alain"))
        wordDao.insertWord(Word("Kylian Mbappe"))

        wordDao.deleteAll()
        val allWords = wordDao.getAlphabetizedWords().waitForValue()
        assert(allWords.isEmpty())
    }
}