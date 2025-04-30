package com.maum.note.data.setting.repository.age

import com.maum.note.data.setting.datasource.age.local.AgeLocalDataSource
import com.maum.note.domain.setting.repository.age.AgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AgeRepositoryImpl @Inject constructor(
    private val ageLocalDataSource: AgeLocalDataSource
): AgeRepository {
    override fun getAgeSetting(): Flow<String> {
        return ageLocalDataSource.getAgeSetting()
    }

    override suspend fun setAgeSetting(age: String) {
        ageLocalDataSource.setAgeSetting(age = age)
    }
}