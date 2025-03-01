package object;

import main.Game;
import util.Handler;
import util.ObjectId;
import Graphics.Texture;
import Graphics.Animation; // Ensure this import is correct

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject {
    private static final float WIDTH = 16;
    private static final float HEIGHT = 16;

    private Handler handler;
    private Texture tex;
    private PlayerState state;
    private BufferedImage[] spritel, spriteS;
    private Animation playerWalkL, playerWalkS;
    private BufferedImage[] currSprite;
    private Animation currAnimation;
    private LinkedList<Block> removeBlocks;
    private boolean jumped = false;
    private int health = 2;
    private boolean forward = false;

    public Player(float x, float y, int scale, Handler handler) {
        super(x, y, ObjectId.Player, WIDTH, HEIGHT, scale);
        this.handler = handler;
        tex = Game.getTexture();
        removeBlocks = new LinkedList<>();
        spritel = tex.getMario_L();
        spriteS = tex.getMarioS();

        playerWalkL = new Animation(5, spritel[1], spritel[2], spritel[3]);
        playerWalkS = new Animation(5, spriteS[1], spriteS[2], spriteS[3]);

        state = PlayerState.Small; // Ensure PlayerState has a Small enum
        currSprite = spriteS;
        currAnimation = playerWalkS;
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());
        setY(getVelY() + getY());
        applyGravity();

        collision();

        currAnimation.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        if (jumped) {
            if (forward) {
                g.drawImage(currSprite[5], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
            } else {
                g.drawImage(currSprite[5], (int) (getX() + getWidth()), (int) getY(), (int) getWidth(), (int) getHeight(), null);
            }
        } else if (getVelX() > 0) {
            currAnimation.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
            forward = true;
        } else if (getVelX() < 0) {
            currAnimation.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), (int) getWidth(), (int) getHeight());
            forward = false;
        } else {
            g.drawImage(currSprite[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        }
    }

    private void collision() {
        for (int i = 0; i < handler.getGameObjs().size(); i++) {
            GameObject temp = handler.getGameObjs().get(i);
            if (temp == this) continue;
            if (removeBlocks.contains(temp)) continue;

            if (temp.getId() == ObjectId.Block && getBoundsTop().intersects(temp.getBounds())) {
                setY(temp.getY() + temp.getHeight());
                setVelY(0);
                ((Block) temp).hit(); // Ensure Block has a hit() method
                removeBlocks.add((Block) temp);
            } else {
                if (getBounds().intersects(temp.getBounds())) {
                    setY(temp.getY() - getHeight());
                    setVelY(0);
                    jumped = false;
                }
                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelY(0);
                }
                if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth());
                }
                if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + temp.getWidth());
                }
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) (getY() + getHeight() / 2),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) getY(),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 5),
                (int) getY() + 5,
                5,
                (int) getHeight() - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(),
                (int) (getY() + 5),
                5,
                (int) (getHeight() - 10));
    }

    private void showBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
    }

    public boolean hasJumped() {
        return jumped;
    }

    public void setJumped(boolean hasJumped) {
        jumped = hasJumped;
    }

    public LinkedList<Block> getAndResetRemoveBlock() {
        LinkedList<Block> output = new LinkedList<>();
        for (Block removeBlock : removeBlocks) {
            if (!removeBlock.shouldRemove()) continue; // Ensure Block has a shouldRemove() method
            output.add(removeBlock);
        }
        for (Block removeBlock : output) {
            removeBlocks.remove(removeBlock);
        }
        return output;
    }
}
