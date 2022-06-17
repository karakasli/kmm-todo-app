//
//  TodoListItem.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 13.06.2022.
//

import UIKit
import shared

protocol TodoListItemDelegate {
    func onTodoCheckBoxChanged(todoUid: String, isChecked: Bool)
}

class TodoListItem: UITableViewCell {

    @IBOutlet weak var todoLabel: UILabel!
    @IBOutlet weak var isCompletedButton: TodoCheckBox!
    
    @IBOutlet weak var uiView: UIView!
    
    var delegate: TodoListItemDelegate?
    
    var todoUIModel: TodoUIModel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        isCompletedButton.delegate = self
        uiView.backgroundColor = .white
        uiView.layer.cornerRadius = 15
    }
    
    func configureListItem(todoUIModel: TodoUIModel) {
        self.todoUIModel = todoUIModel
        
        let attributeString: NSMutableAttributedString = NSMutableAttributedString(string: todoUIModel.title)
        if(todoUIModel.isCompleted == true) {
            self.isCompletedButton.state = .checked
            todoLabel.alpha = 0.5
            attributeString.setAttributes([NSAttributedString.Key.strikethroughStyle: 1], range: NSRange(location: 0, length: attributeString.length))
            todoLabel.attributedText = attributeString
        } else {
            self.isCompletedButton.state = .unchecked
            todoLabel.alpha = 1
            attributeString.removeAttribute(NSAttributedString.Key.strikethroughStyle, range: NSRange(location: 0, length: attributeString.length))
            todoLabel.attributedText = attributeString
        }
    }
}

extension TodoListItem: TodoCheckBoxDelegate {
    func onCheckBoxChanged(isChecked: Bool) {
        self.delegate?.onTodoCheckBoxChanged(todoUid: todoUIModel.uuid, isChecked: isChecked)
    }
}
