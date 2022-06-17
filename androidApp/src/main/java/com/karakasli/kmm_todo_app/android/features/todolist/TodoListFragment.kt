package com.karakasli.kmm_todo_app.android.features.todolist

import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.karakasli.kmm_todo_app.android.R
import com.karakasli.kmm_todo_app.android.ui.event.UIEvent
import com.karakasli.kmm_todo_app.android.core.base.BaseFragment
import com.karakasli.kmm_todo_app.android.databinding.FragmentTodoListBinding
import com.karakasli.kmm_todo_app.android.extentions.gone
import com.karakasli.kmm_todo_app.android.extentions.setSpamProtectedClickListener
import com.karakasli.kmm_todo_app.android.extentions.visible
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.TodoListAdapter
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.TodoAdapterData
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.event.TodoLongClicked
import com.karakasli.kmm_todo_app.android.features.todolist.adapter.data.event.TodoStatusChanged
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Salih Karakaşlı on 16.06.2022.
 */
class TodoListFragment: BaseFragment<TodoListViewModel, FragmentTodoListBinding>(FragmentTodoListBinding::inflate) {

    override val mFragmentViewModel: TodoListViewModel by viewModel()
    override val mIsBottomNavigationVisible: Boolean = false

    private val todoListAdapter: TodoListAdapter by lazy {
        TodoListAdapter(::onTodoListAdapterClicked)
    }

    override fun onResume() {
        super.onResume()
        mFragmentViewModel.getAllTodos()
    }

    private fun onTodoListAdapterClicked(data: Any?) {
        when (data) {
            is TodoAdapterData -> {
                navigate(TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment(data.id))
            }
            is TodoStatusChanged -> {
                mFragmentViewModel.updateTodoStatus(todoUid = data.uuid, isCompleted = data.isCompleted)
            }
            is TodoLongClicked -> {
                mFragmentViewModel.selectedTodoItem = data.todo
            }
        }
    }

    override fun initViews() {
        super.initViews()

        mViewBinding?.todoListRecycler?.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            registerForContextMenu(this)
            itemAnimator = null
        }

        mViewBinding?.btnAdd?.setSpamProtectedClickListener {
            onClickedAddNewItem()
        }

        mViewBinding?.btnAddEmpty?.setSpamProtectedClickListener {
            onClickedAddNewItem()
        }
    }

    private fun onClickedAddNewItem() {
        navigate(TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment())
    }

    override fun initDataFlows() {
        super.initDataFlows()
        mFragmentViewModel.getAllTodos()
    }

    override fun initCollectors() {
        super.initCollectors()

        lifecycleScope.launchWhenStarted {
            mFragmentViewModel.loadTodosEvent.collectLatest {
                when(it) {
                    is UIEvent.Empty -> {
                        mViewBinding?.tvTitle?.text = "Todos"
                        mViewBinding?.containerEmpty?.visible()
                        mViewBinding?.todoListRecycler?.gone()
                    }
                    is UIEvent.Success -> {

                        mViewBinding?.tvTitle?.text = "Todos (${it.uiModel?.completedCount}/${it.uiModel?.count})"
                        mViewBinding?.containerEmpty?.gone()
                        mViewBinding?.todoListRecycler?.visible()

                        todoListAdapter.submitList(
                            it.uiModel?.todos?.map { todo ->
                                TodoAdapterData(
                                    id = todo.uuid,
                                    title = todo.title,
                                    body = todo.body,
                                    isCompleted = todo.isCompleted
                                )
                            }
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.todo_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.delete -> {
                mFragmentViewModel.selectedTodoItem?.let { removingTodo ->
                    MaterialDialog(requireContext()).show {
                        title(text = "Are you sure?")
                        message(text = "You are removing \"${removingTodo.title}\". This action cannot revert after processed.")
                        cancelOnTouchOutside(true)
                        positiveButton(text = "Delete") {
                            mFragmentViewModel.removeTodo(removingTodo.id!!)
                        }
                        negativeButton(text = "Cancel")
                    }
                }
            }
        }
        return true
    }

}