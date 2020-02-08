package com.github.jsappz.lamegame.engine;

import com.github.jsappz.lamegame.engine.item.GameItem;

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
