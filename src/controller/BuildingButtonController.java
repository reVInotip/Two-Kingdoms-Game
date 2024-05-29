package controller;

import utils.Observable;
import utils.events.ShowBuildingPanelEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildingButtonController extends Observable implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        update(new ShowBuildingPanelEvent());
    }
}