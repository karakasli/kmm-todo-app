package com.karakasli.kmm_todo_app.android.ui.views.todoCheckBox

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.karakasli.kmm_todo_app.android.R
import com.karakasli.kmm_todo_app.android.extentions.setSpamProtectedClickListener

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    enum class State {
        Checked, UnChecked
    }

    init {
        setColorFilter(ContextCompat.getColor(context, R.color.checkboxImageColor))
        setSpamProtectedClickListener(timeDelay = 300) {
            toggle()
        }
    }


     var state: State = State.UnChecked
        set(value) {
            when(value) {
                State.UnChecked -> setAsUnChecked()
                State.Checked -> setChecked()
            }
            field = value
        }

    var isChecked: Boolean = false
        get() = state == State.Checked
        set(value) {
            field = value
            state = if(value) {
                State.Checked
            } else {
                State.UnChecked
            }
        }

    var onCheckBoxClicked: ((Boolean) -> Unit)? = null

    private fun setAsUnChecked() {
        setImageDrawable(null)
        background = AppCompatResources.getDrawable(context, R.drawable.bg_todo_checkbox_unchecked_state)
    }

    private fun setChecked() {
        setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_done))

        background = AppCompatResources.getDrawable(context, R.drawable.bg_todo_checkbox_checked_state)
    }

    fun toggle() {
        isChecked = isChecked.not()
        onCheckBoxClicked?.invoke(isChecked)
    }
}