package model;

import java.awt.geom.Point2D;
public abstract class Unit extends AbstractUnit {
    protected double timeBeforeNextAttack = 10;

    public abstract void setNextPoint(double x, double y);

    public abstract Point2D getNextPoint();

    public abstract void attackNearestEnemy(AbstractUnit nearestEnemy);

    public abstract double getTimeIntervalBeforeNextAttack();

    public boolean canAttack(double delay, AbstractUnit target) {
        if (isUnitInRange(target)) {
            if (delay <= timeBeforeNextAttack) {
                timeBeforeNextAttack = 0;
                return true;
            }

            timeBeforeNextAttack += getTimeIntervalBeforeNextAttack();
        } else {
            timeBeforeNextAttack = 10;
        }

        return false;
    }

    public boolean isCome() {
        return (getCurrPoint().getX() == getNextPoint().getX()) && (getCurrPoint().getY() == getNextPoint().getY());
    }
}
