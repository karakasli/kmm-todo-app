package com.karakasli.kmm_todo_app.domain.mapper

import com.karakasli.kmm_todo_app.core.BaseUIMapper
import com.karakasli.kmm_todo_app.data.local.entity.TodoEntity
import com.karakasli.kmm_todo_app.ui.model.TodoUIModel

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoUIModelMapper: BaseUIMapper<TodoEntity, TodoUIModel>() {
    override fun map(data: TodoEntity): TodoUIModel {
        return TodoUIModel(
            uuid = data.uuid,
            title = data.title,
            body = data.body,
            isCompleted = data.isCompleted
        )
    }
}