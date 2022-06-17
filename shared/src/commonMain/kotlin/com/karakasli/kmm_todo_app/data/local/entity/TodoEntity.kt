package com.karakasli.kmm_todo_app.data.local.entity

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
data class TodoEntity(
    val uuid: String,
    val title: String,
    val body: String,
    val isCompleted: Boolean,
)
