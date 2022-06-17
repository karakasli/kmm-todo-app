//
//  TodoCheckBox.swift
//  ToDoTracker
//
//  Created by Salih Karakaşlı on 13.06.2022.
//

import UIKit

protocol TodoCheckBoxDelegate {
    func onCheckBoxChanged(isChecked: Bool)
}

final class TodoCheckBox: UIImageView {

    enum State {
        case checked
        case unchecked
    }
    
    var state: State = .unchecked {
        willSet(newState) {
            switch newState {
                case .checked: setAsChecked()
                case .unchecked: setAsUnChecked()
            }
        }
    }
    
    var isChecked: Bool {
        return state == .checked
    }
    
    var delegate: TodoCheckBoxDelegate?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupView()
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupView()
    }
    
    func setupView() {
        state = .unchecked
        self.layer.cornerRadius = self.layer.visibleRect.width / 3
        self.isUserInteractionEnabled = true
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(onCheckBoxClicked))
        addGestureRecognizer(tapGestureRecognizer)
    }
        
    private func setAsChecked() {
        image = UIImage.done()
        self.image = self.image?.withRenderingMode(.alwaysTemplate)
        self.tintColor = .white
        backgroundColor = UIColor.checkedTodoCheckBox()
    }
    
    private func setAsUnChecked() {
        image = .none
        backgroundColor = UIColor.unCheckedTodoCheckBox()
    }
    
    func toggleState() {
        state = state == .checked ? .unchecked : .checked
    }
    
    @objc private func onCheckBoxClicked() {
        delegate?.onCheckBoxChanged(isChecked: state == .checked ? false : true)
        toggleState()
    }
}
