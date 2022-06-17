package com.karakasli.kmm_todo_app.android.features.tododetail

import com.karakasli.kmm_todo_app.android.ui.event.UIEvent
import com.karakasli.kmm_todo_app.android.core.base.BaseViewModel
import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.domain.usecase.CreateTodoUseCase
import com.karakasli.kmm_todo_app.domain.usecase.GetTodoUseCase
import com.karakasli.kmm_todo_app.domain.usecase.UpdateTodoUseCase
import com.karakasli.kmm_todo_app.ui.model.TodoUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoDetailViewModel: BaseViewModel(), KoinComponent {

    var mTodoId: String? = null

    private val getTodoUseCase: GetTodoUseCase by inject()
    private val createTodoUseCase: CreateTodoUseCase by inject()
    private val updateTodoUseCase: UpdateTodoUseCase by inject()

    private val _saveTodoEvent = MutableStateFlow<UIEvent<Unit>>(UIEvent.Idle)
    val saveTodoEvent = _saveTodoEvent.asStateFlow()
    fun clearSaveTodoEvent() {_saveTodoEvent.value = UIEvent.Idle }

    private val _loadTodoEvent = MutableStateFlow<UIEvent<TodoUIModel>>(UIEvent.Idle)
    val loadTodoEvent = _loadTodoEvent.asStateFlow()

    fun getTodo() {
        mTodoId?.let {
            callUseCase(
                callApi = {
                    getTodoUseCase(it)
                },
                handleSuccess = {
                    _loadTodoEvent.value = UIEvent.Success(it)
                },
                handleError = {
                    _loadTodoEvent.value = UIEvent.Failed(AppError(it.message))
                }
            )
        }
    }

    fun saveTodo(title: String, body: String, isCompleted: Boolean) {
        mTodoId?.let {
            updateTodo(
                TodoUIModel(uuid = it, title = title, body = body, isCompleted = isCompleted)
            )

        } ?: run {
            createTodo(
                TodoUIModel(uuid = UUID.randomUUID().toString(), title = title, body = body, isCompleted = isCompleted)
            )
        }
    }

    private fun createTodo(todoUIModel: TodoUIModel) {

        callUseCase(
            callApi = {
                createTodoUseCase(todoUIModel)
            },
            handleSuccess = {
                _saveTodoEvent.value = UIEvent.Success(Unit)
            },
            handleError = {
                _saveTodoEvent.value = UIEvent.Failed(AppError(it.message))
            }
        )
    }

    private fun updateTodo(todoUIModel: TodoUIModel) {
        callUseCase(
            callApi = {
                updateTodoUseCase(todoUIModel)
            },
            handleSuccess = {
                _saveTodoEvent.value = UIEvent.Success(Unit)
            },
            handleError = {
                _saveTodoEvent.value = UIEvent.Failed(AppError(it.message))
            }
        )
    }

}