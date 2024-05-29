package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImagesUploader {
    static private final Map<String, BufferedImage> IMAGES = new HashMap<>();

    public static Map<String, BufferedImage> uploadImages() {
        String pathToWorkingDirectory = System.getProperty("user.dir", ".");
        findAllImages(new File(pathToWorkingDirectory));

        return IMAGES;
    }

    public static Map<String, BufferedImage> getImages() {
        return IMAGES;
    }

    private static void scanResourcesFolder(File folder) {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File element: files) {
            if (element.isDirectory()) {
                scanResourcesFolder(element);
            } else {
                try {
                    String fileName = element.getName().split("\\.")[0].toLowerCase();
                    IMAGES.put(fileName, ImageIO.read(element));
                } catch (IOException exception) {
                    System.out.println(exception.getLocalizedMessage());
                }
            }
        }
    }

    private static void findAllImages(File cornelDir) {
        if (cornelDir.isDirectory()) {
            if (cornelDir.getName().equals("resources")) {
                scanResourcesFolder(cornelDir);
            } else {
                File[] files = cornelDir.listFiles();
                if (files == null || files.length == 0) {
                    return;
                }

                for (File element: files) {
                    if (element.isDirectory()) {
                        findAllImages(element);
                    }
                }
            }
        }
    }
}
