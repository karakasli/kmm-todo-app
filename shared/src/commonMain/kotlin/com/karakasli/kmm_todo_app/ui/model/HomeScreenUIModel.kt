package com.karakasli.kmm_todo_app.ui.model

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
data class HomeScreenUIModel(
    var todos: List<TodoUIModel> = emptyList()
) {

    fun deleteTodo(index: Int) {
        todos = todos.filterIndexed { mapIndex, _ ->
            mapIndex != index
        }
    }

    fun changeTodoStatus(uuid: String, isCompleted: Boolean) {
        todos = todos.map {
            if (uuid == it.uuid)
                it.isCompleted = isCompleted
            it
        }
    }

    val completedCount: Int
        get() = todos.count { it.isCompleted }

    val count: Int
        get() = todos.size
}
