package io.owuor91.roomwordsample.sql

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): WordDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, WordDatabase::class.java, "word_database")
                        .build()

                //.addCallback(WordDatabaseCallback(coroutineScope))
                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { wordDatabase ->
                scope.launch { }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
            wordDao.insertWord(Word("Alarm"))
            wordDao.insertWord(Word("Calzu"))
            wordDao.insertWord(Word("Queen"))
            wordDao.insertWord(Word("Mango"))
            wordDao.insertWord(Word("Oud"))
        }
    }

}