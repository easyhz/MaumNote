package com.maum.note.ui.screen.note.creation.generation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.AppError
import com.maum.note.core.common.error.ErrorHandler
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.url.urlDecode
import com.maum.note.core.model.error.ErrorMessage
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.note.generation.GenerationNote
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.usecase.GenerateNoteUseCase
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationSideEffect
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

@HiltViewModel
class NoteGenerationViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger,
    private val generateNoteUseCase: GenerateNoteUseCase,
    private val errorHandler: ErrorHandler,
    private val resourceHelper: ResourceHelper,
) : BaseViewModel<NoteGenerationState, NoteGenerationSideEffect>(
    initialState = NoteGenerationState.init()
) {
    init {
        init()
        changeTextIndex()
    }

    private fun init() {
        val ageType: String? = savedStateHandle["ageType"]
        val noteType: String? = savedStateHandle["noteType"]
        val sentenceCountType: String? = savedStateHandle["sentenceCountType"]
        val inputContent: String? = savedStateHandle["inputContent"]
        if (ageType.isNullOrBlank() || noteType.isNullOrBlank() || sentenceCountType.isNullOrBlank()) {
            navigateUp()
            return
        }
        val note = GenerationNote(
            ageType = ageType,
            noteType = noteType,
            sentenceCountType = sentenceCountType,
            inputContent = inputContent?.urlDecode() ?: "",
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

    private fun generateNote() = viewModelScope.launch(ioDispatcher) {
        val params = getNoteGenerationParams()
        if (params == null) {
            withContext(mainDispatcher) {
                navigateUp(AppError.UnexpectedError)
            }
            return@launch
        }

        generateNoteUseCase(params).onSuccess { response ->
            navigateToNoteDetail(note = response.toNote())
        }.onFailure { e ->
            logger.e("NoteGenerationViewModel", "generateNote", e)
            handleError(e)
            navigateUp(e)
        }
    }

    private fun getNoteGenerationParams(): NoteGenerationRequestParam? {
        val generationNote = currentState.generationNote ?: return null

        return NoteGenerationRequestParam(
            noteType = generationNote.noteType,
            ageType = generationNote.ageType,
            sentenceCount = generationNote.sentenceCountType,
            inputContent = generationNote.inputContent
        )
    }

    private fun navigateToNoteDetail(note: Note) {
        postSideEffect { NoteGenerationSideEffect.NavigateToNext(note = note) }
    }

    private fun handleError(e: Throwable) {
        when(e) {
            is AppError.NoUserDataError -> navigateToSplash()
            else -> navigateUp(e)
        }
    }


    private fun navigateUp(error: Throwable? = null) {
        viewModelScope.launch {
            val errorMessage = errorHandler.getErrorMessage(error)?.let {
                ErrorMessage(
                    title = resourceHelper.getString(R.string.error_title),
                    message = it
                )
            }
            delay(500L)
            postSideEffect { NoteGenerationSideEffect.NavigateUp(errorMessage = errorMessage) }
        }
    }

    private fun navigateToSplash() {
        postSideEffect { NoteGenerationSideEffect.NavigateToSplash }
    }
}