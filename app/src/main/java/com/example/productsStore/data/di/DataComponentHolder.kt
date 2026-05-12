package com.example.productsStore.data.di

import com.example.productsStore.data.service.ProductsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object DataComponentHolder {
    lateinit var component: DataComponent
        private set

    fun init() {
        component = DataComponent(object : DataComponent.Dependencies {
            override fun getProductsService(): ProductsService {
                val mediaType = "application/json".toMediaType()
                val json = Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }

                return Retrofit.Builder()
                    .baseUrl(ProductsService.BASE_URL)
                    .addConverterFactory(json.asConverterFactory(mediaType))
                    .build()
                    .create()
            }
        })
    }
}