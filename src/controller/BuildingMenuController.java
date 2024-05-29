package controller;

import model.GameModel;
import utils.Observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildingMenuController extends Observable implements ActionListener {
    private final GameController mainController;

    public BuildingMenuController(GameController gameController) {
        mainController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainController.getGameModel().createBuilding(e.getActionCommand(), GameModel.FIRST_OWNER);
    }
}
