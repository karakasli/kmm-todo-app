package com.karakasli.kmm_todo_app.android.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.viewbinding.ViewBinding
import com.karakasli.kmm_todo_app.android.core.alias.InflateFragmentLayout
import com.karakasli.kmm_todo_app.data.model.event.LoadingEvent

/**
 * Created by salihkarakasli on 30.03.2022
 */
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    private val inflate: InflateFragmentLayout<VB>
) : Fragment() {

    protected var mViewBinding: VB? = null
    protected abstract val mFragmentViewModel: VM?
    protected abstract val mIsBottomNavigationVisible: Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = inflate.invoke(inflater, container, false)

        setBottomNavigationViewVisibility(mIsBottomNavigationVisible)

        return mViewBinding?.root
    }

    open fun initViews() {}
    open fun initListeners() {}
    open fun initCollectors() {

        lifecycleScope.launchWhenStarted {
            mFragmentViewModel?.pageLoadingEvent?.collect {
                when (it) {
                    LoadingEvent.LoadingHidden -> setPageLoadingState(false)
                    LoadingEvent.LoadingShown -> setPageLoadingState(true)
                }
            }
        }
    }

    open fun initDataFlows() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initCollectors()
        initDataFlows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewBinding = null
    }

    fun navigate(
        direction: NavDirections? = null,
        @IdRes actionId: Int? = null,
    ) {
        if (requireActivity() is BaseActivity<*, *>) {
            if (direction != null)
                (requireActivity() as BaseActivity<*, *>).navigate(direction = direction)

            if (actionId != null)
                (requireActivity() as BaseActivity<*, *>).navigate(actionId = actionId)
        }
    }

    fun navigateUp() {
        if (requireActivity() is BaseActivity<*, *>) {
            (requireActivity() as BaseActivity<*, *>).navigateUp()
        }
    }

    private fun setPageLoadingState(isPageLoading: Boolean) {
        if (requireActivity() is BaseActivity<*, *>) {
            (requireActivity() as BaseActivity<*, *>).setPageLoading(isPageLoading)
        }
    }

    private fun setBottomNavigationViewVisibility(isVisible: Boolean) {
        if (requireActivity() is BaseActivity<*, *>) {
            (requireActivity() as BaseActivity<*, *>).setBottomNavigationViewVisibility(isVisible)
        }
    }
}