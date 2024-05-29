package model.player;

import model.GameModel;
import model.UnitsFactory;
import utils.Observer;
import utils.events.AIEvent;
import utils.events.Event;
import view.GameFrame;

import javax.swing.*;
import java.awt.geom.Rectangle2D;

public class AI implements Observer {
    private boolean isMineCreated = false;
    private boolean isFarmCreated = false;
    private boolean isAttackMode = true;
    private final GameModel model;

    private static class AIPlayer extends Player {
        public AIPlayer(int spawnAreaWidth, int spawnAreaHeight, double x, double y) {
            spawnArea = new Rectangle2D.Double(x, y, spawnAreaWidth, spawnAreaHeight);
            this.name = "AI";
        }

        @Override
        public double[][] getBuildingsCoordinates() {
            return new double[][]{{GameFrame.SCREEN_WIDTH - 350, GameFrame.SCREEN_HEIGHT - 100},
                    {GameFrame.SCREEN_WIDTH - 350, GameFrame.SCREEN_HEIGHT / 2.0}};
        }
    }

    private final AIPlayer aiPlayer;

    public AI(int spawnAreaWidth, int spawnAreaHeight, GameModel model) {
        aiPlayer = new AIPlayer(spawnAreaWidth, spawnAreaHeight, 4.0 * GameFrame.SCREEN_WIDTH / 7.0, 3.0 * GameFrame.SCREEN_HEIGHT / 5.0);
        this.model = model;

        Timer createTimer = new Timer(5000, _ -> create());
        createTimer.start();
    }

    private void attack() {
        for (String soldier: UnitsFactory.getAvailableHumans()) {
            model.setGroupMovementPoints(soldier, GameModel.SECOND_OWNER, 0,
                    3 * GameFrame.SCREEN_HEIGHT / 5 + 50 + (int)(Math.random() * 20 - 10));
        }
    }

    private void retreat() {
        for (String soldier: UnitsFactory.getAvailableHumans()) {
            model.setGroupMovementPoints(soldier, GameModel.SECOND_OWNER, 4 * GameFrame.SCREEN_WIDTH / 7,
                    3 * GameFrame.SCREEN_HEIGHT / 5 + 50 + (int)(Math.random() * 20 - 10));
        }
    }

    private void create() {
        if (!isFarmCreated) {
            isFarmCreated = model.createBuilding("farm", GameModel.SECOND_OWNER);
        } else if (!isMineCreated) {
            isMineCreated = model.createBuilding("mine", GameModel.SECOND_OWNER);
        }

        if (isAttackMode) {
            model.createHuman("rider",
                    (int)(aiPlayer.getSpawnArea().getMinX() + Math.random() * aiPlayer.getSpawnArea().getWidth()),
                    (int)(aiPlayer.getSpawnArea().getMinY() + Math.random() * aiPlayer.getSpawnArea().getHeight()),
                    GameModel.SECOND_OWNER);
            model.createHuman("witch",
                    (int)(aiPlayer.getSpawnArea().getMinX() + Math.random() * aiPlayer.getSpawnArea().getWidth()),
                    (int)(aiPlayer.getSpawnArea().getMinY() + Math.random() * aiPlayer.getSpawnArea().getHeight()),
                    GameModel.SECOND_OWNER);
            attack();
        } else {
            model.createHuman("archer",
                    (int)(aiPlayer.getSpawnArea().getMinX() + Math.random() * aiPlayer.getSpawnArea().getWidth()),
                    (int)(aiPlayer.getSpawnArea().getMinY() + Math.random() * aiPlayer.getSpawnArea().getHeight()),
                    GameModel.SECOND_OWNER);
            model.createHuman("knight",
                    (int)(aiPlayer.getSpawnArea().getMinX() + Math.random() * aiPlayer.getSpawnArea().getWidth()),
                    (int)(aiPlayer.getSpawnArea().getMinY() + Math.random() * aiPlayer.getSpawnArea().getHeight()),
                    GameModel.SECOND_OWNER);
            retreat();
        }
    }

    public Player getAiPlayer() {
        return aiPlayer;
    }

    @Override
    public void update(Event event) {
        if (!(event instanceof AIEvent aiEvent)) {
            return;
        }

        isAttackMode = aiPlayer.currentCountSoldiers > aiEvent.currentCountSoldiers() + 1;
    }
}
