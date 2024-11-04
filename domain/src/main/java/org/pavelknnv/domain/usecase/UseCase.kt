package org.pavelknnv.domain.usecase

interface UseCase<D,R>{
    suspend fun execute(data:D): R
}