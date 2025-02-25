package util;

import main.Game;
import object.GameObject;
import object.Player;
import object.Block;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Handler {
    private List<GameObject> gameObjs;
    private Player player;

    public Handler() {
        gameObjs = new LinkedList<GameObject>();
    }

    public void tick() {
        for(GameObject obj : gameObjs) {
            obj.tick();
        }
        LinkedList<Block> removeBlocks = player.getAndResetRemoveBlock();
        for (Block removeBlock : removeBlocks) {
            removeObj(removeBlock);
        }
    }

    public void render(Graphics g) {
        for(GameObject obj : gameObjs) {
            obj.render(g);
        }
    }

    public void addObj(GameObject obj) {
        gameObjs.add(obj);
    }

    public void removeObj(GameObject obj) {
        gameObjs.remove(obj);
    }

    public List<GameObject> getGameObjs() {
        return gameObjs;
    }

    public int setPlayer(Player player) {
        if(this.player != null) {
            return -1;
        }

        addObj(player);
        this.player = player;
        return 0;
    }

    public int removePlayer() {
        if(player == null) {
            return -1;
        }

        removeObj(player);
        this.player = null;
        return 0;
    }

    public Player getPlayer() {
        return player;
    }
}
