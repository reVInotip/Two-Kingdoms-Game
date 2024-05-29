package model;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class UnitsFactory {
    static private final Map<String, Class<AbstractUnit>> HUMANS = new HashMap<>();
    static private final Map<String, Class<AbstractUnit>> BUILDINGS = new HashMap<>();
    static private final Map<String, Class<AbstractUnit>> PROJECTILES = new HashMap<>();
    static private Class<AbstractUnit> CASTLE;
    static private final String RESOURCE_FILE_NAME = "./game_conf.conf";

    static public void initFactory() {
        try(InputStream stream = UnitsFactory.class.getResourceAsStream(RESOURCE_FILE_NAME)) {
            if (stream == null) {
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            Class<AbstractUnit> unit;
            for(String classPath; (classPath = reader.readLine()) != null; ) {
                String[] data = classPath.split("=");
                //noinspection unchecked
                unit = (Class<AbstractUnit>) Class.forName(data[1]);
                switch (unit.getAnnotation(UnitView.class).type()) {
                    case "castle" -> CASTLE = unit;
                    case "human" -> HUMANS.put(data[0], unit);
                    case "building" -> BUILDINGS.put(data[0], unit);
                    case "projectile" -> PROJECTILES.put(data[0], unit);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static public AbstractUnit of(String className) {
        if (HUMANS.containsKey(className)) {
            return createHuman(className);
        } else if (BUILDINGS.containsKey(className)) {
            return createBuilding(className);
        } else if (PROJECTILES.containsKey(className)) {
            return createProjectile(className);
        }
        return null;
    }

    static public AbstractUnit createCastle() {
        try {
            return CASTLE.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("[ERR] Class not found");
            throw new RuntimeException(e);
        }
    }

    static public AbstractUnit createHuman(String className) {
        try {
            return HUMANS.get(className).getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("[ERR] Class not found");
            throw new RuntimeException(e);
        }
    }

    static public AbstractUnit createBuilding(String className) {
        try {
            return BUILDINGS.get(className).getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("[ERR] Class not found");
            throw new RuntimeException(e);
        }
    }

    static public AbstractUnit createProjectile(String className) {
        try {
            return PROJECTILES.get(className).getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("[ERR] Class not found");
            throw new RuntimeException(e);
        }
    }

    static public List<String> getAvailableHumans() {
        return new ArrayList<>(HUMANS.keySet());
    }

    static public int getCountHumans() {
        return HUMANS.size();
    }

    static public Set<String> getAvailableBuildings() {
        return BUILDINGS.keySet();
    }
}
