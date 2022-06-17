package com.karakasli.kmm_todo_app.android.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by salihkarakasli on 4.04.2022
 */

abstract class BaseViewHolder<D : BaseAdapterData, T : ViewBinding>(
    protected val binding: T,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        setOnClickListener()
    }

    protected lateinit var holderData: D
    protected abstract fun bindHolder(data: D, position: Int)
    protected abstract fun setOnClickListener()

    fun bind(data: D, position: Int) {
        holderData = data
        bindHolder(data, position)
    }
}