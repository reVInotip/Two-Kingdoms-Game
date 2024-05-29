package controller;

import utils.Observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoldierMenuController extends Observable implements ActionListener {
    private final MouseControllerForPanel mouseControllerForPanel;

    public SoldierMenuController(MouseControllerForPanel mouseControllerForPanel) {
        this.mouseControllerForPanel = mouseControllerForPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mouseControllerForPanel.setUnitForCreate(e.getActionCommand());
    }
}
