package com.karakasli.kmm_todo_app.domain.usecase

import com.karakasli.kmm_todo_app.core.BaseUseCase
import com.karakasli.kmm_todo_app.core.UseCaseResult
import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.domain.repository.TodoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
class RemoveTodoUseCase : BaseUseCase<String, Unit>(), KoinComponent {

    private val todoRepository: TodoRepository by inject()

    override fun execute(param: String): UseCaseResult<Unit> {
        val result = todoRepository.removeTodo(param)

        return if (result) {
            UseCaseResult.Success(Unit)
        } else {
            UseCaseResult.Error(AppError("remove todo from db is failed!"))
        }
    }
}