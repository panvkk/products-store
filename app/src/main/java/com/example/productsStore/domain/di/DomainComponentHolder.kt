package com.example.productsStore.domain.di

import com.example.productsStore.data.di.DataComponentHolder
import com.example.productsStore.domain.repository.ProductsRepository

object DomainComponentHolder {
    lateinit var component: DomainComponent
        private set

    fun init() {
        component = DomainComponent(
            object : DomainComponent.Dependencies {
                override fun getRepository(): ProductsRepository =
                    DataComponentHolder.component.productsRepository
            }
        )
    }
}