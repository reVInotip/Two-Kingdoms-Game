package model.projectile;

import model.AbstractUnit;
import model.Unit;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;

public abstract class Projectile extends Unit {
    protected final Point2D nextPoint = new Point2D.Double(0.0, 0.0);

    @Override
    public void setCurrPoint(double x, double y) {
        currPoint.setLocation(x, y);
    }

    @Override
    public Point2D getNextPoint() {
        return nextPoint;
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public void changeHealth(double diff) {
    }

    @Override
    public void kill() {
        isDie = true;
        currPoint = null;
    }

    @Override
    public void setNextPoint(double x, double y) {
        nextPoint.setLocation(x, y);
    }

    @Override
    public Ellipse2D getRange() {
        return null;
    }

    @Override
    public void setX(double x) {
        getCurrPoint().setLocation(x, getCurrPoint().getY());
    }

    @Override
    public void setY(double y) {
        getCurrPoint().setLocation(getCurrPoint().getX(), y);
    }

    @Override
    public boolean isUnitInRange(AbstractUnit abstractUnit) {
        return false;
    }

    @Override
    public void moveByX(List<AbstractUnit> allUnitsInField) {
        if (isDie) {
            return;
        }

        AbstractUnit target;
        if (isCome()) {
            return;
        } else if (getNextPoint().getX() < getCurrPoint().getX()) {
            setX(Math.max(getCurrPoint().getX() - getSpeed(), getNextPoint().getX()));
        } else {
            if (getCurrPoint().getX() - getSpeed() >= getNextPoint().getX()) {
                setX(getNextPoint().getX());
            } else {
                setX(getCurrPoint().getX() + getSpeed());
            }
        }

        target = isArrowGetInTarget(allUnitsInField);

        if (target != null) {
            kill();
            target.changeHealth(getDamage());
        }
    }

    @Override
    public void moveByY(List<AbstractUnit> allUnitsInField) {
        if (isDie) {
            return;
        }

        AbstractUnit target;
        if (isCome()) {
            return;
        } else if (getNextPoint().getY() < getCurrPoint().getY()) {
            setY(Math.max(getCurrPoint().getY() - getSpeed(), getNextPoint().getY()));
        } else{
            if (getCurrPoint().getY() - getSpeed() >= getNextPoint().getY()) {
                setY(getNextPoint().getY());
            } else {
                setY(getCurrPoint().getY() + getSpeed());
            }
        }

        target = isArrowGetInTarget(allUnitsInField);

        if (target != null) {
            kill();
            target.changeHealth(getDamage());
        }
    }

    @Override
    public Rectangle2D getRectangle() {
        return null;
    }

    protected AbstractUnit isArrowGetInTarget(List<AbstractUnit> allUnitsInField) {
        for (AbstractUnit target: allUnitsInField) {
            if (target.getRectangle() == null) {
                continue;
            }

            if (target.getRectangle().contains(getCurrPoint()) && !target.getOwner().equals(getOwner())) {
                return target;
            }
        }

        return null;
    }


    @Override
    public Map<String, Integer> getResourcesForCreate() {
        return null;
    }

    @Override
    public void attackNearestEnemy(AbstractUnit unit) {}

    @Override
    public double getTimeIntervalBeforeNextAttack() {
        return 0;
    }
}
