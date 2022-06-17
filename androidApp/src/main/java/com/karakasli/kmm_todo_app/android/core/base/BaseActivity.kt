package com.karakasli.kmm_todo_app.android.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.karakasli.kmm_todo_app.android.extentions.safeNavigate
import com.karakasli.kmm_todo_app.android.extentions.setIsVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karakasli.kmm_todo_app.android.core.alias.InflateActivityLayout

/**
 * Created by salihkarakasli on 30.03.2022
 */

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding>(
    private val inflate: InflateActivityLayout<VB>
) : AppCompatActivity() {

    /**
     * ViewBinding object of activity
     */
    protected var mViewBinding: VB? = null
    protected abstract val mActivityViewModel: VM

    protected open fun initSplash() {}
    protected open fun initViews() {}
    protected open fun initDataFlow() {}
    protected open fun initCollectors() {}

    abstract fun getPageLoadingOverlayView(): View?
    abstract fun getBottomNavigationView(): BottomNavigationView?

    protected var navHost: NavHostFragment? = null

    /**
     * If you want to enable page loading feature use this function
     */
    fun setPageLoading(isPageLoading: Boolean) {
        getPageLoadingOverlayView()?.setIsVisible(isPageLoading)
    }

    /**
     *  If you want to change visibility of bottom navigation view, use this function
     */
    fun setBottomNavigationViewVisibility(isVisible: Boolean) {
        getBottomNavigationView()?.setIsVisible(isVisible)
    }

    /**
     * If you want to use custom nav controller, override this method in your activity
     * @NavController nav controller object of activity.
     */
    open fun getNavHostFragment(): NavHostFragment? = null

    /**
     * onCreate method of base activity
     * it provides view binding object and nav controller
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplash()

        mViewBinding = inflate.invoke(layoutInflater)
        setContentView(mViewBinding?.root)

        navHost = getNavHostFragment()
        navHost?.let {
            getBottomNavigationView()?.setupWithNavController(it.navController)
        }

        initCollectors()
        initDataFlow()
        initViews()
    }

    fun navigate(
        direction: NavDirections? = null,
        @IdRes actionId: Int? = null,
    ) {
        if (direction != null)
            navHost?.navController?.safeNavigate(direction)
        if (actionId != null)
            navHost?.navController?.safeNavigate(actionId)
    }

    fun navigateUp() = navHost?.navController?.navigateUp()

    protected fun setStartDestinationOfNavGraph(navGraphId: Int, destinationId: Int) {
        val inflater = navHost?.navController?.navInflater
        val graph = inflater?.inflate(navGraphId)
        graph?.setStartDestination(destinationId)

        graph?.let {
            navHost?.navController?.graph = graph
        }
    }
}