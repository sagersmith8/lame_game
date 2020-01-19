package com.github.jsappz.lamegame.game;

import com.github.jsappz.lamegame.engine.GameItem;
import com.github.jsappz.lamegame.engine.GameLogic;
import com.github.jsappz.lamegame.engine.ModelLoader;
import com.github.jsappz.lamegame.engine.Window;
import com.github.jsappz.lamegame.engine.graph.Mesh;
import com.github.jsappz.lamegame.engine.graph.Texture;
import org.joml.Vector3f;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class DummyGame implements GameLogic {
    private int x=0, y=0, z=0, xRot=0, yRot=0, zRot=0, scale=1;
    private final Renderer renderer;
    private GameItem[] gameItems;

    public DummyGame() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        // Create the Mesh
        float[] positions = new float[] {
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,

                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,};
//        Path path = Paths.get(ClassLoader.getSystemResource("obj/dog.obj").toURI());
//        GameItem gameItem = ModelLoader.loadModel(path);
        Path path = Paths.get(ClassLoader.getSystemResource("obj/textures/grassblock.png").toURI());
        Texture texture = new Texture(path.toFile().getAbsolutePath());
        Mesh mesh = new Mesh(positions, textCoords, indices, texture);
        GameItem gameItem = new GameItem(mesh);
        gameItem.setPosition(0, 0, -2);
        gameItems = new GameItem[] { gameItem };
    }

    @Override
    public void input(Window window) {
        x=0;y=0;z=0;
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            y += 3;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            y -= 3;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            x -= 3;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            x += 3;
        } else if (window.isKeyPressed(GLFW_KEY_W)) {
            z += 30;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            z -= 30;
        } else if (window.isKeyPressed(GLFW_KEY_Z)) {
            zRot += 3;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            xRot += 3;
        } else if (window.isKeyPressed(GLFW_KEY_Y)) {
            yRot += 3;
        } else if (window.isKeyPressed(GLFW_KEY_P)) {
            scale += 3;
        } else if (window.isKeyPressed(GLFW_KEY_O)) {
            scale -= 3;
        }
    }

    @Override
    public void update(float interval) {
        for (GameItem gameItem : gameItems) {
            // Update position
            Vector3f itemPos = gameItem.getPosition();
            float posx = itemPos.x + x * 0.01f;
            float posy = itemPos.y + y * 0.01f;
            float posz = itemPos.z + z * 0.01f;
            gameItem.setPosition(posx, posy, posz);
            if ( scale < 0 ) {
                scale = 0;
            }
            gameItem.setScale(scale);
            gameItem.setRotation(xRot, yRot, zRot);
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
