package com.github.jsappz.lamegame.engine.item;

import com.github.jsappz.lamegame.engine.graph.Material;
import com.github.jsappz.lamegame.engine.graph.Mesh;
import com.github.jsappz.lamegame.engine.graph.OBJLoader;
import com.github.jsappz.lamegame.engine.graph.Texture;

import java.nio.file.Paths;

public class SkyBox extends GameItem {

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(Paths.get(ClassLoader.getSystemResource(objModel).toURI()));
        Texture skyBoxTexture = new Texture(Paths.get(ClassLoader.getSystemResource(textureFile).toURI()).toFile().getAbsolutePath());
        skyBoxMesh.setMaterial(new Material(skyBoxTexture, 0.0f));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }
}
