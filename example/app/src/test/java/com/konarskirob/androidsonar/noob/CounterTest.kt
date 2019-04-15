package com.konarskirob.androidsonar.noob.counter

import org.junit.Assert.assertEquals
import org.junit.Test

class CounterTest {

    @Test
    fun testIncrementByOne() {
        val counter: Counter = CounterImpl(0)
        counter.increment(1)

        assertEquals(1, counter.getValue())
    }

    @Test
    fun testIncrementByTwo() {
        val counter: Counter = CounterImpl(2)
        counter.increment(2)

        assertEquals(4, counter.getValue())
    }

    @Test
    fun testIncrementDefault() {
        val counter: Counter = CounterImpl(0)
        counter.increment()

        assertEquals(1, counter.getValue())
    }

    @Test
    fun testDecrementByOne() {
        val counter: Counter = CounterImpl(0)
        counter.decrement(1)

        assertEquals(-1, counter.getValue())
    }

    @Test
    fun testDecrementByTwo() {
        val counter: Counter = CounterImpl(2)
        counter.decrement(2)

        assertEquals(0, counter.getValue())
    }

    @Test
    fun testDecrementDefault() {
        val counter: Counter = CounterImpl(10)
        counter.decrement()

        assertEquals(9, counter.getValue())
    }

    @Test
    fun testPrint() {
        val counter: Counter = DuplicateCounterImpl(10)
        counter.print("lol")
    }

    @Test
    fun testCallback() {
        val counter: Counter = CounterImpl(10) {
            assertEquals(9, it)
        }

        counter.decrement()
    }
}
