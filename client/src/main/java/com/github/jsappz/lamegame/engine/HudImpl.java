package com.github.jsappz.lamegame.engine;

import org.joml.Vector4f;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HudImpl implements Hud {

    private static final int FONT_COLS = 16;

    private static final int FONT_ROWS = 16;

    private final Path fontTexture;

    private final GameItem[] gameItems;

    private final TextItem statusTextItem;

    public HudImpl(String statusText) throws Exception {
       fontTexture = Paths.get(ClassLoader.getSystemResource("textures/font_texture.png").toURI());
       this.statusTextItem = new TextItem(statusText, fontTexture.toFile().getAbsolutePath(), FONT_COLS, FONT_ROWS);
       this.statusTextItem.getMesh().getMaterial().setAmbientColor(new Vector4f(1, 1, 1, 1));
       gameItems = new GameItem[]{statusTextItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    }
}