package model.human;

import model.UnitView;

@UnitView(color = {255, 255, 1, 255}, type = "human", name = "archer")
public class Archer extends RangedFighter {
    public Archer() {
        resources.put("food", 8);
        resources.put("gold", 2);
        health = 100;
        range.setFrame(range.getX(), range.getY(), range.getWidth() + 60, range.getHeight() + 60);
    }

    @Override
    public double getSpeed() {
        return 0.3;
    }

    @Override
    public double getDamage() {
        return 20;
    }

    @Override
    protected String getProjectileName() {
        return "arrow";
    }

    @Override
    public double getTimeIntervalBeforeNextAttack() {
        return 0.1;
    }
}
