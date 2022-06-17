package com.karakasli.kmm_todo_app.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.karakasli.kmm_todo_app.BuildConfig
import com.karakasli.kmm_todo_app.android.di.appModule
import com.karakasli.kmm_todo_app.di.commonModule
import com.karakasli.kmm_todo_app.di.initKoin
import org.koin.android.ext.koin.androidContext

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initKoin {
            androidContext(this@TodoApplication)
            modules(appModule, commonModule)
        }


    }

    companion object {
        fun isDebugMode() = BuildConfig.DEBUG
    }
}