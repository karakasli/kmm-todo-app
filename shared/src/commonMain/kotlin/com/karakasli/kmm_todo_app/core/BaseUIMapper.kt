package com.karakasli.kmm_todo_app.core

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
abstract class BaseUIMapper<I, O> {
    abstract fun map(data: I): O

    operator fun invoke(input: I): O {
        return map(input)
    }

}