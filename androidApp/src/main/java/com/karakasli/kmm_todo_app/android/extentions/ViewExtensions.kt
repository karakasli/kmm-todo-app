package com.karakasli.kmm_todo_app.android.extentions

import android.view.View

/**
 * Created by salihkarakasli on 29.03.2022
 */

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setIsVisible(isVisible: Boolean) {
    if (isVisible) this.visible() else this.gone()
}

fun View.setSpamProtectedClickListener(timeDelay: Long? = 1000L, onSafeClick: (View) -> Unit) {
    val safeClickListener =
        object : SpamProtectedClickListener(timeDelay) {
            override fun onClick(v: View) {
                super.onClick(v)
                onSafeClick(v)
            }
        }
    setOnClickListener(safeClickListener)
}