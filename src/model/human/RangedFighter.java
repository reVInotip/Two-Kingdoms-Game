package model.human;

import model.AbstractUnit;

public abstract class RangedFighter extends Human {
    @Override
    public void attackNearestEnemy(AbstractUnit nearestEnemy) {
        if (nearestEnemy != null) {
            getGameModel().createProjectile(getProjectileName(), getCurrPoint(), nearestEnemy.getCurrPoint(), getOwner());
        }
    }

    protected abstract String getProjectileName();
}
