package com.github.jsappz.lamegame.engine;

public interface Hud {
    GameItem[] getGameItems();
    void updateSize(Window window);

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
