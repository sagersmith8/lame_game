package com.github.jsappz.lamegame.engine;

public interface Hud {
    GameItem[] getGameItems();

    void updateSize(Window window);

    void rotateCompass(float rotation);

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
