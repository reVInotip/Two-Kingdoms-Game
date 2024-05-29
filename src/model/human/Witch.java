package model.human;

import model.UnitView;

@UnitView(color = {255, 1, 1, 255}, type = "human", name = "witch")
public class Witch extends RangedFighter {
    public Witch() {
        resources.put("food", 5);
        resources.put("gold", 3);
        health = 80;
        range.setFrame(range.getX(), range.getY(), range.getWidth() + 100, range.getHeight() + 100);
    }

    @Override
    public double getDamage() {
        return 500;
    }

    @Override
    public double getSpeed() {
        return 0.2;
    }

    @Override
    protected String getProjectileName() {
        return "fireball";
    }

    @Override
    public double getTimeIntervalBeforeNextAttack() {
        return 0.1;
    }
}
