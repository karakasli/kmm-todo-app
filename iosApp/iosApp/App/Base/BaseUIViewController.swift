//
//  BaseUIViewController.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 10.06.2022.
//

import Foundation
import Combine
import UIKit
import shared

class BaseUIViewController<T: BaseViewModel>: UIViewController {
    
    var viewControllerLifeCycle = Set<AnyCancellable>()
    var viewModel: T!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = (getViewModel() as? T)
        setNavigation()
        initViews()
        initObserves()
        initDataFlow()
    }
    
    private func setNavigation() {
        self.navigationController?.navigationBar.prefersLargeTitles = true
    }
    
    /*
     * Default observers for view controllers
     */
    func initObserves() {
        viewModel.$loadingEvent.sink {
            
            if let _ = $0 as? LoadingEvent.LoadingHidden {
                //TODO: Show loading anim
            } else {
                //TODO: Hide loading anim
            }
        
        }.store(in: &viewControllerLifeCycle)
        
        viewModel.$promptEvent.sink {
            
            guard $0 != nil else {
                return
            }
            
            let promptDialog = UIAlertController(title: $0?.title, message: $0?.message, preferredStyle: .alert)
            let action = UIAlertAction(title: "OK", style: .cancel)
            promptDialog.addAction(action)
            self.present(promptDialog, animated: true)
            
        }.store(in: &viewControllerLifeCycle)
    }
    
    /*
     *  This function will be overriden by view controller
     *  Initialization of UI Views should be completed in this method.
     */
    func initViews() {}
    
    /*
     * This function will be overriden by view controller to
     * pass viewmodel instance to the base view controller.
     */
    func getViewModel() -> BaseViewModel? { return nil }
    
    /*
     *  This function will be overriden by view controller
     *  Initialization of data flow should be triggerd in this method.
     */
    func initDataFlow() {}
}
