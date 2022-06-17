package com.karakasli.kmm_todo_app.android.ui.event

import com.karakasli.kmm_todo_app.data.model.error.AppError

/**
 * Created by salihkarakasli on 5.04.2022
 */

sealed class UIEvent<out T> {
    data class Success<out T>(val uiModel: T?) : UIEvent<T>()
    data class Failed(val errors: AppError) : UIEvent<Nothing>()
    object Empty : UIEvent<Nothing>()
    object Idle : UIEvent<Nothing>()
}
