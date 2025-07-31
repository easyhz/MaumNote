package com.maum.note.core.common.helper.page

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems


val LazyPagingItems<*>.isInitialLoading: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount == 0

val LazyPagingItems<*>.isRefreshing: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount > 0

val LazyPagingItems<*>.isAppending: Boolean
    get() = loadState.append is LoadState.Loading

val LazyPagingItems<*>.isError: Boolean
    get() = loadState.refresh is LoadState.Error || loadState.append is LoadState.Error

val LazyPagingItems<*>.isInitialError: Boolean
    get() = loadState.refresh is LoadState.Error

val LazyPagingItems<*>.isAppendError: Boolean
    get() = loadState.append is LoadState.Error

val LazyPagingItems<*>.isEmpty: Boolean
    get() = itemCount == 0 &&
            loadState.refresh is LoadState.NotLoading &&
            loadState.append is LoadState.NotLoading

val LazyPagingItems<*>.hasMoreToLoad: Boolean
    get() = loadState.append.endOfPaginationReached.not()