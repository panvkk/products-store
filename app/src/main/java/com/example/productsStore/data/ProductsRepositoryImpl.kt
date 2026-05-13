package com.example.productsStore.data

import com.example.productsStore.core.Resource
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.data.service.ProductsService
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProductsRepositoryImpl @Inject constructor(
    private val productsService: ProductsService
) : ProductsRepository {
    override suspend fun getProducts(fields: String): Resource<List<Product>> {
        return try {
            val response = productsService.getProducts(fields).productsApi.map { it.toDomain() }
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(DomainError.ServerIssue)
        } catch (e: IOException) {
            Resource.Error(DomainError.NetworkIssue)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Resource.Error(DomainError.Other)
        }

    }
}