package com.github.jsappz.lamegame.engine.item;

import com.github.jsappz.lamegame.engine.graph.Material;
import com.github.jsappz.lamegame.engine.graph.Mesh;
import com.github.jsappz.lamegame.engine.graph.OBJLoader;
import com.github.jsappz.lamegame.engine.graph.Texture;

import java.nio.file.Paths;

public class SkyBox extends GameItem {

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Texture skyBoxTexture = new Texture(textureFile);
        skyBoxMesh.setMaterial(new Material(skyBoxTexture, 0.0f));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }
}
