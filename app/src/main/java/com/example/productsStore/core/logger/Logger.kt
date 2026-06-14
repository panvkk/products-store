package com.example.productsStore.core.logger

interface Logger {
    fun e(tag: String?, msg: String)
    fun d(tag: String?, msg: String)
    fun i(tag: String?, msg: String)
    fun w(tag: String?, msg: String)
    fun v(tag: String?, msg: String)
}