package com.example.productsStore.testing.stub

import com.example.productsStore.core.logger.LoggingProvider

internal class LoggingProviderStub : LoggingProvider {
    override fun e(tag: String?, msg: String) = Unit

    override fun d(tag: String?, msg: String) = Unit

    override fun i(tag: String?, msg: String) = Unit

    override fun w(tag: String?, msg: String) = Unit

    override fun v(tag: String?, msg: String) = Unit
}