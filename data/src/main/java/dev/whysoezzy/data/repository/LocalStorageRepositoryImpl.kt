package dev.whysoezzy.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import dev.whysoezzy.domain.repository.LocalStorageRepository

class LocalStorageRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : LocalStorageRepository {
    override fun markAsRead(id: Long) {
        sharedPreferences.edit {
            putBoolean("readed_$id", true)
        }
    }

    override fun isMarkAsRead(id: Long): Boolean {
        return sharedPreferences.getBoolean("readed_$id", false)
    }
}