package model.player;

import model.AbstractUnit;
import utils.Observable;
import utils.events.AIEvent;
import view.GameFrame;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Player extends Observable {
    protected final Map<String, Integer> resources = new HashMap<>();
    protected Rectangle2D spawnArea;
    protected String name;

    public int currentCountSoldiers = 0;
    public int currentCountBuildings = 0;

    public Player() {
        spawnArea = null;
        name = "undefined";

        resources.put("food", 50);
        resources.put("gold", 0);
        resources.put("iron", 0);
    }

    public Player(int spawnAreaWidth, int spawnAreaHeight, String name, AI ai) {
        spawnArea = new Rectangle2D.Double(
                1.0 * GameFrame.SCREEN_WIDTH / 7.0,
                3.0 * GameFrame.SCREEN_HEIGHT / 5.0,
                spawnAreaWidth, spawnAreaHeight);

        resources.put("food", 50);
        resources.put("gold", 0);
        resources.put("iron", 0);

        add(ai);

        this.name = name;
    }

    public Rectangle2D getSpawnArea() {
        return spawnArea;
    }

    public Map<String, Integer> getResources() {
        return resources;
    }

    public void updateResources(String resource, int diff) {
        resources.replace(resource, resources.get(resource) + diff);
    }

    public void addSoldier() {
        ++currentCountSoldiers;
        update(new AIEvent(currentCountSoldiers));
    }

    public void addBuilding() {
        ++currentCountBuildings;
    }

    public void eraseSoldier() {
        --currentCountSoldiers;
        update(new AIEvent(currentCountSoldiers));
    }

    public void updateInfo() {
        update(new AIEvent(currentCountSoldiers));
    }

    public void eraseBuilding() {
        ++currentCountBuildings;
    }

    public boolean canNotCreateUnit(AbstractUnit unit) {
        if (unit == null) {
            return true;
        }

        Map<String, Integer> playerResources = getResources();
        Map<String, Integer> resourcesForCreate = unit.getResourcesForCreate();

        for (String resource: resourcesForCreate.keySet()) {
            if (playerResources.get(resource) < resourcesForCreate.get(resource)) {
                return true;
            }
        }

        return false;
    }

    public double[][] getBuildingsCoordinates() {
        return new double[][]{{300, GameFrame.SCREEN_HEIGHT - 100}, {300, GameFrame.SCREEN_HEIGHT / 2.0}};
    }
}
