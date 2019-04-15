package com.konarskirob.androidsonar.pro

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.konarskirob.androidsonar.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProActivityTest {

    @Test
    fun testIncrement() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.up)).perform(click())

        onView(withId(R.id.counter)).check(matches(withText("1")))
    }

    @Test
    fun testIncrementLocked() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.lock)).perform(click())
        onView(withId(R.id.up)).perform(click())

        onView(withId(R.id.counter)).check(matches(withText("0")))
    }

    @Test
    fun testIncrementMax() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        for (i in 0..20) {
            onView(withId(R.id.up)).perform(click())
        }
        onView(withId(R.id.counter)).check(matches(withText("10")))
    }

    @Test
    fun testDecrement() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.up)).perform(click())
        onView(withId(R.id.down)).perform(click())

        onView(withId(R.id.counter)).check(matches(withText("0")))
    }

    @Test
    fun testDecrementLocked() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.up)).perform(click())
        onView(withId(R.id.up)).perform(click())

        onView(withId(R.id.counter)).check(matches(withText("2")))

        onView(withId(R.id.lock)).perform(click())
        onView(withId(R.id.down)).perform(click())

        onView(withId(R.id.counter)).check(matches(withText("2")))
    }

    @Test
    fun testDecrementMin() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        for (i in 0..20) {
            onView(withId(R.id.down)).perform(click())
        }
        onView(withId(R.id.counter)).check(matches(withText("0")))
    }

    @Test
    fun testLockUnlock() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.lock)).perform(click())

        onView(withId(R.id.up)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.up)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.unlock)).perform(click())

        onView(withId(R.id.up)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("1")))

        onView(withId(R.id.down)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))
    }

    @Test
    fun testRecreate() {
        val scenario = ActivityScenario.launch(ProActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.counter)).check(matches(withText("0")))

        onView(withId(R.id.up)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("1")))

        scenario.recreate()

        onView(withId(R.id.counter)).check(matches(withText("1")))
    }
}