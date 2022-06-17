package com.karakasli.kmm_todo_app.di

import com.karakasli.kmm_todo_app.data.local.platformModule
import com.karakasli.kmm_todo_app.data.repository.TodoRepositoryImpl
import com.karakasli.kmm_todo_app.domain.mapper.TodoUIModelMapper
import com.karakasli.kmm_todo_app.domain.repository.TodoRepository
import com.karakasli.kmm_todo_app.domain.usecase.*
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, platformModule())
}

// called by iOS etc
fun initKoin() = initKoin{}

val commonModule = module {
    single { GetTodoUseCase() }
    single { GetTodosUseCase() }
    single { CreateTodoUseCase() }
    single { UpdateTodoStatusUseCase() }
    single { UpdateTodoUseCase() }
    single { RemoveTodoUseCase() }

    single { TodoUIModelMapper() }

    single<TodoRepository> { TodoRepositoryImpl() }
}