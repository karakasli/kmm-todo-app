package com.karakasli.kmm_todo_app.data.repository

import com.karakasli.kmm_todo_app.db.AppDatabase
import com.karakasli.kmm_todo_app.data.local.entity.TodoEntity
import com.karakasli.kmm_todo_app.domain.repository.TodoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
class TodoRepositoryImpl : TodoRepository, KoinComponent {

    private val database: AppDatabase by inject()

    override fun getTodo(uuid: String): TodoEntity? {
        return try {
            database.appDatabaseQueries.select(uuid = uuid) { _, title, body, is_completed ->
                TodoEntity(
                    uuid = uuid,
                    title = title,
                    body = body ?: "",
                    isCompleted = is_completed
                )
            }.executeAsOne()
        } catch (e: Exception) {
            null
        }
    }

    override fun getTodos(): List<TodoEntity>? {
        return try {
            database.appDatabaseQueries.selectAll { uuid, title, body, isCompleted ->
                TodoEntity(
                    uuid = uuid,
                    title = title,
                    body = body ?: "",
                    isCompleted = isCompleted
                )
            }.executeAsList()
        } catch (e: Exception) {
            return null
        }
    }

    override fun createTodo(
        uuid: String,
        title: String,
        body: String?,
        isCompleted: Boolean
    ): Boolean {
        return try {
            database.appDatabaseQueries.insert(
                uuid = uuid,
                title = title,
                body = body,
                is_completed = isCompleted
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun updateTodo(
        uuid: String,
        title: String,
        body: String?,
        isCompleted: Boolean
    ): Boolean {
        return try {
            database.appDatabaseQueries.update(
                title = title,
                body = body,
                is_completed = isCompleted,
                uuid = uuid
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun updateTodoStatus(uuid: String, isCompleted: Boolean): Boolean {
        return try {
            database.appDatabaseQueries.updateTodoStatus(
                uuid = uuid,
                is_completed = isCompleted
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun removeTodo(uuid: String): Boolean {
        return try {
            database.appDatabaseQueries.remove(
                uuid = uuid
            )
            true
        } catch (e: Exception) {
            false
        }
    }
}