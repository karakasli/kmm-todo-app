package com.karakasli.kmm_todo_app.android.features.tododetail

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.karakasli.kmm_todo_app.android.core.base.BaseFragment
import com.karakasli.kmm_todo_app.android.databinding.FragmentTodoDetailBinding
import com.karakasli.kmm_todo_app.android.extentions.setSpamProtectedClickListener
import com.karakasli.kmm_todo_app.android.ui.event.UIEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoDetailFragment :
    BaseFragment<TodoDetailViewModel, FragmentTodoDetailBinding>(FragmentTodoDetailBinding::inflate),
    KoinComponent {

    override val mFragmentViewModel: TodoDetailViewModel by inject()
    override val mIsBottomNavigationVisible = false

    private val args: TodoDetailFragmentArgs by navArgs()

    override fun initViews() {
        super.initViews()

        mFragmentViewModel.mTodoId = args.todoUid

        mViewBinding?.btnSave?.setSpamProtectedClickListener {
            mFragmentViewModel.saveTodo(
                title = mViewBinding?.etTitle?.text.toString(),
                body = mViewBinding?.etBody?.text.toString(),
                isCompleted = mViewBinding?.ivCheck?.isChecked ?: false
            )
        }

        mViewBinding?.containerIsDoneCheck?.setSpamProtectedClickListener(300) {
            mViewBinding?.ivCheck?.toggle()
        }
    }

    override fun initDataFlows() {
        super.initDataFlows()
        mFragmentViewModel.getTodo()
    }

    override fun initCollectors() {
        super.initCollectors()

        lifecycleScope.launchWhenStarted {
            mFragmentViewModel.saveTodoEvent.collectLatest {
                when (it) {
                    is UIEvent.Success -> {
                        mFragmentViewModel.clearSaveTodoEvent()
                        navigateUp()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mFragmentViewModel.loadTodoEvent.collectLatest {
                when (it) {
                    is UIEvent.Success -> {
                        it.uiModel?.let { uiModel ->
                            mViewBinding?.etTitle?.setText(uiModel.title)
                            mViewBinding?.etBody?.setText(uiModel.body)
                            mViewBinding?.ivCheck?.isChecked = uiModel.isCompleted
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}