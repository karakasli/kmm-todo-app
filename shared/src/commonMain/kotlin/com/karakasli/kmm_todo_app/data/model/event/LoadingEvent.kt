package com.karakasli.kmm_todo_app.data.model.event

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
sealed class LoadingEvent {
    object LoadingShown: LoadingEvent()
    object LoadingHidden: LoadingEvent()
}

