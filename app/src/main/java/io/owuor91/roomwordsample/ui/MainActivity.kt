package io.owuor91.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.owuor91.roomwordsample.R
import io.owuor91.roomwordsample.sql.Word
import io.owuor91.roomwordsample.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel
    lateinit var adapter: WordListAdapter
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRv()
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.fetchAllWords().observe(this, Observer { words ->
            words?.let { adapter.setWords(it) }
        })
        fab.setOnClickListener {
            startActivityForResult(Intent(baseContext, NewWordActivity::class.java), newWordActivityRequestCode)
        }
    }

    private fun setupRv() {
        adapter = WordListAdapter(baseContext)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(baseContext)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}
