package main.mainutil;

import Graphics.BufferedImageLoader;
import object.Block;
import object.Pipe;
import object.Player;
import util.Handler;

import java.awt.image.BufferedImage;

public class LevelHandler {
    private final String PARENT_FOLDER = "/level";

    private BufferedImageLoader loader;
    private BufferedImage levelTiles;
    private Handler handler;

    public LevelHandler(Handler handler) {
        this.handler = handler;
        loader = new BufferedImageLoader();
    }

    public void start() {
        setLevel(PARENT_FOLDER + "/1_1.png");
        loadCharacters(PARENT_FOLDER + "/1_1c.png");
    }

    public void setLevel(String levelTilesPath) {
        this.levelTiles = loader.loadImage(levelTilesPath);

        int width = levelTiles.getWidth();
        int height = levelTiles.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = levelTiles.getRGB(j, i); // Swapped i and j
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 255 && green == 255 && blue == 255) {
                    continue; // Skip white pixels
                }

                if (red == green && red == blue) {
                    handler.addObj(new Block(j * 16, i * 16, 16, 16, 0, 3));
                } else if (blue == 0 && green == 0 && red == 5) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 0, 3, false));
                } else if (blue == 0 && green == 0 && red == 10) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 1, 3, false));
                } else if (blue == 0 && green == 0 && red == 15) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 2, 3, false));
                } else if (blue == 0 && green == 0 && red == 20) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 3, 3, false));
                } else if (blue == 0 && green == 0 && red == 25) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 0, 3, true));
                } else if (blue == 0 && green == 0 && red == 30) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 2, 3, true));
                }
            }
        }
    }

    private void loadCharacters(String levelCharactersPath) {
        this.levelTiles = loader.loadImage(levelCharactersPath);

        int width = levelTiles.getWidth();
        int height = levelTiles.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = levelTiles.getRGB(j, i); // Swapped i and j
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 255 && green == 255 && blue == 255) {
                    continue; // Skip white pixels
                }

                if (red == green && red == blue) {
                    if (red == 0) {
                        handler.setPlayer(new Player(j * 16, i * 16, 3, handler)); // Swapped i and j
                    }
                }
            }
        }
    }
}
