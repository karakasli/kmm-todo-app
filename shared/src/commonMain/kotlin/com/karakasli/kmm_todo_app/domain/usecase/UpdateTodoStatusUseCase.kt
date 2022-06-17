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
class UpdateTodoStatusUseCase : BaseUseCase<UpdateTodoStatusUseCase.Params, Unit>(), KoinComponent {

    private val todoRepository: TodoRepository by inject()

    override fun execute(param: Params): UseCaseResult<Unit> {

        val result = todoRepository.updateTodoStatus(param.uuid, param.isCompleted)

        return if (result) {
            UseCaseResult.Success(Unit)
        } else {
            UseCaseResult.Error(AppError("update todo status in db failed"))
        }
    }

    data class Params(
        val uuid: String,
        val isCompleted: Boolean,
    )
}