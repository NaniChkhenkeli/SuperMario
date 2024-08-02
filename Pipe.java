package object;

import main.Game;
import util.ObjectId;
import Graphics.Texture; // Ensure this import is correct based on your project structure

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pipe extends GameObject {
    private Texture tex;
    private int index;
    private BufferedImage[] sprite;
    private boolean entrable;

    public Pipe(int x, int y, int width, int height, int scale, int index, boolean entrable) {
        super(x, y, ObjectId.Pipe, width, height, scale);
        this.entrable = entrable;
        this.index = index;
        tex = Game.getTexture();
        sprite = tex.getPipe1(); // Ensure this method returns a BufferedImage[]
    }

    @Override
    public void tick() {
        // Update the state of the pipe if needed
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
