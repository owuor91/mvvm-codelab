package io.owuor91.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import io.owuor91.roomwordsample.R
import kotlinx.android.synthetic.main.activity_new_word.*

class NewWordActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        button_save.setOnClickListener {
            val intent = Intent()
            val input = edit_word.text.toString()
            if (TextUtils.isEmpty(input)) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                intent.putExtra(EXTRA_REPLY, input)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}
