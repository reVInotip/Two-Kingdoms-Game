package controller;

import model.UnitsFactory;
import utils.Observable;
import utils.events.HidePathsEvent;
import utils.events.HideRangeEvent;
import utils.events.ShowPathsEvent;
import utils.events.ShowRangeEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController extends Observable implements KeyListener {
    private final MouseControllerForPanel mouseControllerForPanel;

    public KeyController(MouseControllerForPanel mouseControllerForPanel) {
        this.mouseControllerForPanel = mouseControllerForPanel;
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_R) {
            update(new ShowRangeEvent());
        } else if (event.getKeyCode() == KeyEvent.VK_P) {
            update(new ShowPathsEvent());
        } else {
            for (int i = 0; i < UnitsFactory.getCountHumans(); ++i) {
                if (event.getKeyCode() == KeyEvent.VK_1 + i) {
                    mouseControllerForPanel.setUnitForMove(UnitsFactory.getAvailableHumans().get(i));
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_R) {
            update(new HideRangeEvent());
        } else if (event.getKeyCode() == KeyEvent.VK_P) {
            update(new HidePathsEvent());
        }
    }
}
