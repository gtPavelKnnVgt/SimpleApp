package dev.whysoezzy.domain.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}