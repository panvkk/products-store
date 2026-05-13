package com.example.productsStore.core.domain

sealed interface DomainError {
    data object NetworkIssue : DomainError
    data object ServerIssue: DomainError
    data object Other: DomainError
}