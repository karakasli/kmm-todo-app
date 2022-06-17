package com.karakasli.kmm_todo_app.android.extentions

import android.view.View

/**
 * Created by salihkarakasli on 29.03.2022
 */

open class SpamProtectedClickListener(
    private var delay: Long?,
) : View.OnClickListener {

    companion object {
        const val DEFAULT_DELAY = 1000L
    }

    override fun onClick(v: View) {
        v.isEnabled = false
        v.postDelayed({
            v.isEnabled = true
        }, delay ?: DEFAULT_DELAY)
    }
}