package object;

import main.Game;
import util.ObjectId;
import Graphics.Texture;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {
    private Texture tex;
    private BufferedImage[] sprite;
    private int index;

    public Block(float x, float y, float width, float height, int scale, int index) {
        super(x, y, ObjectId.Block, width, height, scale);
        this.index = index;
        this.tex = Game.getTexture();
        sprite = tex.getTile1(); // Ensure getTile1() is a method in Texture returning BufferedImage[]
    }

    @Override
    public void tick() {
        // Update the state of the block if needed
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    // Method to handle interaction when hit
    public void hit() {
        // Define what happens when the block is hit
        System.out.println("Block hit!");
    }

    public boolean shouldRemove() {
        // Logic to determine if the block should be removed
        return false; // or true based on your criteria
    }
}
