//
//  ViewController.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 10.06.2022.
//

import UIKit
import Combine


class TodoListViewController: BaseUIViewController<TodoListViewModel> {
        
    @IBOutlet weak var todoTableView: UITableView!
    @IBOutlet weak var noRecordContainer: UIView!
    
    var targetTodoUid: String? = nil
    
    override func initViews() {
        super.initViews()
        initTodoTableView()
    }
    
    override func getViewModel() -> BaseViewModel {
        return TodoListViewModel()
    }
    
    override func initObserves() {
        super.initObserves()
        
        viewModel.$loadTodosEvent.sink { loadTodoEvent in
            
            switch loadTodoEvent {
                case .success(let uiModel):
                    self.noRecordContainer.isHidden = true
                    self.todoTableView.isHidden = false
                    self.title = "Todos (\(uiModel.completedCount)/\(uiModel.count))"
                    self.todoTableView.reloadData()
                
                case .error(let error):
                    print(error.message ?? "")
                
                case .empty:
                    self.title = "Todos"
                    self.noRecordContainer.isHidden = false
                    self.todoTableView.isHidden = true
                
                case .none:
                    break
            }
        }.store(in: &viewControllerLifeCycle)
    }
    
    override func initDataFlow() {
        super.initDataFlow()
        viewModel.getAllTodos()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == TodoListK.goToTodoDetailSegueID {
            let targetController = segue.destination as? TodoDetailViewController
            targetController?.delegate = self
            if targetTodoUid != nil {
                targetController?.setTodoUId(todoUid: targetTodoUid!)
            }
            
        }
    }
    
    @IBAction func onNewTodoButtonClicked(_ sender: Any) {
        targetTodoUid = nil
        performSegue(withIdentifier: TodoListK.goToTodoDetailSegueID, sender: self)
    }
    
    func initTodoTableView() {
        todoTableView.dataSource = self
        todoTableView.delegate = self
        todoTableView.register(
            UINib(nibName: TodoListK.TodoListViewName, bundle: nil),
            forCellReuseIdentifier: TodoListK.TodoListItemID
        )
        todoTableView.allowsSelection = true
        todoTableView.separatorStyle = .none
    }
}

extension TodoListViewController: TodoDetailViewDelegate {
    func onScreenClosed() {
        viewModel.getAllTodos()
    }
}

extension TodoListViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = todoTableView.dequeueReusableCell(withIdentifier: TodoListK.TodoListItemID, for: indexPath) as! TodoListItem
        let cellData = viewModel.homeScreenUIModel?.todos[indexPath.row]
        
        cell.delegate = self
        cell.configureListItem(todoUIModel: cellData!)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Int(viewModel.homeScreenUIModel?.count ?? 0)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        targetTodoUid = viewModel.homeScreenUIModel?.todos[indexPath.row].uuid
        performSegue(withIdentifier: TodoListK.goToTodoDetailSegueID, sender: self)
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, editingStyleForRowAt indexPath: IndexPath) -> UITableViewCell.EditingStyle {
        return .delete
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let willRemoveTodoModel = viewModel.homeScreenUIModel?.todos[indexPath.row]
            showRemoveConfirmationDialog(willRemoveTodoModel?.title, indexPath: indexPath)
        }
    }
    
    private func showRemoveConfirmationDialog(_ title: String?, indexPath: IndexPath) {
        
        let deleteTodoDialog = UIAlertController(
            title: "Are you sure?",
            message: "You are removing \"\(title ?? "")\". This action cannot revert after processed.",
            preferredStyle: .alert
        )
        
        let saveAction = UIAlertAction(title: "Delete", style: .destructive) { action in
            let todoUid = self.viewModel.homeScreenUIModel?.todos[indexPath.row].uuid
            
            self.viewModel.homeScreenUIModel?.deleteTodo(index: Int32(indexPath.row))
            self.todoTableView.deleteRows(at: [indexPath], with: .fade)
            
            self.viewModel.removeTodo(todoUid: todoUid!)
        }
        
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel) { action in
            deleteTodoDialog.dismiss(animated: true)
        }
        
        deleteTodoDialog.addAction(saveAction)
        deleteTodoDialog.addAction(cancelAction)
        
        present(deleteTodoDialog, animated: true, completion: nil)
    }
}

extension TodoListViewController: TodoListItemDelegate {
    
    func onTodoCheckBoxChanged(todoUid: String, isChecked: Bool) {
        viewModel.homeScreenUIModel?.changeTodoStatus(uuid: todoUid, isCompleted: isChecked)
        viewModel.updateTodoItemStatus(todoUid: todoUid, isCompleted: isChecked)
        todoTableView.reloadData()
    }
}
