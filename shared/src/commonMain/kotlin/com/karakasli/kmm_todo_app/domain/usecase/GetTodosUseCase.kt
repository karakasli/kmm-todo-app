package com.karakasli.kmm_todo_app.domain.usecase

import com.karakasli.kmm_todo_app.core.BaseUseCase
import com.karakasli.kmm_todo_app.core.UseCaseResult
import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.domain.mapper.TodoUIModelMapper
import com.karakasli.kmm_todo_app.ui.model.TodoUIModel
import com.karakasli.kmm_todo_app.domain.repository.TodoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
class GetTodosUseCase : BaseUseCase<Unit, List<TodoUIModel>>(), KoinComponent {

    private val todoRepository: TodoRepository by inject()
    private val todoUIMapper: TodoUIModelMapper by inject()

    override fun execute(param: Unit): UseCaseResult<List<TodoUIModel>> {
        val todos = todoRepository.getTodos()
        todos?.let {
            return UseCaseResult.Success(todos.map { todoUIMapper(it) })
        }
        return UseCaseResult.Error(AppError("query failed for get todos!"))
    }
}