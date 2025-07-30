package com.maum.note.data.setting.datasource.age.local

import com.maum.note.core.datastore.age.AgeDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AgeLocalDataSourceImpl @Inject constructor(
    private val ageDataStore: AgeDataStore
): AgeLocalDataSource {
    override suspend fun getAgeSetting(): String? {
        return ageDataStore.getAgeSetting().first()
    }

    override suspend fun setAgeSetting(age: String) {
        ageDataStore.setAgeSetting(age = age)
    }
}