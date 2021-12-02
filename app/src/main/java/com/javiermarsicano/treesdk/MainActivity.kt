package com.javiermarsicano.treesdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.javiermarsicano.tree.Tree

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.log_result_message)

        button = findViewById(R.id.btn_log)
        button.setOnClickListener {
            Tree.log(text.text.toString())
        }
    }
}