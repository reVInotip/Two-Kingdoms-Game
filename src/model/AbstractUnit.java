package model;

import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;

public abstract class AbstractUnit extends JComponent {
    protected GameModel model = null;
    protected double health;
    protected boolean isDie = false;
    protected Point2D currPoint = new Point2D.Double(0.0, 0.0);
    protected String owner = null;

    public void setGameModel(GameModel model) {
        this.model = model;
    }

    public double getSpeed() {
        return 0;
    }

    public boolean isDie() {
        return isDie;
    }

    public GameModel getGameModel() {
        return model;
    }

    public void changeHealth(double diff) {
        health -= diff;
        if (health < 0) {
            kill();
        }
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getHealth() {
        return health;
    }

    public Point2D getCurrPoint() {
        return currPoint;
    }

    public abstract void kill();

    public abstract void moveByX(List<AbstractUnit> allUnitsInField);

    public abstract void moveByY(List<AbstractUnit> allUnitsInField);

    public abstract double getDamage();

    public abstract Rectangle2D getRectangle();

    public abstract Ellipse2D getRange();

    public abstract void setCurrPoint(double x, double y);

    public abstract void setX(double x);

    public abstract void setY(double y);

    public abstract boolean isUnitInRange(AbstractUnit abstractUnit);

    public abstract Map<String, Integer> getResourcesForCreate();

    public abstract boolean isCome();
    
    //public abstract void drawUnit();
}
