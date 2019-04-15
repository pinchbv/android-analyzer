package com.konarskirob.androidsonar.extensions

inline fun Int.incrementIf(predicate: (Int) -> Boolean): Int {
    return if (predicate(this)) this.plus(1) else this
}

inline fun Int.decrementIf(predicate: (Int) -> Boolean): Int {
    return if (predicate(this)) this.minus(1) else this
}