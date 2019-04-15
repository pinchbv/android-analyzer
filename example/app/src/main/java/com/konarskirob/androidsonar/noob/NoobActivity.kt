package com.konarskirob.androidsonar.noob

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.konarskirob.androidsonar.R
import com.konarskirob.androidsonar.noob.counter.Counter
import com.konarskirob.androidsonar.noob.counter.CounterImpl

class NoobActivity : FragmentActivity() {

    private lateinit var counter: Counter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.counter)

        counter = CounterImpl { count ->
            textView.text = count.toString()
        }

        findViewById<Button>(R.id.up).setOnClickListener {
            counter.increment()
        }

        findViewById<Button>(R.id.down).setOnClickListener {
            counter.decrement()
        }
    }
}
