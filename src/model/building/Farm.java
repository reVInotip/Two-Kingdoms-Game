package model.building;

import model.UnitView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@UnitView(height = 50, width = 100, color = {0, 255, 0, 255}, type = "building", name = "farm")
public class Farm extends Building {
    private Map<String, Integer> resourcesForCreate =  new HashMap<>();
    private final Timer resourcesTimer = new Timer(4000, _ -> model.updateResourcesForPlayer(owner, productResources));

    public Farm() {
        productResources.put("food", 2);
        resourcesForCreate.put("gold", 3);
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
