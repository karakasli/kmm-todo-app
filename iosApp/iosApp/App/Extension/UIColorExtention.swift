//
//  UIColorExtension.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 10.06.2022.
//

import Foundation
import UIKit

extension UIColor {
    
    /*
     * This function helps to convert rgb colors to the UIColor
     */
    static func parseFromHex(_ hex: String) -> UIColor {
        var clearHex = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        
        if(clearHex.hasPrefix("#")) {
            clearHex.remove(at: clearHex.startIndex)
        }
        
        if(clearHex.count != 6) {
            return UIColor.black
        }
        
        var rgbValue:UInt64 = 0
        Scanner(string: clearHex).scanHexInt64(&rgbValue)

        return UIColor(
            red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }
    
    static func unCheckedTodoCheckBox() -> UIColor {
        return UIColor(named: "cl_unchecked")!
    }
    
    static func checkedTodoCheckBox() -> UIColor {
        return UIColor(named: "cl_checked")!
    }
    
}
