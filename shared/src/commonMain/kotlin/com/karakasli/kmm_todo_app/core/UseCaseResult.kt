package com.karakasli.kmm_todo_app.core

import com.karakasli.kmm_todo_app.data.model.error.AppError
import com.karakasli.kmm_todo_app.data.model.error.ValidationError

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
sealed class UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class ValidationFailed<T>(val validationError: ValidationError): UseCaseResult<T>()
    data class Error<T>(val appError: AppError): UseCaseResult<T>()
}