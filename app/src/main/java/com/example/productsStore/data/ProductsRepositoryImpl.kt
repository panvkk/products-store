package com.example.productsStore.data

import com.example.productsStore.core.CACHE_EXPIRATION_DATE_IN_SECONDS
import com.example.productsStore.core.Resource
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.data.local.dao.ProductDetailsCacheDao
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.data.mapper.toEntity
import com.example.productsStore.data.remote.service.ProductsService
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.model.ProductDetails
import com.example.productsStore.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Clock.System

class ProductsRepositoryImpl @Inject constructor(
    private val productsService: ProductsService,
    private val productDetailsCacheDao: ProductDetailsCacheDao
) : ProductsRepository {
    override suspend fun getProducts(
        skip: Int,
        limit: Int,
        fields: String
    ): Resource<List<Product>> {
        return try {
            val response = productsService.getProducts(skip, limit, fields).productsApi.map { it.toDomain() }
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

    override suspend fun getProductDetails(id: Int, fields: String): Resource<ProductDetails> {
        val cachedDetails = productDetailsCacheDao.getDetails(id)
        val currentTimestamp = System.now().epochSeconds
        val isCacheNotExpired = cachedDetails != null && cachedDetails.timestamp + CACHE_EXPIRATION_DATE_IN_SECONDS > currentTimestamp

        return try {
            if(isCacheNotExpired) return Resource.Success(cachedDetails.toDomain(false))

            val response = productsService.getProductDetails(id, fields)
            productDetailsCacheDao.putDetails(response.toEntity(id))
            Resource.Success(response.toDomain())
        } catch (e: HttpException) {
            if(cachedDetails != null) return Resource.Success(cachedDetails.toDomain(true))

            Resource.Error(DomainError.ServerIssue)
        } catch (e: IOException) {
            if(cachedDetails != null) return Resource.Success(cachedDetails.toDomain(true))

            Resource.Error(DomainError.NetworkIssue)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Resource.Error(DomainError.Other)
        }
    }
}