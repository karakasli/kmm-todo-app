package com.karakasli.kmm_todo_app.android.di

import com.karakasli.kmm_todo_app.android.features.main.MainViewModel
import com.karakasli.kmm_todo_app.android.features.tododetail.TodoDetailViewModel
import com.karakasli.kmm_todo_app.android.features.todolist.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { TodoListViewModel() }
    viewModel { TodoDetailViewModel() }
}