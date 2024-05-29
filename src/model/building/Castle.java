package model.building;

import model.UnitView;

import javax.swing.*;
import java.util.Map;

@UnitView(height = 100, width = 80, color = {0, 0, 0, 255}, type = "castle", name = "castle")
public class Castle extends Building {
    private final Timer resourcesTimer = new Timer(5000, _ -> model.updateResourcesForPlayer(owner, productResources));

    public Castle() {
        productResources.put("gold", 1);
        health = 10000;
    }

    @Override
    public double getDamage() {
        return 0;
    }

    @Override
    public void initResourcesTimer() {
        resourcesTimer.start();
    }

    @Override
    public void stopResourcesTimer() {
        resourcesTimer.start();
    }

    @Override
    public Map<String, Integer> getResourcesForCreate() {
        return null;
    }
}
