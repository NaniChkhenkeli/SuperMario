package main;

import Graphics.Texture;
import Graphics.Windows;
import main.mainutil.LevelHandler;
import object.KeyInput;
import util.Handler;
import util.Camera;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final int MILLIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;

    private static final String NAME = "MARIO";
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    private boolean running;
    private Thread thread;
    private Handler handler;
    private Camera cam;
    private static Texture tex;
    private LevelHandler levelHandler;

    private static final int SCREEN_OFFSET = 0; // Assuming SCREEN_OFFSET is defined here

    public Game() {
        initialize();
    }

    public static void main(String[] args) {
        new Game();
    }

    private void initialize() {
        tex = new Texture();
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler)); // Corrected 'keyInput' to 'KeyInput'
        levelHandler = new LevelHandler(handler);
        levelHandler.start();

        cam = new Camera(0, SCREEN_OFFSET);
        new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);
        start();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
        try {
            running = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double numOfTicks = NUM_TICKS;
        double ns = NANOS_PER_SEC / numOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            if (running) {
                render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > MILLIS_PER_SEC) {
                timer += MILLIS_PER_SEC;
                System.out.println("FPS: " + frames + " TPS: " + updates);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        System.out.println("Ticking..."); // Debug statement
        handler.tick();
    }

    private void render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        handler.render(g);

        g.dispose();
        buf.show();
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static Texture getTexture() {
        return tex;
    }
}
