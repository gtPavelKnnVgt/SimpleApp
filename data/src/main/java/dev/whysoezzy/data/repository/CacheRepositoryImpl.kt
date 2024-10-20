package dev.whysoezzy.data.repository

import dev.whysoezzy.domain.data.repository.CacheRepository

class CacheRepositoryImpl : CacheRepository {

    private val cache = mutableMapOf<String, Any>()
    override suspend fun <T> getAndSave(force: Boolean, key: String, remote: suspend () -> T): T {
        if (force) {
            val data = remote.invoke()
            cache[key] = data!!
            return data
        } else {
            val data = cache[key] ?: let {
                val data = remote.invoke()
                cache[key] = data!!
                data
            }
            return data as T
        }
    }

    override suspend fun <T> getCache(key: String): T? {
        return cache[key] as T
    }
}