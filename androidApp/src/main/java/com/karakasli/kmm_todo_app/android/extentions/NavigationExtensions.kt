package com.karakasli.kmm_todo_app.android.extentions

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Created by salihkarakasli on 30.03.2022
 */

fun NavController.safeNavigate(direction: NavDirections? = null) {
    direction?.let {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(it)
        }
    }
}

fun NavController.safeNavigate(@IdRes actionId: Int? = null) {
    actionId?.let {
        currentDestination?.getAction(actionId)?.run {
            navigate(it)
        }
    }
}

fun NavController.safeNavigate(
    @IdRes destinationId: Int,
    @IdRes id: Int
) {
    if (currentDestination?.id != destinationId) {
        navigate(id)
    }
}