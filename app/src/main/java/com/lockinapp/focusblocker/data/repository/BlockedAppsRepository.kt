package com.lockinapp.focusblocker.data.repository

import com.lockinapp.focusblocker.data.local.BlockedAppDao
import com.lockinapp.focusblocker.data.local.BlockedAppEntity
import com.lockinapp.focusblocker.domain.model.BlockedApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface BlockedAppsRepository {
    fun observeAllApps(): Flow<List<BlockedApp>>
    fun observeBlockedApps(): Flow<List<BlockedApp>>
    suspend fun setAppBlocked(packageName: String, label: String, isBlocked: Boolean)
}

class BlockedAppsRepositoryImpl(
    private val dao: BlockedAppDao
) : BlockedAppsRepository {

    override fun observeAllApps(): Flow<List<BlockedApp>> {
        return dao.observeBlockedApps().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeBlockedApps(): Flow<List<BlockedApp>> {
        return dao.observeCurrentlyBlockedApps().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun setAppBlocked(packageName: String, label: String, isBlocked: Boolean) {
        val entity = BlockedAppEntity(
            packageName = packageName,
            label = label,
            isBlocked = isBlocked
        )
        dao.upsertBlockedApp(entity)
    }

    private fun BlockedAppEntity.toDomain(): BlockedApp =
        BlockedApp(
            packageName = packageName,
            label = label,
            isBlocked = isBlocked
        )
}

