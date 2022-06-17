//
//  BaseUIEvent.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 13.06.2022.
//

import Foundation
import shared

enum BaseUIEvent<T> {
    case empty
    case error(AppError)
    case success(T)
}
