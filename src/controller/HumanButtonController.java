package controller;

import utils.Observable;
import utils.events.ShowSoldierPanelEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanButtonController extends Observable implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        update(new ShowSoldierPanelEvent());
    }
}
