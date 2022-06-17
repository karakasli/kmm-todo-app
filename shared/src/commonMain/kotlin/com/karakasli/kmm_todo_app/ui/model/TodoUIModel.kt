package com.karakasli.kmm_todo_app.ui.model

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
data class TodoUIModel(
    val uuid: String,
    val title: String,
    val body: String?,
    var isCompleted: Boolean
)
