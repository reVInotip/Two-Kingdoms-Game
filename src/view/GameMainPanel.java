package view;

import controller.GameController;
import model.*;
import model.building.Farm;
import model.building.Mine;
import utils.ImagesUploader;
import utils.Observer;
import utils.events.*;
import utils.events.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameMainPanel extends JPanel implements Observer {
    private final Rectangle2D playerSpawnArea;
    private boolean showRange = false;
    private boolean showPaths = false;

    private static class ChooseSoldierMenu extends JPopupMenu {
        ChooseSoldierMenu(GameController controller) {
            super("Soldiers:");

            JMenuItem item;
            for (String unitName: UnitsFactory.getAvailableHumans()) {
                item = new JMenuItem(unitName);
                item.addActionListener(controller.getSoldierMenuController());
                add(item);
            }

            setVisible(false);
            setEnabled(false);
        }
    }

    private static class ChooseBuildingMenu extends JPopupMenu {
        ChooseBuildingMenu(GameController controller) {
            super("Buildings:");

            JMenuItem item;
            for (String unitName: UnitsFactory.getAvailableBuildings()) {
                item = new JMenuItem(unitName);
                item.addActionListener(controller.getBuildingMenuController());
                add(item);
            }

            setVisible(false);
            setEnabled(false);
        }
    }

    private List<AbstractUnit> unitsForDraw = new ArrayList<>();
    private final ChooseSoldierMenu chooseSoldierMenu;
    private final ChooseBuildingMenu chooseBuildingMenu;
    private final JButton chooseSoldierButton;
    private final JButton chooseBuildingButton;
    private final JLabel playerResourcesPanel;

    GameMainPanel(GameController controller, Rectangle2D playerSpawnArea, List<Integer> playerResources) {
        super();

        chooseSoldierMenu = new ChooseSoldierMenu(controller);

        this.playerSpawnArea = playerSpawnArea;

        chooseBuildingMenu = new ChooseBuildingMenu(controller);

        playerResourcesPanel = new JLabel(String.format("Gold: %d Iron: %d Food: %d",
                playerResources.getFirst(), playerResources.get(1), playerResources.get(2)));
        playerResourcesPanel.setBounds(10, 10, 300, 100);
        playerResourcesPanel.setVisible(true);
        add(playerResourcesPanel);

        controller.add(this);
        setSize(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);

        chooseSoldierButton = new JButton("Choose soldier");
        chooseSoldierButton.setBounds(10, GameFrame.SCREEN_HEIGHT - 100, 150,  50);
        chooseSoldierButton.addActionListener(controller.getSoldierButtonController());
        chooseSoldierButton.setComponentPopupMenu(chooseSoldierMenu);

        chooseBuildingButton = new JButton("Choose building");
        chooseBuildingButton.setBounds(GameFrame.SCREEN_WIDTH - 160, GameFrame.SCREEN_HEIGHT - 100, 150,  50);
        chooseBuildingButton.addActionListener(controller.getBuildingButtonController());
        chooseBuildingButton.setComponentPopupMenu(chooseBuildingMenu);

        addKeyListener(controller.getKeyController());

        setLayout(null);
        add(chooseSoldierButton);
        add(chooseSoldierMenu);
        add(chooseBuildingButton);
        add(chooseBuildingMenu);

        setOpaque(false);
    }

    private void draw(Graphics2D g) {
        g.draw(playerSpawnArea);
        drawUnit(g);
    }

    private void updateTextPanel(List<Integer> playerResources) {
        playerResourcesPanel.setText(String.format("Gold: %d Iron: %d Food: %d",
                playerResources.getFirst(), playerResources.get(1), playerResources.get(2)));
    }

    private void drawUnit(Graphics2D g) {
        for (AbstractUnit abstractUnit : unitsForDraw) {
            UnitView unitMetaData = abstractUnit.getClass().getAnnotation(UnitView.class);
            if (unitMetaData == null || abstractUnit.isDie()) {
                continue;
            }

            switch (unitMetaData.type()) {
                case "projectile" -> {
                    Ellipse2D ellipse2D = new Ellipse2D.Double(abstractUnit.getCurrPoint().getX(),
                            abstractUnit.getCurrPoint().getY(), unitMetaData.width(), unitMetaData.height());
                    g.setColor(new Color(unitMetaData.color()[0], unitMetaData.color()[1], unitMetaData.color()[2], unitMetaData.color()[3]));
                    g.fill(ellipse2D);
                    g.draw(ellipse2D);
                }
                case "human" -> {
                    Ellipse2D ellipse2D = new Ellipse2D.Double(abstractUnit.getCurrPoint().getX(),
                            abstractUnit.getCurrPoint().getY(), unitMetaData.width(), unitMetaData.height());
                    g.setColor(new Color(unitMetaData.color()[0], unitMetaData.color()[1], unitMetaData.color()[2], unitMetaData.color()[3]));
                    g.fill(ellipse2D);
                    g.draw(ellipse2D);

                    if (showRange) {
                        g.draw(abstractUnit.getRange());
                    }

                    if  (showPaths) {
                        g.drawLine((int)abstractUnit.getCurrPoint().getX(), (int)abstractUnit.getCurrPoint().getY(),
                                (int)((Unit) abstractUnit).getNextPoint().getX(), (int)((Unit) abstractUnit).getNextPoint().getY());
                    }
                }
                case "building" -> {
                    BufferedImage image;
                    g.drawString(String.format("%.2f", abstractUnit.getHealth()), (float) abstractUnit.getCurrPoint().getX() + 10,
                            (float) abstractUnit.getCurrPoint().getY() - 5);

                    if (abstractUnit instanceof Farm) {
                        image = ImagesUploader.getImages().get("farm");
                    } else if (abstractUnit instanceof Mine) {
                        image = ImagesUploader.getImages().get("mine");
                    } else {
                        Rectangle2D rectangle2D = new Rectangle2D.Double(abstractUnit.getCurrPoint().getX(),
                                abstractUnit.getCurrPoint().getY(), unitMetaData.width(), unitMetaData.height());
                        g.setColor(new Color(unitMetaData.color()[0], unitMetaData.color()[1], unitMetaData.color()[2], unitMetaData.color()[3]));
                        g.fill(rectangle2D);
                        g.draw(rectangle2D);
                        return;
                    }

                    g.drawImage(image, (int) abstractUnit.getCurrPoint().getX(),
                            (int) abstractUnit.getCurrPoint().getY(), unitMetaData.width(),
                            unitMetaData.height(), null);
                }
                case "castle" -> {
                    BufferedImage image;
                    g.drawString(String.format("%.2f", abstractUnit.getHealth()), (float) abstractUnit.getCurrPoint().getX() + 10,
                            (float) abstractUnit.getCurrPoint().getY() - 80);

                    if (abstractUnit.getOwner().equals(GameModel.FIRST_OWNER)) {
                        image = ImagesUploader.getImages().get("castle1");
                    } else if (abstractUnit.getOwner().equals(GameModel.SECOND_OWNER)) {
                        image = ImagesUploader.getImages().get("castle2");
                    } else {
                        Rectangle2D rectangle2D = new Rectangle2D.Double(abstractUnit.getCurrPoint().getX(),
                                abstractUnit.getCurrPoint().getY(), unitMetaData.width(), unitMetaData.height());
                        g.setColor(new Color(unitMetaData.color()[0], unitMetaData.color()[1], unitMetaData.color()[2], unitMetaData.color()[3]));
                        g.fill(rectangle2D);
                        g.draw(rectangle2D);
                        return;
                    }

                    g.drawImage(image, (int) abstractUnit.getCurrPoint().getX() - 20,
                            (int) abstractUnit.getCurrPoint().getY() - 90, unitMetaData.width() + 40,
                            unitMetaData.height() + 100, null);
                }
            }
        }

        chooseSoldierButton.setFocusable(false);
        chooseBuildingButton.setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }

    @Override
    public void update(Event event) {
        if (event instanceof ShowSoldierPanelEvent) {
            chooseSoldierMenu.show(this, 10 + chooseSoldierButton.getWidth(), GameFrame.SCREEN_HEIGHT - 100);
            chooseSoldierButton.setFocusable(false);
            chooseSoldierMenu.setFocusable(false);
        } else if (event instanceof UpdateFieldEvent) {
            unitsForDraw = ((UpdateFieldEvent) event).allUnisInField();
            updateTextPanel(((UpdateFieldEvent) event).playerResources());
            repaint();
        } else if (event instanceof UpdateResourcesEvent) {
            updateTextPanel(((UpdateResourcesEvent) event).playerResources());
        } else if (event instanceof ShowBuildingPanelEvent) {
            chooseBuildingMenu.show(this, chooseBuildingButton.getX() - 55, GameFrame.SCREEN_HEIGHT - 100);
            chooseBuildingButton.setFocusable(false);
            chooseBuildingMenu.setFocusable(false);
        } else if (event instanceof ShowRangeEvent) {
            showRange = true;
        } else if (event instanceof HideRangeEvent) {
            showRange = false;
        } else if (event instanceof ShowPathsEvent) {
            showPaths = true;
        } else if (event instanceof HidePathsEvent) {
            showPaths = false;
        }
    }
}
