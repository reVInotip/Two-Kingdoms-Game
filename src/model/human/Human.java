package model.human;

import model.AbstractUnit;
import model.Unit;
import model.UnitView;
import model.projectile.Projectile;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Human extends Unit {
    protected Rectangle2D rect = new Rectangle2D.Double(0, 0, this.getClass().getAnnotation(UnitView.class).width(),
            this.getClass().getAnnotation(UnitView.class).height());
    protected Ellipse2D range = new Ellipse2D.Double(0, 0,
            this.getClass().getAnnotation(UnitView.class).width() + 10,
            this.getClass().getAnnotation(UnitView.class).height() + 10);
    protected Point2D nextPoint = new Point2D.Double(0.0, 0.0);
    protected Map<String, Integer> resources =  new HashMap<>();

    @Override
    public void kill() {
        isDie = true;
        rect = null;
        currPoint = null;
        resources = null;
        nextPoint = null;
        range = null;
    }

    @Override
    public Map<String, Integer> getResourcesForCreate() {
        return resources;
    }

    @Override
    public Ellipse2D getRange() {
        return range;
    }

    @Override
    public Rectangle2D getRectangle() {
        return rect;
    }

    @Override
    public void setNextPoint(double x, double y) {
        nextPoint.setLocation(x, y);
    }

    @Override
    public Point2D getNextPoint() {
        return nextPoint;
    }

    @Override
    public void moveByX(List<AbstractUnit> allUnitsInField) {
        if (getCurrPoint().getX() == getNextPoint().getX()) {
            return;
        }

        if (getNextPoint().getX() < getCurrPoint().getX()) {
            if (getCurrPoint().getX() - getSpeed() <= getNextPoint().getX()) {
                setX(getNextPoint().getX());
            } else {
                setX(getCurrPoint().getX() - getSpeed());
                if (isIncorrectCollisions(allUnitsInField)) {
                    setX(getCurrPoint().getX() + getSpeed());
                    getNextPoint().setLocation(getCurrPoint().getX(), getNextPoint().getY());
                }
            }
        } else {
            if (getCurrPoint().getX() + getSpeed() >= getNextPoint().getX()) {
                setX(getNextPoint().getX());
            } else {
                setX(getCurrPoint().getX() + getSpeed());
                if (isIncorrectCollisions(allUnitsInField)) {
                    setX(getCurrPoint().getX() - getSpeed());
                    getNextPoint().setLocation(getCurrPoint().getX(), getNextPoint().getY());
                }
            }
        }
    }

    @Override
    public void moveByY(List<AbstractUnit> allUnitsInField) {
        if (getCurrPoint().getY() == getNextPoint().getY()) {
            return;
        }

        if (getNextPoint().getY() < getCurrPoint().getY()) {
            if (getCurrPoint().getY() - getSpeed() <= getNextPoint().getY()) {
                setY(getNextPoint().getY());
            } else {
                setY(getCurrPoint().getY() - getSpeed());
                if (isIncorrectCollisions(allUnitsInField)) {
                    setY(getCurrPoint().getY() + getSpeed());
                    getNextPoint().setLocation(getNextPoint().getX(), getCurrPoint().getY());
                }
            }
        } else {
            if (getCurrPoint().getY() + getSpeed() >= getNextPoint().getY()) {
                setY(getNextPoint().getY());
            } else {
                setY(getCurrPoint().getY() + getSpeed());
                if (isIncorrectCollisions(allUnitsInField)) {
                    setY(getCurrPoint().getY() - getSpeed());
                    getNextPoint().setLocation(getNextPoint().getX(), getCurrPoint().getY());
                }
            }
        }
    }

    private boolean isIncorrectCollisions(List<AbstractUnit> allUnitsInField) {
        for (AbstractUnit abstractUnit : allUnitsInField) {
            if (getRectangle() == null || abstractUnit.getRectangle() == null) {
                continue;
            }

            if (getRectangle().intersects(abstractUnit.getRectangle()) && abstractUnit != this) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setCurrPoint(double x, double y) {
        getCurrPoint().setLocation(x, y);
        getRectangle().setFrame(x, y, getRectangle().getWidth(), getRectangle().getHeight());
        getRange().setFrame(x - getRange().getWidth() / 2.0 + 5, y - getRange().getHeight() / 2.0 + 5, getRange().getWidth(), getRange().getHeight());
    }

    @Override
    public void setX(double x) {
        getCurrPoint().setLocation(x, getCurrPoint().getY());
        getRectangle().setFrame(x, getRectangle().getY(), getRectangle().getWidth(), getRectangle().getHeight());
        getRange().setFrame(x - getRange().getWidth() / 2.0 + 5, getRange().getY(), getRange().getWidth(), getRange().getHeight());
    }

    @Override
    public void setY(double y) {
        getCurrPoint().setLocation(getCurrPoint().getX(), y);
        getRectangle().setFrame(getRectangle().getX(), y, getRectangle().getWidth(), getRectangle().getHeight());
        getRange().setFrame(getRange().getX(), y - getRange().getHeight() / 2.0 + 5, getRange().getWidth(), getRange().getHeight());
    }

    @Override
    public boolean isUnitInRange(AbstractUnit abstractUnit) {
        if (abstractUnit == null || abstractUnit instanceof Projectile || abstractUnit.isDie()) {
            return false;
        }

        return getRange().intersects(abstractUnit.getRectangle());
    }
}
