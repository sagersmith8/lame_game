package com.github.jsappz.lamegame.engine;

import com.github.jsappz.lamegame.engine.graph.FontTexture;
import com.github.jsappz.lamegame.engine.graph.Material;
import com.github.jsappz.lamegame.engine.graph.Mesh;
import com.github.jsappz.lamegame.engine.graph.OBJLoader;
import org.joml.Vector4f;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HudImpl implements Hud {
    private static final Font FONT = new Font(Font.SERIF, Font.PLAIN, 20);
    private static final String CHARSET = "ISO-8859-1";

    private final GameItem[] gameItems;
    private final TextItem statusTextItem;
    private final GameItem compassItem;
    public HudImpl(String statusText) throws Exception {
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        this.statusTextItem = new TextItem(statusText, fontTexture);
       this.statusTextItem.getMesh().getMaterial().setAmbientColor(new Vector4f(1, 1, 1, 1));
        // Create compass
        Path path = Paths.get(ClassLoader.getSystemResource("models/compass.obj").toURI());
        Mesh mesh = OBJLoader.loadMesh(path);
        Material material = new Material();
        material.setAmbientColor(new Vector4f(.5f, 0, 0, 1));
        mesh.setMaterial(material);
        compassItem = new GameItem(mesh);
        compassItem.setScale(40.0f);
        // Rotate to transform it to screen coordinates
        compassItem.setRotation(0f, 0f, 180f);

        // Create list that holds the items that compose the HUD
        gameItems = new GameItem[]{statusTextItem, compassItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    public void rotateCompass(float angle) {
        this.compassItem.setRotation(0, 0, 180 + angle);
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
        this.compassItem.setPosition(window.getWidth() - 40f, 50f, 0);
    }
}