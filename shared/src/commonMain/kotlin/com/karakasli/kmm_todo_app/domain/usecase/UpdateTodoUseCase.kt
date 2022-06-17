package com.karakasli.kmm_todo_app.domain.usecase

import com.karakasli.kmm_todo_app.core.BaseUseCase
import com.karakasli.kmm_todo_app.core.UseCaseResult
import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.data.model.error.ValidationError
import com.karakasli.kmm_todo_app.ui.model.TodoUIModel
import com.karakasli.kmm_todo_app.domain.repository.TodoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
class UpdateTodoUseCase : BaseUseCase<TodoUIModel, Unit>(), KoinComponent {

    private val todoRepository: TodoRepository by inject()

    override fun execute(param: TodoUIModel): UseCaseResult<Unit> {

        val result = todoRepository.updateTodo(
            uuid = param.uuid,
            title = param.title,
            body = param.body,
            isCompleted = param.isCompleted
        )

        return if (result) {
            UseCaseResult.Success(Unit)
        } else {
            UseCaseResult.Error(AppError("update todo in db is failed!"))
        }
    }

    override fun checkParamsForValidation(param: TodoUIModel): List<ValidationError> {
        if (param.title.isEmpty()) {
            listOf(
                ValidationError(
                    title = "Validation Error",
                    message = "Please type something for title!"
                )
            )
        }
        return emptyList()
    }
}