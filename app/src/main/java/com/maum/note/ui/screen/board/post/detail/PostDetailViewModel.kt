package com.maum.note.ui.screen.board.post.detail

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.designSystem.component.bottomSheet.BottomSheetType
import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.common.OwnerBottomSheet
import com.maum.note.core.model.common.ViewerBottomSheet
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import com.maum.note.domain.board.usecase.GetAnonymousSettingFlowUseCase
import com.maum.note.domain.board.usecase.SetAnonymousSettingUseCase
import com.maum.note.domain.board.usecase.comment.CreateCommentUseCase
import com.maum.note.domain.board.usecase.comment.DeleteCommentUseCase
import com.maum.note.domain.board.usecase.comment.FetchCommentsUseCase
import com.maum.note.domain.board.usecase.post.DeletePostUseCase
import com.maum.note.domain.board.usecase.post.FetchPostUseCase
import com.maum.note.domain.user.useacse.GetCurrentUserUseCase
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailSideEffect
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import com.maum.note.ui.screen.board.post.detail.model.MoreBottomSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 7. 26.
 * Time: 오후 5:38
 */

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val fetchPostUseCase: FetchPostUseCase,
    private val fetchCommentsUseCase: FetchCommentsUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val getAnonymousSettingFlowUseCase: GetAnonymousSettingFlowUseCase,
    private val setAnonymousSettingUseCase: SetAnonymousSettingUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
) : BaseViewModel<PostDetailState, PostDetailSideEffect>(
    initialState = PostDetailState.init()
) {

    init {
        init()
        getCurrentUser()
        getAnonymousSetting()
    }

    private fun init() {
        val id: String? = savedStateHandle["id"]
        val title: String = savedStateHandle["title"] ?: ""

        if (id == null) return navigateUp()

        setState { copy(post = post?.copy(id = id, title = title)) }
        fetchAll(id)
    }
    
    private fun fetchAll(id: String = currentState.post?.id ?: "") {
        fetchPost(id = id)
        fetchComments(id = id)
    }

    private fun getCurrentUser() {
        val user = getCurrentUserUseCase.invoke()
        setState { copy(userId = user?.id) }
    }

    private fun getAnonymousSetting() {
        viewModelScope.launch {
            getAnonymousSettingFlowUseCase.invoke().flowOn(ioDispatcher).catch { e ->
                e.printStackTrace()
            }.collect {
                setState { copy(isAnonymous = it) }
            }
        }
    }

    fun onCommentTextChanged(value: TextFieldValue) {
        setState { copy(commentText = value) }
    }

    fun onClickPostMore() {
        if (currentState.post == null) return
        val list =
            if (currentState.userId == currentState.post?.userId) enumValues<OwnerBottomSheet>()
            else enumValues<ViewerBottomSheet>()
        setState {
            copy(
                moreBottomSheet = MoreBottomSheet.Post(
                    targetId = post?.id!!,
                    list = list.toList()
                )
            )
        }
    }

    fun onClickCommentMore(item: Comment) {
        val list =
            if (currentState.userId == item.userId) enumValues<OwnerBottomSheet>()
            else enumValues<ViewerBottomSheet>()
        setState {
            copy(
                moreBottomSheet = MoreBottomSheet.Comment(
                    targetId = item.id,
                    list = list.toList()
                )
            )
        }
    }

    fun onClickAnonymous() {
        setState { copy(isAnonymous = !isAnonymous) }
        setAnonymousSetting()
    }

    fun onClickSend() {
        if (currentState.commentText.text.isBlank()) return
        val postId = currentState.post?.id
        if (postId.isNullOrBlank()) return
        val param = CreateCommentRequest(
            postId = postId,
            content = currentState.commentText.text,
            isAnonymous = currentState.isAnonymous
        )
        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            createCommentUseCase.invoke(param).onSuccess {
                setState { copy(commentText = TextFieldValue("")) }
            }.onFailure {
                // ERROR 처리
            }.also {
                setLoading(false)
            }
            fetchAll()
        }

    }

    fun hideMoreBottomSheet() {
        setState { copy(moreBottomSheet = null) }
    }

    fun onClickBottomSheetItem(moreBottomSheet: MoreBottomSheet, item: BottomSheetType) {
        val targetId = moreBottomSheet.targetId

        when(item) {
            OwnerBottomSheet.DELETE -> { delete(targetId = targetId, moreBottomSheet = moreBottomSheet) }
            ViewerBottomSheet.REPORT -> { }
        }
    }

    private fun fetchPost(id: String = currentState.post?.id ?: "") {
        if (id.isBlank()) return

        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchPostUseCase.invoke(param = id).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(post = it) }
                }
            }.onFailure {
                // ERROR 처리
            }.also {
                setLoading(false)
            }
        }
    }

    private fun fetchComments(id: String = currentState.post?.id ?: "") {
        if (id.isBlank()) return

        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchCommentsUseCase.invoke(param = id).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(comments = it) }
                }
            }.onFailure {
                // ERROR 처리
                it.printStackTrace()
            }.also {
                setLoading(false)
            }
        }
    }

    private fun navigateUp() {
        postSideEffect { PostDetailSideEffect.NavigateUp }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

    private fun setAnonymousSetting() {
        viewModelScope.launch(ioDispatcher) {
            val isAnonymous = currentState.isAnonymous
            setAnonymousSettingUseCase.invoke(isAnonymous)
        }
    }

    private fun delete(targetId: String, moreBottomSheet: MoreBottomSheet) {
        when(moreBottomSheet) {
            is MoreBottomSheet.Post -> deletePost(targetId = targetId)
            is MoreBottomSheet.Comment -> deleteComment(targetId = targetId)
        }

    }

    private fun deletePost(
        targetId: String
    ) {
        viewModelScope.launch {
            deletePostUseCase.invoke(param = targetId).onSuccess {
                // TODO 어떻게 처리 할지 고민
                navigateUp()
            }.onFailure {
                // TODO ERROR 처리
                it.printStackTrace()
            }
        }
    }

    private fun deleteComment(
        targetId: String
    ) {
        println("deleteComment $targetId")
        viewModelScope.launch {
            deleteCommentUseCase.invoke(param = targetId).onSuccess {
                fetchAll()
            }.onFailure {
                // TODO ERROR 처리
                it.printStackTrace()
            }
        }
    }

    private fun report() {

    }

}