package com.maum.note.ui.screen.board.post.detail

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.designSystem.component.bottomSheet.BottomSheetType
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.board.BoardReport
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
import kotlinx.coroutines.delay
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
    private val resourceHelper: ResourceHelper,
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
                setErrorDialog(it)
            }.also {
                setLoading(false)
            }
            fetchAll()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            setState { copy(isRefreshing = true) }
            fetchAll()
            delay(100)
            setState { copy(isRefreshing = false) }
        }
    }

    fun hideMoreBottomSheet() {
        setState { copy(moreBottomSheet = null) }
    }

    fun onClickBottomSheetItem(moreBottomSheet: MoreBottomSheet, item: BottomSheetType) {
        val targetId = moreBottomSheet.targetId

        when(item) {
            OwnerBottomSheet.DELETE -> { delete(targetId = targetId, moreBottomSheet = moreBottomSheet) }
            ViewerBottomSheet.REPORT -> { report(targetId = targetId, moreBottomSheet = moreBottomSheet) }
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
                setErrorDialog(it)
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
                it.printStackTrace()
                setErrorDialog(it)
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
            is MoreBottomSheet.Post -> setPostDeleteDialog(targetId = targetId)
            is MoreBottomSheet.Comment -> setCommentDeleteDialog(targetId = targetId)
        }
    }

    private fun report(targetId: String, moreBottomSheet: MoreBottomSheet) {
        val boardReport = when(moreBottomSheet) {
            is MoreBottomSheet.Post -> BoardReport(postId = targetId, commentId = null)
            is MoreBottomSheet.Comment -> BoardReport(postId = null, commentId = targetId)
        }

        navigateToReport(boardReport)
    }

    private fun setPostDeleteDialog(targetId: String) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.board_detail_post_delete_title),
                message = resourceHelper.getString(R.string.board_detail_post_delete_content),
                positiveButton = BasicDialogButton.delete(
                    text = resourceHelper.getString(R.string.dialog_delete_positive),
                    onClick = {
                        hideDialog()
                        deletePost(targetId)
                    }
                ),
                negativeButton = BasicDialogButton.cancel(
                    text = resourceHelper.getString(R.string.dialog_delete_negative),
                    onClick = ::hideDialog
                )
            )
        )
    }

    private fun setCommentDeleteDialog(targetId: String) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.board_detail_comment_delete_title),
                message = resourceHelper.getString(R.string.board_detail_comment_delete_content),
                positiveButton = BasicDialogButton.delete(
                    text = resourceHelper.getString(R.string.dialog_delete_positive),
                    onClick = {
                        hideDialog()
                        deleteComment(targetId)
                    }
                ),
                negativeButton = BasicDialogButton.cancel(
                    text = resourceHelper.getString(R.string.dialog_delete_negative),
                    onClick = ::hideDialog
                )
            )
        )
    }

    private fun deletePost(
        targetId: String
    ) {
        viewModelScope.launch {
            setLoading(true)
            deletePostUseCase.invoke(param = targetId).onSuccess {
                navigateToBoard()
            }.onFailure {
                it.printStackTrace()
                setErrorDialog(it)
            }
            setLoading(false)
        }
    }

    private fun deleteComment(
        targetId: String
    ) {
        viewModelScope.launch {
            setLoading(true)
            deleteCommentUseCase.invoke(param = targetId).onSuccess {
                fetchAll()
            }.onFailure {
                it.printStackTrace()
                setErrorDialog(it)
            }
            setLoading(false)
        }
    }

    private fun setErrorDialog(error: Throwable) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.error_dialog_title),
                message = resourceHelper.getString(error.handleError()),
                positiveButton = BasicDialogButton.default(
                    text = resourceHelper.getString(R.string.dialog_positive_button),
                    onClick = ::hideDialog
                )
            )
        )
    }

    private fun setDialog(message: DialogMessage?) {
        setState { copy(dialogMessage = message) }
    }

    private fun hideDialog() {
        setDialog(null)
    }

    private fun navigateToBoard() {
        postSideEffect { PostDetailSideEffect.NavigateToBoard }
    }

    private fun navigateToReport(boardReport: BoardReport) {
        postSideEffect { PostDetailSideEffect.NavigateToReport(postId = boardReport.postId, commentId = boardReport.commentId) }
    }
}