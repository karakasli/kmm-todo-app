//
//  TodoDetailScreenViewModel.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 14.06.2022.
//

import Foundation
import shared

class TodoDetailViewModel: BaseViewModel {
    
    
    private let mGetTodoUseCase: GetTodoUseCase
    private let mUpdateTodoUseCase: UpdateTodoUseCase
    private let mCreateTodoUseCase: CreateTodoUseCase
    
    init(
        getTodoUseCase: GetTodoUseCase = GetTodoUseCase(),
        updateTodoUseCase: UpdateTodoUseCase = UpdateTodoUseCase(),
        createTodoUseCase: CreateTodoUseCase = CreateTodoUseCase()
    ) {
        mGetTodoUseCase = getTodoUseCase
        mUpdateTodoUseCase = updateTodoUseCase
        mCreateTodoUseCase = createTodoUseCase
    }
    
    var todoUid: String? = nil
    
    @Published var loadTodoEvent: BaseUIEvent<TodoDetailUIModel>!
    @Published var saveTodoEvent: BaseUIEvent<Bool>!
    @Published var todoDetailUIModel: TodoDetailUIModel? = nil
    
    func getTodoItem(_ todoUid: String) {
        
        callUseCase(
            call: {
                mGetTodoUseCase.invoke(param: NSString(utf8String: todoUid))
            },
            handleSuccess: {
                self.todoDetailUIModel = TodoDetailUIModel(
                    todo: $0
                )
                self.loadTodoEvent = .success(self.todoDetailUIModel!)
            },
            handleError: { _ in
                self.loadTodoEvent = .empty
            },
            isShowLoading: true
        )
        
    }
    
    func saveTodoItem(todoUid: String?, title: String?, body: String?, isCompleted: Bool) {
        
        if todoUid == nil {
            createTodoItem(
                TodoUIModel(uuid: NSUUID().uuidString, title: title!, body: body, isCompleted: isCompleted)
            )
        } else {
            updateTodoItem(
                TodoUIModel(uuid: todoUid!, title: title!, body: body, isCompleted: isCompleted)
            )
        }
    }
    
    private func createTodoItem(_ todoUiModel: TodoUIModel) {
        
        callUseCase(
            call: {
                mCreateTodoUseCase.invoke(param: todoUiModel)
            },
            handleSuccess: { result in
                self.saveTodoEvent = .success(true)
            },
            handleError: { _ in
                self.loadTodoEvent = .empty
            },
            isShowLoading: true
        )
        
    }
    
    private func updateTodoItem(_ todoUiModel: TodoUIModel) {
        
        callUseCase(
            call: {
                mUpdateTodoUseCase.invoke(param: todoUiModel)
            },
            handleSuccess: { result in
                self.saveTodoEvent = .success(true)
            },
            handleError: { _ in
                self.loadTodoEvent = .empty
            },
            isShowLoading: true
        )
        
    }
    
}
