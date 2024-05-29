package controller;

import model.GameModel;
import view.GameFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseControllerForPanel extends MouseAdapter {
    private String unitForMove = null;
    private String unitForCreate = null;
    private final GameController mainController;

    public MouseControllerForPanel(GameController gameController) {
        mainController = gameController;
    }

    public void setUnitForCreate(String unitForCreate) {
        this.unitForCreate = unitForCreate;
    }

    public void setUnitForMove(String unitForMove) {
        this.unitForMove = unitForMove;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getY() < GameFrame.SCREEN_HEIGHT / 2) {
            return;
        }

        if (unitForCreate != null) {
            mainController.getGameModel().createHuman(unitForCreate, e.getX(), e.getY() - 35, GameModel.FIRST_OWNER);
            unitForCreate = null;
        }

        if (unitForMove != null) {
            mainController.getGameModel().setGroupMovementPoints(unitForMove, GameModel.FIRST_OWNER, e.getX(), e.getY() - 35);
            unitForMove = null;
        }
    }
}