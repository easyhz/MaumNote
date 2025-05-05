package com.maum.note.ui.screen.note.creation.generation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.serializable.SerializableHelper
import com.maum.note.core.common.util.url.urlDecode
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.generation.GenerationNote
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.usecase.GenerateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationSideEffect
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

@HiltViewModel
class NoteGenerationViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher, private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
    private val generateNoteUseCase: GenerateNoteUseCase,
) : BaseViewModel<NoteGenerationState, NoteGenerationSideEffect>(
    initialState = NoteGenerationState.init()
) {
    init {
        init()
        changeTextIndex()
    }

    private fun init() {
        val paramArgs: String? = savedStateHandle["generationNoteArgs"]
        val generationNote = serializableHelper.deserialize(paramArgs, GenerationNote::class.java) ?: return navigateUp()
        val note = generationNote.copy(
            inputContent = generationNote.inputContent.urlDecode(),
        )
        setState { copy(generationNote = note) }
        generateNote()
    }

    private fun changeTextIndex() = viewModelScope.launch {
        for (index in 1 until currentState.captionTexts.size) {
            delay(3000L)
            setState { copy(currentTextIndex = index) }
        }
    }

    private fun generateNote() = viewModelScope.launch {
        val params = getNoteGenerationParams() ?: return@launch navigateUp()
        withContext(ioDispatcher) {
            generateNoteUseCase.invoke(params).onSuccess { note ->
                // TODO note 결과 화면으로 이동
            }.onFailure {
                // TODO 뒤로 가서 에러 처리

            }
        }
    }

    private fun getNoteGenerationParams(): NoteGenerationRequestParam? {
        val generationNote = currentState.generationNote ?: return null

        return NoteGenerationRequestParam(
            noteType = generationNote.noteType,
            ageType = AgeType.MIXED.name,
            sentenceCount = generationNote.sentenceCountType,
            inputContent = generationNote.inputContent
        )

    }



    private fun navigateUp() {
        // TODO("Navigate up logic")
    }

}