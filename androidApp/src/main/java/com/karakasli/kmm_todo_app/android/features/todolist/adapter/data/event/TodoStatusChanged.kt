package com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.event

/**
 * Created by Salih Karakaşlı on 17.06.2022.
 */
data class TodoStatusChanged(
    val uuid: String,
    val isCompleted: Boolean,
)
