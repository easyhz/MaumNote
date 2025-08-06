package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.error.AppError
import com.maum.note.core.common.util.version.Version
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.core.model.user.UserStep
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import com.maum.note.domain.note.model.request.LegacyNoteRequestParam
import com.maum.note.domain.note.repository.NoteRepository
import com.maum.note.domain.setting.repository.age.AgeRepository
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import javax.inject.Inject

class CheckUserStepUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val userRepository: UserRepository,
    private val ageRepository: AgeRepository,
    private val version: Version = Version(),
    private val noteRepository: NoteRepository
): BaseUseCase<Unit, UserStep>() {
    override suspend fun invoke(param: Unit): Result<UserStep> {
        return runCatching {
            val config = configurationRepository.fetchConfiguration().getOrThrow()
            checkConfiguration(config) ?: checkLoginFlow()
        }
    }

    private fun checkConfiguration(configuration: ConfigurationResponse): UserStep? {
        return when {
            configuration.maintenanceNotice.isNotBlank() ->
                UserStep.Maintenance(configuration.maintenanceNotice)

            version.needsUpdate(configuration.androidVersion) ->
                UserStep.Update(configuration.androidVersion)

            else -> null
        }
    }

    private suspend fun checkLoginFlow(): UserStep {
        if (userRepository.isLogin().getOrThrow()) return UserStep.AlreadyLoginToMain

        userRepository.signInAnonymously()
        val currentUser = userRepository.getCurrentUser() ?: throw AppError.NoUserDataError

        val existingUser = userRepository.fetchUser(currentUser.id)
        if (existingUser != null) {
            return UserStep.ExistingUserToOnboarding
        }

        return handleNewUser(currentUser.id)
    }

    private suspend fun handleNewUser(userId: String): UserStep {
        userRepository.saveUser(SaveUserRequestParam(userId)).getOrThrow()
        val isSynchronization = configurationRepository.getIsSynchronization().first()
        val hasAge = ageRepository.getAgeSetting() != null
        return if (!isSynchronization && hasAge) {
            UserStep.NeedSynchronize
        } else {
            configurationRepository.updateIsSynchronization(true)
            insertExampleNote(userId)
            UserStep.NewUserToOnboarding
        }
    }

    private suspend fun insertExampleNote(userId: String) {
        val noteRequestParam = getExampleNoteParam(userId)
        noteRepository.insertLegacyNote(noteRequestParam)
    }


    private fun getExampleNoteParam(userId: String): LegacyNoteRequestParam {
        return LegacyNoteRequestParam(
            userId = userId,
            noteType = NoteType.PLAY_CONTEXT.name,
            studentAge = AgeType.THREE.name,
            sentenceCount = SentenceType.FOUR_TO_FIVE.name,
            inputContent = exampleInputContent,
            result = exampleResult,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
    }

    private val exampleInputContent = "동물원으로 봄소풍"
    private val exampleResult = """
      [예시] 따뜻한 봄바람을 따라 우리 반 친구들이 동물원으로 봄소풍을 다녀왔어요. 활짝 핀 꽃길을 걸으며 손을 꼭 잡고 걸어가는 모습이 참 사랑스러웠답니다.
      동물 친구들을 만난 아이들은 “우와~ 진짜 기린이다!”, “사자 목소리 무서워요!” 하며 신기하고 즐거운 마음을 가득 담아 이야기했어요.
      서로가 보고 싶은 동물을 알려주며 지도를 함께 보기도 하고, 친구와 나란히 앉아 도시락을 먹는 순간순간에도 웃음꽃이 피었답니다.
      자연과 동물에 대한 따뜻한 마음을 키우며, 하루 종일 웃음이 가득했던 소중한 봄날이었어요.
      
      *이 글은 기능 안내를 위한 샘플 노트입니다.*
    """.trimIndent()

}