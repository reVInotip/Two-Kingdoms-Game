package model.human;

import model.UnitView;

@UnitView(color = {255, 255, 255, 255}, type = "human", name = "knight")
public class Knight extends MeleeFighter {
    public Knight() {
        resources.put("food", 5);
        resources.put("gold", 1);
        resources.put("iron", 7);
        health = 500;
    }

    @Override
    public double getDamage() {
        return 150;
    }

    @Override
    public double getSpeed() {
        return 0.2;
    }

    @Override
    public double getTimeIntervalBeforeNextAttack() {
        return 0.1;
    }
}
