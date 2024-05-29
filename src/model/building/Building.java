package model.building;

import model.AbstractUnit;
import model.UnitView;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Building extends AbstractUnit {
    protected Rectangle2D rect = new Rectangle2D.Double(0, 0, this.getClass().getAnnotation(UnitView.class).width(),
            this.getClass().getAnnotation(UnitView.class).height());
    protected Map<String, Integer> productResources = new HashMap<>();

    @Override
    public void kill() {
        isDie = true;
        rect = null;
        currPoint = null;
        productResources = null;
        stopResourcesTimer();
    }


    @Override
    public Rectangle2D getRectangle() {
        return rect;
    }

    @Override
    public void moveByX(List<AbstractUnit> allUnitsInField) {
    }

    @Override
    public void moveByY(List<AbstractUnit> allUnitsInField) {
    }

    @Override
    public void setCurrPoint(double x, double y) {
        getCurrPoint().setLocation(x, y);
        getRectangle().setFrame(x, y, getRectangle().getWidth(), getRectangle().getHeight());
    }

    @Override
    public void setX(double x) {
        getCurrPoint().setLocation(x, getCurrPoint().getY());
        getRectangle().setFrame(x, getRectangle().getY(), getRectangle().getWidth(), getRectangle().getHeight());
    }

    @Override
    public void setY(double y) {
        getCurrPoint().setLocation(getCurrPoint().getX(), y);
        getRectangle().setFrame(getRectangle().getX(), y, getRectangle().getWidth(), getRectangle().getHeight());
    }

    @Override
    public boolean isUnitInRange(AbstractUnit abstractUnit) {
        return false;
    }

    @Override
    public Ellipse2D getRange() {
        return null;
    }

    @Override
    public boolean isCome() {
        return true;
    }

    public abstract Map<String, Integer> getResourcesForCreate();

    public abstract void initResourcesTimer();

    public abstract void stopResourcesTimer();
}
