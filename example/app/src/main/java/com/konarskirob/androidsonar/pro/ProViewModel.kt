package com.konarskirob.androidsonar.pro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.konarskirob.androidsonar.extensions.decrementIf
import com.konarskirob.androidsonar.extensions.incrementIf

class ProViewModel : ViewModel() {

    private val countData = MutableLiveData<Int>().apply { value = MIN }
    val count: LiveData<Int> = countData

    var locked = false
        private set

    fun lock() {
        locked = true
    }

    fun unlock() {
        locked = false
    }

    fun increment() {
        if (locked) return

        countData.value = (countData.value ?: 0).incrementIf {
            it < MAX
        }
    }

    fun decrement() {
        if (locked) return

        countData.value = (countData.value ?: 0).decrementIf {
            it > MIN
        }
    }

    companion object {

        private const val MAX = 10
        private val MIN = 0
    }
}