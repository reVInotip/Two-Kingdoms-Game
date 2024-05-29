package view;

import controller.GameController;
import utils.ImagesUploader;
import utils.Observer;
import utils.events.Event;
import utils.events.GameOverEvent;

import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class GameFrame extends JFrame implements Observer {

    public final static String GAME_NAME = "Two Kingdoms";
    public final static int SCREEN_HEIGHT = 675;
    public final static int SCREEN_WIDTH = 1200;

    public GameFrame(GameController controller, Rectangle2D playerSpawnArea, List<Integer> playerResources) {
        super(GAME_NAME);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        BufferedImage image = ImagesUploader.uploadImages().get("map");
        if (image != null) {
            JLabel background = new JLabel(new ImageIcon(image));
            setContentPane(background);
        }

        setLayout(null);
        //pack();

        addMouseListener(controller.getMouseController());
        addKeyListener(controller.getKeyController());
        controller.add(this);

        GameMainPanel gameMainPanel = new GameMainPanel(controller, playerSpawnArea, playerResources);
        add(gameMainPanel);
        setVisible(true);
        setFocusable(true);
    }

    @Override
    public void update(Event event) {
        if (event instanceof GameOverEvent) {
            System.exit(0);
        }
    }
}
