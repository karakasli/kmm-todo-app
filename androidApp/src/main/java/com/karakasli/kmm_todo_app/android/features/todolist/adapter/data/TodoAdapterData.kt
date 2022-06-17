package com.karakasli.kmm_todo_app.android.features.todolist.adapter.data

import com.karakasli.kmm_todo_app.android.core.base.BaseAdapterData

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
data class TodoAdapterData(
    override val id: String?,
    val title: String,
    val body: String?,
    val isCompleted: Boolean,
): BaseAdapterData
