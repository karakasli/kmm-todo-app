package com.karakasli.kmm_todo_app.data.local

import com.karakasli.kmm_todo_app.db.AppDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */

actual fun platformModule() = module {
    single {
        val dbDriver = NativeSqliteDriver(AppDatabase.Schema,"app.db")
        AppDatabase(dbDriver)
    }
}