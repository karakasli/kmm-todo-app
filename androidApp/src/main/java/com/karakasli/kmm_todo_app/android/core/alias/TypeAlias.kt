package com.karakasli.kmm_todo_app.android.core.alias

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by salihkarakasli on 30.03.2022
 */

typealias InflateFragmentLayout<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias InflateActivityLayout<T> = (LayoutInflater) -> T