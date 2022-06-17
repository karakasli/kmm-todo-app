package com.karakasli.kmm_todo_app.android.features.todolist

import com.karakasli.kmm_todo_app.android.core.base.BaseViewModel
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.TodoAdapterData
import com.karakasli.kmm_todo_app.android.ui.event.UIEvent
import com.karakasli.kmm_todo_app.domain.usecase.GetTodosUseCase
import com.karakasli.kmm_todo_app.domain.usecase.RemoveTodoUseCase
import com.karakasli.kmm_todo_app.domain.usecase.UpdateTodoStatusUseCase
import com.karakasli.kmm_todo_app.ui.model.HomeScreenUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoListViewModel : BaseViewModel(), KoinComponent {

    private val getTodosUseCase: GetTodosUseCase by inject()
    private val updateTodoStatusUseCase: UpdateTodoStatusUseCase by inject()
    private val removeTodoUseCase: RemoveTodoUseCase by inject()

    private var homeScreenUIModel: HomeScreenUIModel? = null
    private val _loadTodosEvent = MutableStateFlow<UIEvent<HomeScreenUIModel>>(UIEvent.Idle)
    val loadTodosEvent = _loadTodosEvent.asStateFlow()

    var selectedTodoItem: TodoAdapterData? = null

    fun getAllTodos() {
        callUseCase(
            callApi = {
                getTodosUseCase(Unit)
            },
            handleSuccess = { data ->
                data?.let {
                    homeScreenUIModel = HomeScreenUIModel(
                        todos = data
                    )
                    if (it.isNullOrEmpty()) {
                        _loadTodosEvent.value = UIEvent.Empty
                    } else {
                        _loadTodosEvent.value = UIEvent.Success(homeScreenUIModel)
                    }
                }
            },
            showLoading = true
        )

    }

    fun updateTodoStatus(todoUid: String, isCompleted: Boolean) {

        callUseCase(
            callApi = {
                updateTodoStatusUseCase(
                    UpdateTodoStatusUseCase.Params(
                        uuid = todoUid,
                        isCompleted = isCompleted
                    )
                )
            },
            handleSuccess = {
                getAllTodos()
            },
            handleError = {
                getAllTodos()
            }
        )
    }

    fun removeTodo(todoUid: String) {

        callUseCase(
            callApi = {
                removeTodoUseCase(todoUid)
            },
            handleSuccess = {
                getAllTodos()
            }
        )

    }
}