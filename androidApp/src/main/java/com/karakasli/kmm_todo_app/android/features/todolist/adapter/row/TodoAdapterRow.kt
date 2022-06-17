package com.karakasli.kmm_todo_app.android.features.todolist.adapter.row

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.karakasli.kmm_todo_app.android.R
import com.karakasli.kmm_todo_app.android.core.base.BaseAdapterRow
import com.karakasli.kmm_todo_app.android.core.base.BaseViewHolder
import com.karakasli.kmm_todo_app.android.databinding.AdapterRowTodoItemBinding
import com.karakasli.kmm_todo_app.android.extentions.setSpamProtectedClickListener
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.TodoAdapterData
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.event.TodoLongClicked
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.event.TodoStatusChanged

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoAdapterRow : BaseAdapterRow<TodoAdapterRow.ViewHolder, TodoAdapterData>() {

    override fun getViewType() = R.layout.adapter_row_todo_item

    override fun getDataType() = TodoAdapterData::class

    override fun onCreateViewHolder(parent: ViewGroup, adapterClick: ((Any) -> Unit)?): ViewHolder {
        val vh =
            AdapterRowTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(vh, adapterClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, data: TodoAdapterData, position: Int) {
        holder.bind(data, position)
    }

    inner class ViewHolder(
        binding: AdapterRowTodoItemBinding,
        private val adapterClick: ((Any) -> Unit)? = null,
    ) :
        BaseViewHolder<TodoAdapterData, AdapterRowTodoItemBinding>(binding) {
        override fun bindHolder(data: TodoAdapterData, position: Int) {
            binding.isCompletedCheckbox.isChecked = data.isCompleted

            if(data.isCompleted) {
                binding.root.alpha = 0.5f
                binding.tvTitle.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else {
                binding.root.alpha = 1f
                binding.tvTitle.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            binding.tvTitle.text = data.title
            binding.isCompletedCheckbox.onCheckBoxClicked = { isChecked ->
                adapterClick?.invoke(
                    TodoStatusChanged(
                        uuid = data.id ?: "",
                        isCompleted = isChecked
                    )
                )
            }
        }

        override fun setOnClickListener() {
            binding.root.setSpamProtectedClickListener {
                adapterClick?.invoke(holderData)
            }

            binding.root.setOnLongClickListener {
                adapterClick?.invoke(TodoLongClicked(holderData))
                it.showContextMenu()
                return@setOnLongClickListener true
            }
        }
    }
}