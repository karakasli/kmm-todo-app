//
//  TodoDetailScreenController.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 14.06.2022.
//

import UIKit

protocol TodoDetailViewDelegate {
    func onScreenClosed()
}

class TodoDetailViewController: BaseUIViewController<TodoDetailViewModel> {

    var todoUid: String? = nil
    var delegate: TodoDetailViewDelegate?
    
    @IBOutlet weak var tvTodoBody: UITextView!
    @IBOutlet weak var tvTodoTitle: UITextField!
    @IBOutlet weak var cbCompleted: TodoCheckBox!
    @IBOutlet weak var cbCompleteContainer: UIStackView!
    @IBOutlet weak var btnDone: UIButton!
    
    override func getViewModel() -> BaseViewModel {
        return TodoDetailViewModel()
    }
    
    override func initViews() {
        tvTodoBody.layer.cornerRadius = 16
        tvTodoTitle.layer.cornerRadius = 16
        btnDone.layer.cornerRadius = 16
        
        cbCompleteContainer.isUserInteractionEnabled = true
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(onMarkedAsDoneClicked))
        cbCompleteContainer.addGestureRecognizer(tapGestureRecognizer)
    }
    
    override func initDataFlow() {
        if (todoUid != nil) {
            viewModel.getTodoItem(todoUid!)
        }
    }
    
    override func initObserves() {
        super.initObserves()
        
        viewModel.$loadTodoEvent.sink{
            switch $0 {
                case .success(let uiModel):
                    self.tvTodoTitle.text = uiModel.todo?.title
                    self.tvTodoBody.text = uiModel.todo?.body
                    self.cbCompleted.state = uiModel.todo?.isCompleted == true ? .checked : .unchecked
                    break
                case .empty:
                    break
                
                case .error(let error):
                    print(error.message ?? "")
                    break
                
                case .none:
                     break
            }
        }.store(in: &viewControllerLifeCycle)
        
        viewModel.$saveTodoEvent.sink {
            switch $0 {
                case .success(_):
                    self.delegate?.onScreenClosed()
                    self.dismiss(animated: true)
                case .error(let error):
                    print(error.message ?? "")
                case .empty:
                    break
                case .none:
                    break
            }
        }.store(in: &viewControllerLifeCycle)
    }
    
    @IBAction func onSaveButtonClicked(_ sender: Any) {
        
        viewModel.saveTodoItem(
            todoUid: viewModel.todoDetailUIModel?.todo?.uuid,
            title: tvTodoTitle.text!,
            body: tvTodoBody.text,
            isCompleted: cbCompleted.isChecked
        )
    }
    
    @objc private func onMarkedAsDoneClicked() {
        cbCompleted.toggleState()
    }
    
    func setTodoUId(todoUid: String) {
        self.todoUid = todoUid
    }
}
