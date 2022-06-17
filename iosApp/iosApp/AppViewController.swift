//
//  AppViewController.swift
//  iosApp
//
//  Created by Salih Karakaşlı on 15.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import shared

class AppViewController: UIViewController {

    @IBOutlet weak var tv: UILabel!
    
    let getTodosUseCase = GetTodosUseCase()
    let getTodoUsecase = GetTodoUseCase()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let todos = getTodosUseCase.invoke(param: KotlinUnit())
        
        if let todos = todos as? UseCaseResultSuccess {
            if let data = todos.data {
                print(data.count)
            }
        } else if let error = todos as? UseCaseResultError {
            print(error.appError.message ?? "error")
        } else if let error = todos as? UseCaseResultValidationFailed {
            print(error.validationError.title ?? "error")
        }
        
        let todo = getTodoUsecase.invoke(param: KotlinUnit())
        
        if let todo = todo as? UseCaseResultSuccess {
            if let data = todo.data {
                print(data.uuid)
            }
        } else if let error = todo as? UseCaseResultError {
            print(error.appError.message ?? "error")
        } else if let error = todos as? UseCaseResultValidationFailed {
            print(error.validationError.title ?? "error")
        }
    }
    

}
