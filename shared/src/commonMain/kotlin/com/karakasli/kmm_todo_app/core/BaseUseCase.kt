package com.karakasli.kmm_todo_app.core

import com.karakasli.kmm_todo_app.data.model.error.ValidationError

/**
 * Created by Salih Karakaşlı on 15.06.2022.
 */
abstract class BaseUseCase<I, O> {

    /**
     * This is actual execution block for implemented use cases.
     */
    protected abstract fun execute(param: I): UseCaseResult<O>

    /**
     * This function helps to check use case params by overriding it inside implemented use cases.
     * It returns list of validation errors, emptyList means there is not any error,
     * To simplify handling of validations on UI, just first of the validation error is sent via UseCaseResult
     */
    protected open fun checkParamsForValidation(param: I): List<ValidationError> {
        return emptyList()
    }

    /**
     * You have to call operator function to execute any use case which is inherited from BaseUseCase
     */
    operator fun invoke(param: I): UseCaseResult<O> {
        val validationErrors = checkParamsForValidation(param)
        if(validationErrors.isNotEmpty()) {
            return UseCaseResult.ValidationFailed(validationErrors.first())
        }
        return execute(param)
    }
}