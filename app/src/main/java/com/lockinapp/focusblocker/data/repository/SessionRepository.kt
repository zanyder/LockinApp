package com.lockinapp.focusblocker.data.repository

import com.lockinapp.focusblocker.data.local.FocusSessionDao
import com.lockinapp.focusblocker.data.local.FocusSessionEntity
import com.lockinapp.focusblocker.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface SessionRepository {
    fun observeActiveSession(): Flow<FocusSession?>
    fun observeAllSessions(): Flow<List<FocusSession>>
    suspend fun startSession(durationMillis: Long, isStrict: Boolean): FocusSession
    suspend fun endSession(successful: Boolean)
}

class SessionRepositoryImpl(
    private val dao: FocusSessionDao
) : SessionRepository {

    override fun observeActiveSession(): Flow<FocusSession?> {
        return dao.observeActiveSession().map { entity ->
            entity?.toDomain()
        }
    }

    override fun observeAllSessions(): Flow<List<FocusSession>> {
        return dao.observeAllSessions().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun startSession(durationMillis: Long, isStrict: Boolean): FocusSession {
        val now = System.currentTimeMillis()
        val entity = FocusSessionEntity(
            startTimeMillis = now,
            targetDurationMillis = durationMillis,
            isStrict = isStrict
        )
        val id = dao.insertSession(entity)
        return entity.copy(id = id).toDomain()
    }

    override suspend fun endSession(successful: Boolean) {
        val current = dao.observeActiveSession().firstOrNull() ?: return

        val updated = current.copy(
            endTimeMillis = System.currentTimeMillis(),
            completedSuccessfully = successful
        )
        dao.updateSession(updated)
    }

    private fun FocusSessionEntity.toDomain(): FocusSession =
        FocusSession(
            id = id,
            startTimeMillis = startTimeMillis,
            endTimeMillis = endTimeMillis,
            targetDurationMillis = targetDurationMillis,
            isStrict = isStrict,
            completedSuccessfully = completedSuccessfully
        )
}

