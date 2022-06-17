package com.karakasli.kmm_todo_app.android.features.main

import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karakasli.kmm_todo_app.android.R
import com.karakasli.kmm_todo_app.android.core.base.BaseActivity
import com.karakasli.kmm_todo_app.android.core.base.BaseViewModel
import com.karakasli.kmm_todo_app.android.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<BaseViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val mActivityViewModel: MainViewModel by viewModel()

    override fun getPageLoadingOverlayView(): View? {
        return null
    }

    override fun getBottomNavigationView(): BottomNavigationView? {
        return null
    }

    override fun getNavHostFragment(): NavHostFragment {
        return (supportFragmentManager.findFragmentById(R.id.main_activity_fragment_container_view) as NavHostFragment)
    }

    override fun initSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mActivityViewModel.isSplashCompleted.not()
            }
        }
    }
}
