package model.building;

import model.UnitView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@UnitView(height = 50, width = 100, color = {0, 0, 0, 255}, type = "building", name = "mine")
public class Mine extends Building {
    private Map<String, Integer> resourcesForCreate =  new HashMap<>();
    private final Timer resourcesTimer = new Timer(5000, _ -> model.updateResourcesForPlayer(owner, productResources));

    public Mine() {
        productResources.put("iron", 3);
        resourcesForCreate.put("gold", 4);
        health = 800;
    }

    @Override
    public void kill() {
        super.kill();
        resourcesForCreate = null;
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
        return resourcesForCreate;
    }
}
