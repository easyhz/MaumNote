package com.maum.note.data.configuration.repository

import com.maum.note.data.configuration.datasource.local.ConfigurationLocalDataSource
import com.maum.note.data.configuration.datasource.remote.ConfigurationRemoteDataSource
import com.maum.note.data.configuration.mapper.ConfigurationMapper
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val configurationMapper: ConfigurationMapper,
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource,
    private val configurationLocalDataSource: ConfigurationLocalDataSource
) : ConfigurationRepository {
    private val notificationDateStandard = 2
    override suspend fun fetchConfiguration(): Result<ConfigurationResponse> {
        return configurationRemoteDataSource.fetchConfiguration()
            .map { configurationMapper.mapToResponse(it) }
    }

    override suspend fun shouldNotificationPermission(): Result<Boolean> = runCatching {
        val isNotificationEnabled = configurationLocalDataSource.getNotificationStatus().first()
        val notificationDate = configurationLocalDataSource.getNotificationDate().first()

        if (isNotificationEnabled) return@runCatching false
        if (notificationDate == null) return@runCatching true

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val lastDate = dateFormat.parse(notificationDate) ?: return@runCatching true

        val daysSinceLastNotification = ChronoUnit.DAYS.between(
            lastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            LocalDate.now()
        )

        daysSinceLastNotification >= notificationDateStandard
    }

    override suspend fun updateNotificationPermission(isAllowed: Boolean): Result<Unit> = runCatching {
        configurationLocalDataSource.updateNotificationStatus(isAllowed)
        configurationLocalDataSource.updateNotificationDate(LocalDate.now().toString())
    }

    override fun getIsSynchronization(): Flow<Boolean> {
        return configurationLocalDataSource.getIsSynchronization()
    }

    override suspend fun updateIsSynchronization(newValue: Boolean) {
        configurationLocalDataSource.updateIsSynchronization(newValue)
    }
}