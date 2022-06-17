package com.karakasli.kmm_todo_app.android.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakasli.kmm_todo_app.core.UseCaseResult
import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.data.model.event.LoadingEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by salihkarakasli on 30.03.2022
 */

open class BaseViewModel : ViewModel() {
    private val _pageLoadingEvent =
        MutableStateFlow<LoadingEvent>(LoadingEvent.LoadingHidden)
    val pageLoadingEvent: StateFlow<LoadingEvent> = _pageLoadingEvent

    protected fun <T> callUseCase(
        callApi: suspend () -> UseCaseResult<T>,
        handleError: (suspend (error: AppError) -> Unit)? = null,
        handleSuccess: (suspend (T?) -> Unit)? = null,
        showLoading: Boolean = false,
        collectDispatcher: CoroutineDispatcher = Dispatchers.Main,
    ) {
        viewModelScope.launch(collectDispatcher) {
            if (showLoading)
                _pageLoadingEvent.value = LoadingEvent.LoadingShown

            when (val result = callApi()) {
                is UseCaseResult.Success -> {
                    handleSuccess?.invoke(result.data)
                }
                is UseCaseResult.ValidationFailed -> {
                    //TODO: Check This part to handle
                    //handleError?.invoke(result.validationError)
                }
                is UseCaseResult.Error -> {
                    handleError?.invoke(result.appError)
                }
            }
            if (showLoading)
                _pageLoadingEvent.value = LoadingEvent.LoadingHidden
        }
    }
}