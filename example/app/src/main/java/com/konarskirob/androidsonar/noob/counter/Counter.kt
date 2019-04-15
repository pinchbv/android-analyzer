package com.konarskirob.androidsonar.noob.counter

interface Counter {

    fun getValue(): Int

    fun increment(by: Int = 1)

    fun decrement(by: Int = 1)

    fun print(message: String)

    fun goCrazy(times: Int)
}