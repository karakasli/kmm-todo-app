//
//  HomeScreenViewModel.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 10.06.2022.
//

import Foundation
import shared

class TodoListViewModel: BaseViewModel {

    private let mGetTodosUseCase: GetTodosUseCase
    private let mUpdateTodoStatusUseCase: UpdateTodoStatusUseCase
    private let mRemoveTodoUseCase: RemoveTodoUseCase
    
    init(
        getTodosUseCase: GetTodosUseCase = GetTodosUseCase(),
        updateTodoStatusUseCase: UpdateTodoStatusUseCase = UpdateTodoStatusUseCase(),
        removeTodoUseCase: RemoveTodoUseCase = RemoveTodoUseCase()
    ) {
        mGetTodosUseCase = getTodosUseCase
        mUpdateTodoStatusUseCase = updateTodoStatusUseCase
        mRemoveTodoUseCase = removeTodoUseCase
    }
    
    @Published var loadTodosEvent: BaseUIEvent<HomeScreenUIModel>!
    @Published var homeScreenUIModel: HomeScreenUIModel? = nil
    
    func getAllTodos() {
        
        callUseCase(
            call: {
                mGetTodosUseCase.invoke(param: KotlinUnit())
            },
            handleSuccess: { data in
                if let data = data as? [TodoUIModel] {
                    self.homeScreenUIModel = HomeScreenUIModel(
                        todos: data
                    )
                    
                    if(data.isEmpty == true) {
                        self.loadTodosEvent = .empty
                    } else {
                        self.loadTodosEvent = .success(self.homeScreenUIModel!)
                    }
                }
                
            },
            isShowLoading: true
        )

    }
    
    func removeTodo(todoUid: String) {
        
        callUseCase(
            call: {
                mRemoveTodoUseCase.invoke(param: NSString(utf8String: todoUid))
            },
            handleSuccess: {
                let _ = $0
                self.getAllTodos()
            },
            isShowLoading: true
        )
        
    }
    
    func updateTodoItemStatus(todoUid: String, isCompleted: Bool) {

        callUseCase(
            call: {
                mUpdateTodoStatusUseCase.invoke(
                    param: UpdateTodoStatusUseCase.Params(
                        uuid: todoUid,
                        isCompleted: isCompleted
                    )
                )
            },
            handleSuccess: {
                let _ = $0
                self.getAllTodos()
            }
        )
    }
    
}
