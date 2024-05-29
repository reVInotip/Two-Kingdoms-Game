package controller;

import model.GameModel;
import model.UnitsFactory;
import utils.Observable;
import utils.Observer;
import view.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController extends Observable implements ActionListener {
    private final MouseControllerForPanel mouseControllerForPanel;
    private final HumanButtonController humanButtonController;
    private final SoldierMenuController soldierMenuController;
    private final BuildingButtonController buildingButtonController;
    private final BuildingMenuController buildingMenuController;
    private final KeyController keyController;
    private final GameModel gameModel;

    public GameController() {
        UnitsFactory.initFactory();
        mouseControllerForPanel = new MouseControllerForPanel(this);
        humanButtonController = new HumanButtonController();
        buildingButtonController = new BuildingButtonController();
        buildingMenuController = new BuildingMenuController(this);
        soldierMenuController = new SoldierMenuController(mouseControllerForPanel);
        keyController = new KeyController(mouseControllerForPanel);
        gameModel = new GameModel();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

    public MouseControllerForPanel getMouseController() {
        return mouseControllerForPanel;
    }

    public KeyController getKeyController() {
        return keyController;
    }

    public HumanButtonController getSoldierButtonController() {
        return humanButtonController;
    }

    public SoldierMenuController getSoldierMenuController() {
        return soldierMenuController;
    }

    public BuildingButtonController getBuildingButtonController() {
        return buildingButtonController;
    }

    public BuildingMenuController getBuildingMenuController() {
        return buildingMenuController;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void run() {
        JFrame gameFrame = new GameFrame(this, gameModel.getPlayerSpawnArea(), gameModel.getPlayerResources(GameModel.FIRST_OWNER));
        gameModel.createCastles();
    }

    @Override
    public void add(Observer ob) {
        super.add(ob);
        humanButtonController.add(ob);
        buildingButtonController.add(ob);
        gameModel.add(ob);
        keyController.add(ob);
    }
}
