package com.maum.note.domain.user.useacse

import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.AppError
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.domain.note.model.request.LegacyNoteRequestParam
import com.maum.note.domain.note.repository.NoteRepository
import com.maum.note.domain.setting.repository.age.AgeRepository
import com.maum.note.domain.setting.repository.tone.ToneRepository
import com.maum.note.domain.user.model.request.UpdateUserStudentRequestParam
import com.maum.note.domain.setting.model.tone.request.InsertToneRequestParam
import com.maum.note.domain.user.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class SynchronizeUserUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val noteRepository: NoteRepository,
    private val toneRepository: ToneRepository,
    private val ageRepository: AgeRepository
) {
    operator fun invoke(
        concurrency: Int = 5
    ): Flow<Float> = channelFlow {
        send(0f)
        val userId = userRepository.getCurrentUser()?.id ?: throw AppError.NoUserDataError
        val notes = noteRepository.findAllNotesFlow().first()
        val totalCount = notes.size

        updateAge(userId)
        updateTone(userId)

        val semaphore = Semaphore(concurrency)
        val uploadedCounter = AtomicInteger(0)
        notes.forEach { note ->
            launch {
                semaphore.acquire()
                try {
                    val param = LegacyNoteRequestParam(
                        userId = userId,
                        noteType = note.noteType,
                        studentAge = note.ageType,
                        sentenceCount = note.sentenceCountType,
                        inputContent = note.inputContent,
                        result = note.result,
                        createdAt = note.createdAt,
                        updatedAt = note.createdAt
                    )
                    noteRepository.insertLegacyNote(param = param)
                } finally {
                    semaphore.release()
                }

                val currentCount = uploadedCounter.incrementAndGet()
                val percent = currentCount / totalCount.toFloat()
                send(percent)
            }
        }
    }.flowOn(dispatcher)

    private suspend fun updateAge(userId: String) {
        val age = ageRepository.getAgeSetting() ?: AgeType.MIXED.name
        val updateUserStudentRequestParam = UpdateUserStudentRequestParam(userId = userId, ageType = AgeType.getByValue(age)?.alias ?: AgeType.MIXED.alias)
        userRepository.updateUserStudentAge(updateUserStudentRequestParam)
    }

    private suspend fun updateTone(userId: String) {
        val tones = toneRepository.getAllSelectedTones()
        val toneMap = tones.associateBy { it.noteType }

        val param = InsertToneRequestParam(
            userId = userId,
            common = toneMap[NoteType.DEFAULT.name]?.content.orEmpty(),
            letterGreeting = toneMap[NoteType.LETTER_GREETING.name]?.content.orEmpty(),
            playContext = toneMap[NoteType.PLAY_CONTEXT.name]?.content.orEmpty(),
            announcementContent = toneMap[NoteType.ANNOUNCEMENT_CONTENT.name]?.content.orEmpty()
        )

        toneRepository.insertTone(param)
    }
}