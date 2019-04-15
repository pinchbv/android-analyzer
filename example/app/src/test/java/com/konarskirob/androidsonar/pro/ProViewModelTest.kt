package com.konarskirob.androidsonar.pro

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProViewModelTest {

    @Test
    fun testVm() {
        val vm = ProViewModel()

        Assert.assertFalse(vm.locked)
        Assert.assertEquals(0, vm.count.value)

        vm.lock()
        Assert.assertTrue(vm.locked)

        vm.increment()
        Assert.assertEquals(0, vm.count.value)

        vm.unlock()
        vm.increment()
        Assert.assertEquals(1, vm.count.value)
    }
}