package com.konarskirob.androidsonar.pro

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.konarskirob.androidsonar.R

class ProActivity : FragmentActivity() {

    private val viewModel: ProViewModel by lazy {
        ViewModelProviders.of(this)[ProViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.up).setOnClickListener {
            viewModel.increment()
        }

        findViewById<Button>(R.id.down).setOnClickListener {
            viewModel.decrement()
        }

        findViewById<Button>(R.id.lock).setOnClickListener {
            viewModel.lock()
        }

        findViewById<Button>(R.id.unlock).setOnClickListener {
            viewModel.unlock()
        }

        val textView = findViewById<TextView>(R.id.counter)

        viewModel.count.observe(this, Observer {
            textView.text = it.toString()
        })
    }
}