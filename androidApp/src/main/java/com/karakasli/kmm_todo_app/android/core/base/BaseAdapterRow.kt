package com.karakasli.kmm_todo_app.android.core.base

import android.view.ViewGroup
import kotlin.reflect.KClass

/**
 * Created by salihkarakasli on 7.04.2022
 */

abstract class BaseAdapterRow<VH : BaseViewHolder<*, *>, D : BaseAdapterData>() {
    abstract fun getViewType(): Int
    abstract fun getDataType(): KClass<out BaseAdapterData>
    abstract fun onCreateViewHolder(parent: ViewGroup, adapterClick: ((Any) -> Unit)? = null): VH
    abstract fun onBindViewHolder(holder: VH, data: D, position: Int)
}