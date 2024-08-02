package object;

import Graphics.Texture;
import main.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Debris {
    private Texture tex;
    private BufferedImage[] debris;
    private int scale;
    private float width, height, velX, velY;
    private float[] x, y;

    public Debris(float x, float y, float width, float height, float scale) {
        this.x = new float[4];
        this.y = new float[4];

        this.x[0] = (float) (x - (0.5 * width));
        this.x[1] = (float) (x - (0.5 * width));
        this.x[2] = (float) (x + (0.5 * width));
        this.x[3] = (float) (x + (0.5 * width));

        this.y[0] = (float) (y + (0.5 * height));
        this.y[1] = (float) (y - (0.5 * height));
        this.y[2] = (float) (y + (0.5 * height));
        this.y[3] = (float) (y - (0.5 * height));

        this.width = width / 2;
        this.height = height / 2;
        this.scale = (int) scale; // Ensure scale is properly set
        this.tex = Game.getTexture();
        this.debris = tex.getDebris1(); // Ensure getDebris1() is a method in Texture returning BufferedImage[]

        this.velX = 2f;
        this.velY = -7f;
    }

    public void applyGravity() {
        velY += 0.5f;
    }

    public void tick() {
        x[0] -= velX;
        x[1] -= velX;
        x[2] += velX;
        x[3] += velX;

        y[0] += velY;
        y[1] += (velY - 2);
        y[2] += velY;
        y[3] += (velY - 2);

        applyGravity();
    }

    public boolean shouldRemove() {
        return y[1] > Game.getWindowHeight();
    }

    public void draw(Graphics g) {
        for (int i = 0; i < 4; i++) {
            g.drawImage(debris[i], (int) x[i], (int) y[i], (int) width, (int) height, null);
        }
    }
}
