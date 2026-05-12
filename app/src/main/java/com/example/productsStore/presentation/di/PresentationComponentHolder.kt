package com.example.productsStore.presentation.di

import com.example.productsStore.domain.di.DomainComponentHolder
import com.example.productsStore.domain.usecase.GetProductsUseCase

object PresentationComponentHolder {
    lateinit var component: PresentationComponent
        private set

    fun init() {
        component = PresentationComponent(object : PresentationComponent.Dependencies {
            override fun getProductsUseCase(): GetProductsUseCase =
                DomainComponentHolder.component.getProductsUseCase()
        })
    }
}