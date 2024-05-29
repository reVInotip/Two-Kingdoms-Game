package model;

import model.building.Building;
import model.human.Human;
import model.player.AI;
import model.player.Player;
import model.projectile.Projectile;
import utils.Observable;
import utils.events.*;
import view.GameFrame;

import javax.swing.Timer;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class GameModel extends Observable {
    private final List<AbstractUnit> allUnitsInField = new ArrayList<>();
    private final List<Unit> allHumansInField = new ArrayList<>();
    private final List<Unit> allProjectilesInField = new ArrayList<>();
    private final Map<String, Player> players = new HashMap<>();

    public static final String FIRST_OWNER = "Player";
    public static final String SECOND_OWNER = "AI";

    public GameModel() {
        int spawnAreaWidth = 350;
        int spawnAreaHeight = 150;
        AI ai = new AI(spawnAreaWidth, spawnAreaHeight, this);
        players.put(FIRST_OWNER, new Player(spawnAreaWidth, spawnAreaHeight, FIRST_OWNER, ai));
        players.put(SECOND_OWNER, ai.getAiPlayer());

        Timer updateTimer = new Timer(10, _ -> updateField());
        updateTimer.start();
    }

    private boolean isUnitInSpawnArea(double x, double y, String owner) {
        return players.get(owner).getSpawnArea().contains(x, y);
    }

    private void payForUnit(Player player, AbstractUnit unit) {
        Map<String, Integer> resourcesForCreate = unit.getResourcesForCreate();

        for (String resource: resourcesForCreate.keySet()) {
            player.updateResources(resource, resourcesForCreate.get(resource) * (-1));
        }
    }

    public Rectangle2D getPlayerSpawnArea() {
        return players.get(FIRST_OWNER).getSpawnArea();
    }

    public void setGroupMovementPoints(String unitName, String owner, int x, int y) {
        for (Unit unit : allHumansInField) {
            if (unit.getClass().getAnnotation(UnitView.class).name().equals(unitName) && unit.getOwner().equals(owner)) {
                unit.setNextPoint(x, y);
            }
        }
    }

    public void createHuman(String type, int x, int y, String owner) {
        int capForSoldiersOnPlayer = 20;
        if (players.get(owner).currentCountSoldiers == capForSoldiersOnPlayer ||
            !isUnitInSpawnArea(x, y, owner))
        {
            return;
        }

        Unit unit = (Unit) UnitsFactory.of(type);

        if (unit == null) {
            return;
        } else if (players.get(owner).canNotCreateUnit(unit)) {
            return;
        }

        unit.setCurrPoint(x, y);
        unit.setNextPoint(x, y);
        unit.setOwner(owner);

        payForUnit(players.get(owner), unit);

        players.get(owner).addSoldier();

        unit.setGameModel(this);

        allUnitsInField.add(unit);
        allHumansInField.add(unit);
    }

    public void createProjectile(String name, Point2D startPoint, Point2D endPoint, String owner) {
        Unit unit = (Unit) UnitsFactory.of(name);

        if (unit == null) {
            return;
        }

        unit.setCurrPoint(startPoint.getX(), startPoint.getY());
        unit.setNextPoint(endPoint.getX(), endPoint.getY());
        unit.setOwner(owner);

        unit.setGameModel(this);

        allUnitsInField.add(unit);
        allProjectilesInField.add(unit);
    }

    public boolean createBuilding(String type, String owner) {
        int capForBuildingsOnPlayer = 2;
        if (players.get(owner).currentCountBuildings == capForBuildingsOnPlayer) {
            return false;
        }

        Building unit = (Building) UnitsFactory.of(type);

        if (unit == null) {
            return false;
        } else if (players.get(owner).canNotCreateUnit(unit)) {
            return false;
        }

        payForUnit(players.get(owner), unit);

        double[][] coordinates = players.get(owner).getBuildingsCoordinates();
        if (players.get(owner).currentCountBuildings == 0) {
            unit.setCurrPoint(coordinates[0][0], coordinates[0][1]);
        } else {
            unit.setCurrPoint(coordinates[1][0], coordinates[1][1]);
        }

        players.get(owner).addBuilding();

        unit.setOwner(owner);
        unit.initResourcesTimer();
        unit.setGameModel(this);

        allUnitsInField.add(unit);

        return true;
    }

    public void createCastles() {
        Building castle1 = (Building) UnitsFactory.createCastle();
        Building castle2 = (Building) UnitsFactory.createCastle();
        castle1.setCurrPoint(50, GameFrame.SCREEN_HEIGHT - 300);
        castle1.setOwner(FIRST_OWNER);
        castle2.setCurrPoint(GameFrame.SCREEN_WIDTH - castle2.getClass().getAnnotation(UnitView.class).width() - 50,
                GameFrame.SCREEN_HEIGHT - 300);
        castle2.setOwner(SECOND_OWNER);
        castle1.setGameModel(this);
        castle2.setGameModel(this);

        castle1.initResourcesTimer();
        castle2.initResourcesTimer();

        allUnitsInField.add(castle1);
        allUnitsInField.add(castle2);
    }

    public void updateField() {
        for (AbstractUnit unit: new ArrayList<>(allUnitsInField)) {
            if (unit.isDie()) {
                eraseDyingUnit(unit);
                continue;
            }

            if (unit.getSpeed() != 0) {
                moveUnit((Unit) unit);
            }
            if (!(unit instanceof Building)) {
                attackEnemy((Unit) unit);
            }
        }

        players.get(FIRST_OWNER).updateInfo();
        update(new UpdateFieldEvent(allUnitsInField, getPlayerResources(FIRST_OWNER)));
    }

    public double getDistance(AbstractUnit unit1, AbstractUnit unit2) {
        return Math.sqrt(Math.pow(unit1.getCurrPoint().getX() - unit2.getCurrPoint().getX(), 2) +
                Math.pow(unit1.getCurrPoint().getY() - unit2.getCurrPoint().getY(), 2));
    }

    private void attackEnemy(Unit unit) {
        AbstractUnit nearestEnemy = null;
        double minDistance = Double.MAX_VALUE;
        for (AbstractUnit target: allUnitsInField) {
            if ((target instanceof Building || target instanceof Human)
                    && unit.isUnitInRange(target) && !target.getOwner().equals(unit.getOwner())
                    && (getDistance(target, unit) < minDistance)) {
                nearestEnemy = target;
                minDistance = getDistance(target, unit);
            }
        }

        if (unit.canAttack(10, nearestEnemy)) {
            unit.attackNearestEnemy(nearestEnemy);
        }
    }

    private void moveUnit(Unit unit) {
        if (unit.isCome()) {
            if (unit instanceof Projectile) {
                unit.kill();
            }
            return;
        }

        unit.moveByX(allUnitsInField);
        unit.moveByY(allUnitsInField);
    }

    private void eraseDyingUnit(AbstractUnit unit) {
        allUnitsInField.remove(unit);
        if (unit instanceof Human) {
            players.get(unit.getOwner()).eraseSoldier();
            allHumansInField.remove(unit);
        } else if (unit instanceof Building) {
            players.get(unit.getOwner()).eraseBuilding();
        } else if (unit instanceof  Projectile) {
            allProjectilesInField.remove(unit);
        }

        if (unit.getClass().getAnnotation(UnitView.class).type().equals("castle")) {
            if (unit.getOwner().equals(FIRST_OWNER)) {
                System.out.println("YOU LOSE!");
                update(new GameOverEvent());
            } else {
                System.out.println("YOU WIN!");
                update(new GameOverEvent());
            }
        }
    }

    public List<Integer> getPlayerResources(String name) {
        return new ArrayList<>(players.get(name).getResources().values());
    }

    public void updateResourcesForPlayer(String name, Map<String, Integer> diff) {
        for (String resource: diff.keySet()) {
            players.get(name).updateResources(resource, diff.get(resource));
        }

        if (name.equals(FIRST_OWNER)) {
            update(new UpdateResourcesEvent(getPlayerResources(name)));
        }
    }
}
