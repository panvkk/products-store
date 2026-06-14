package com.example.productsStore.core.logger

import android.util.Log

class AppLogger : Logger {
    override fun e(tag: String?, msg: String) { Log.e(tag, msg) }
    override fun d(tag: String?, msg: String) { Log.d(tag, msg) }
    override fun i(tag: String?, msg: String) { Log.i(tag, msg) }
    override fun v(tag: String?, msg: String) { Log.v(tag, msg) }
    override fun w(tag: String?, msg: String) { Log.w(tag, msg) }
}