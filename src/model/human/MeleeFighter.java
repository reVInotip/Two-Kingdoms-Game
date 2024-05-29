package model.human;

import model.AbstractUnit;

public abstract class MeleeFighter extends Human {
    @Override
    public void attackNearestEnemy(AbstractUnit nearestEnemy) {
        if (nearestEnemy != null) {
            nearestEnemy.changeHealth(this.getDamage());
        }
    }
}
