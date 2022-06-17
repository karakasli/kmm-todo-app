package com.karakasli.kmm_todo_app.domain.repository

import com.karakasli.kmm_todo_app.data.local.entity.TodoEntity

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
interface TodoRepository {
    fun getTodo(uuid: String): TodoEntity?
    fun getTodos(): List<TodoEntity>?
    fun createTodo(uuid: String, title: String, body: String?, isCompleted: Boolean): Boolean
    fun updateTodo(uuid: String, title: String, body: String?, isCompleted: Boolean): Boolean
    fun updateTodoStatus(uuid: String, isCompleted: Boolean): Boolean
    fun removeTodo(uuid: String): Boolean
}