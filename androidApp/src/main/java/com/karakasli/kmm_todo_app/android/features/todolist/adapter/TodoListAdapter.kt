package com.karakasli.kmm_todo_app.android.features.todolist.adapter

import com.karakasli.kmm_todo_app.android.core.base.BaseAdapter
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.row.TodoAdapterRow


/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoListAdapter(
    adapterClick: ((Any) -> Unit)? = null,
) : BaseAdapter(
    adapterRowTypes = listOf(
        TodoAdapterRow()
    ),
    adapterClick = adapterClick
)