package com.pinkal.gallery.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.pinkal.gallery.R


class AddActivity : AppCompatActivity() {

    private var editText: EditText? = null
    private var title: String? = null
    private var formattedDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editText = findViewById(R.id.title_text) as EditText?
        val btn = findViewById(R.id.addFinish)

        btn.setOnClickListener {
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formattedDate = df.format(c)
            title = editText!!.text.toString()
            val add = Intent()
            add.putExtra("title", title)
            add.putExtra("time", formattedDate)
            setResult(Activity.RESULT_OK, add)
            finish()
        }
    }
}
