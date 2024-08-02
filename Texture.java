package Graphics;

import java.awt.image.BufferedImage;

public class Texture {
    private final String PARENT_FOLDER = "/tile";
    private final int MARIO_L_COUNT = 21;
    private final int MARIO_S_COUNT = 14;

    private final int TILE_1_COUNT = 28;
    private final int TILE_2_COUNT = 33;

    private BufferedImageLoader loader;
    private BufferedImage playerSheet, enemySheet, npcSheet, blockSheet, tileSheet, gameOverSheet, introSheet;

    private BufferedImage[] marioL, marioS, tile1, tile2, tile3, tile4, pipe1, debris1;

    public Texture() {
        marioL = new BufferedImage[MARIO_L_COUNT];
        marioS = new BufferedImage[MARIO_S_COUNT];
        tile1 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile2 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile3 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile4 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        pipe1 = new BufferedImage[4];
        debris1 = new BufferedImage[4];

        loader = new BufferedImageLoader();

        try {
            playerSheet = loader.loadImage(PARENT_FOLDER + "/Mario&luigi.png");
            enemySheet = loader.loadImage(PARENT_FOLDER + "/enemies.png");
            npcSheet = loader.loadImage(PARENT_FOLDER + "/itemsObject.png");
            blockSheet = loader.loadImage(PARENT_FOLDER + "/NES - Super Mario Bros - Item and Brick Blocks.png");
            tileSheet = loader.loadImage(PARENT_FOLDER + "/tileset.png");
            gameOverSheet = loader.loadImage(PARENT_FOLDER + "/timeup&gameover.png");
            introSheet = loader.loadImage(PARENT_FOLDER + "/titleScreen.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        getPlayerTextures();
        getTileTextures();
        getPipeTextures();
        getDebrisTextures();
    }

    private void getPlayerTextures() {
        int xOff = 80;
        int yOff = 1;
        int width = 16;
        int height = 32;

        for (int i = 0; i < MARIO_L_COUNT; i++) {
            int x = xOff + i * width;
            int y = yOff;
            if (x + width <= playerSheet.getWidth() && y + height <= playerSheet.getHeight()) {
                marioL[i] = playerSheet.getSubimage(x, y, width, height);
            } else {
                System.out.println("Player texture coordinates out of bounds: x=" + x + ", y=" + y);
            }
        }

        yOff += height;
        height = 16;

        for (int i = 0; i < MARIO_S_COUNT; i++) {
            int x = xOff + i * width;
            int y = yOff;
            if (x + width <= playerSheet.getWidth() && y + height <= playerSheet.getHeight()) {
                marioS[i] = playerSheet.getSubimage(x, y, width, height);
            } else {
                System.out.println("Small player texture coordinates out of bounds: x=" + x + ", y=" + y);
            }
        }
    }

    private void getTileTextures() {
        int xOff = 0;
        int yOff = 0;
        int width = 16;
        int height = 16;

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < TILE_1_COUNT; i++) {
                int x = xOff + i * width;
                int y = yOff + j * height;
                if (x + width <= tileSheet.getWidth() && y + height <= tileSheet.getHeight()) {
                    if (j == 0) {
                        tile1[i] = tileSheet.getSubimage(x, y, width, height);
                    } else if (j == 1) {
                        tile2[i] = tileSheet.getSubimage(x, y, width, height);
                    } else if (j == 2) {
                        tile3[i] = tileSheet.getSubimage(x, y, width, height);
                    } else if (j == 3) {
                        tile4[i] = tileSheet.getSubimage(x, y, width, height);
                    }
                } else {
                    System.out.println("Tile texture coordinates out of bounds: x=" + x + ", y=" + y);
                }
            }
            yOff += height;

            for (int i = 0; i < TILE_2_COUNT; i++) {
                int x = xOff + i * width;
                int y = yOff + 3 * height;
                if (x + width <= tileSheet.getWidth() && y + height <= tileSheet.getHeight()) {
                    if (j == 0) {
                        tile1[i + TILE_1_COUNT] = tileSheet.getSubimage(x, y, width, height);
                    } else if (j == 1) {
                        tile2[i + TILE_1_COUNT] = tileSheet.getSubimage(x, y, width, height);
                    } else if (j == 2) {
                        tile3[i + TILE_1_COUNT] = tileSheet.getSubimage(x, y, width, height);
                    } else if (j == 3) {
                        tile4[i + TILE_1_COUNT] = tileSheet.getSubimage(x, y, width, height);
                    }
                } else {
                    System.out.println("Tile 2 texture coordinates out of bounds: x=" + x + ", y=" + y);
                }
            }
        }
    }

    private void getPipeTextures() {
        int xOff = 0;
        int yOff = 16 * 8;
        int width = 32;
        int height = 16;

        for (int i = 0; i < 4; i++) {
            int x = xOff + (i % 2) * width;
            int y = yOff + (i / 2) * height;
            if (x + width <= tileSheet.getWidth() && y + height <= tileSheet.getHeight()) {
                pipe1[i] = tileSheet.getSubimage(x, y, width, height);
            } else {
                System.out.println("Pipe texture coordinates out of bounds: x=" + x + ", y=" + y);
            }
        }
    }

    private void getDebrisTextures() {
        int xOff = 304;
        int yOff = 112;
        int width = 8;
        int height = 8;

        for (int i = 0; i < 4; i++) {
            int x = xOff + (i % 2) * width;
            int y = yOff + (i / 2) * height;
            if (x + width <= blockSheet.getWidth() && y + height <= blockSheet.getHeight()) {
                debris1[i] = blockSheet.getSubimage(x, y, width, height);
            } else {
                System.out.println("Debris texture coordinates out of bounds: x=" + x + ", y=" + y);
            }
        }
    }

    public BufferedImage[] getMario_L() {
        return marioL;
    }

    public BufferedImage[] getMarioS() {
        return marioS;
    }

    public BufferedImage[] getTile1() {
        return tile1;
    }

    public BufferedImage[] getTile2() {
        return tile2;
    }

    public BufferedImage[] getTile3() {
        return tile3;
    }

    public BufferedImage[] getTile4() {
        return tile4;
    }

    public BufferedImage[] getPipe1() {
        return pipe1;
    }

    public BufferedImage[] getDebris1() {
        return debris1;
    }
}
