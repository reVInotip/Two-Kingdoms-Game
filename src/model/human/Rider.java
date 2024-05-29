package model.human;

import model.UnitView;

@UnitView(color = {1, 1, 255, 255}, type = "human", name = "rider")
public class Rider extends MeleeFighter {
    public Rider() {
        resources.put("food", 10);
        health = 200;
    }

    @Override
    public double getDamage() {
        return 50;
    }

    @Override
    public double getSpeed() {
        return 0.5;
    }

    @Override
    public double getTimeIntervalBeforeNextAttack() {
        return 0.1;
    }
}
