//
//  BaseViewModel.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 13.06.2022.
//

import Foundation
import shared

class BaseViewModel: ObservableObject {
    
    @Published var loadingEvent: LoadingEvent? = nil
    @Published var promptEvent: PromptEvent? = nil
    
    func showLoading() {
        loadingEvent = .LoadingShown()
    }
    
    func hideLoading() {
        loadingEvent = .LoadingHidden()
    }
    
    func callUseCase<T>(
        call: () -> UseCaseResult<T>?,
        handleSuccess: ((T) -> Void)?,
        handleError: ((AppError) -> Void)? = nil,
        isShowLoading: Bool = false
    ) {
        if isShowLoading {
            showLoading()
        }
            
        let result = call()
        
        
        if let result = result as? UseCaseResultSuccess {
            if let data = result.data {
                handleSuccess?(data)
            }
        } else if let error = result as? UseCaseResultError {
            handleError?(error.appError)
            print(error.appError.message ?? "error")
        } else if let error = result as? UseCaseResultValidationFailed {
            promptEvent = PromptEvent(title: error.validationError.title, message: error.validationError.message)
        }
        
        if isShowLoading {
            hideLoading()
        }
    }
}
