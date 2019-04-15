package com.konarskirob.androidsonar.noob.counter

class CounterImpl(initialCount: Int = 0, private val onChange: ((Int) -> Unit)? = null) : Counter {

    private var value = initialCount

    override fun getValue() = value

    override fun increment(by: Int) {
        value = value.plus(by)
        onChange?.invoke(value)
    }

    override fun decrement(by: Int) {
        value = value.minus(by)
        onChange?.invoke(value)
    }

    override fun print(message: String) {
        println(message)
    }

    override fun goCrazy(times: Int) {
        for (i in 0..times) print("crazy $i")
    }
}