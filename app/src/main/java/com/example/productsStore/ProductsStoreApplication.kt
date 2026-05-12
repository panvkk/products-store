package com.example.productsStore

import android.app.Application
import com.example.productsStore.data.di.DataComponentHolder
import com.example.productsStore.domain.di.DomainComponentHolder
import com.example.productsStore.presentation.di.PresentationComponentHolder

class ProductsStoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataComponentHolder.init()
        DomainComponentHolder.init()
        PresentationComponentHolder.init()
    }
}