package com.karakasli.kmm_todo_app.domain.usecase

import com.karakasli.kmm_todo_app.core.BaseUseCase
import com.karakasli.kmm_todo_app.core.UseCaseResult
import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.data.model.error.ValidationError
import com.karakasli.kmm_todo_app.domain.mapper.TodoUIModelMapper
import com.karakasli.kmm_todo_app.ui.model.TodoUIModel
import com.karakasli.kmm_todo_app.domain.repository.TodoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
class GetTodoUseCase : BaseUseCase<String, TodoUIModel>(), KoinComponent {

    private val todoRepository: TodoRepository by inject()
    private val todoUIModelMapper: TodoUIModelMapper by inject()

    override fun execute(param: String): UseCaseResult<TodoUIModel> {
        val todo = todoRepository.getTodo(param)
        todo?.let {
            return UseCaseResult.Success(todoUIModelMapper(todo))
        }
        return UseCaseResult.Error(AppError(message = "query failed for get todo!"))
    }

    override fun checkParamsForValidation(param: String): List<ValidationError> {
        if (param.isBlank()) {
            return listOf(
                ValidationError(title = "Validation Error", message = "uuid could not be empty!")
            )
        }
        return emptyList()
    }
}