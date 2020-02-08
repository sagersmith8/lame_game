package com.github.jsappz.lamegame.engine;

import com.github.jsappz.lamegame.engine.item.GameItem;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
    public static GameItem loadModel(Path path) {
        try {
            List<Float> vertexList = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line == null || line.isEmpty() || "".equals(line)) {
                        continue;
                    }
                    String[] splitLine = line.split("\\s+");
                    switch (splitLine[0]) {
                        case "v":
                            parseVertex(splitLine, vertexList);
                            break;
                        case "f":
                            parseIndex(splitLine, indexList);
                            break;
                        default:
                            break;
                    }
                }
            }
            float [] verts = new float[vertexList.size()];
            for (int i = 0; i < vertexList.size(); i++) {
                verts[i] = vertexList.get(i);
            }

            float [] colors = new float[vertexList.size()];
            for (int i = 0; i < vertexList.size(); i++) {
                colors[i] = 1;
            }

            int [] indexes = new int[indexList.size()];
            for (int i = 0; i < indexList.size(); i++) {
                indexes[i] = indexList.get(i);
            }
            System.out.println(vertexList);
            System.out.println(indexList.size() + " " + indexList.size()%3);
//            Mesh mesh = new Mesh(verts, colors, indexes);
//            return new GameItem(mesh);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parseVertex(String[] splitVertex, List<Float> vertexList) {
        for (int i = 1; i < splitVertex.length; i++) {
            vertexList.add(Float.parseFloat(splitVertex[i]));
        }
    }

    private static void parseIndex(String[] splitIndex, List<Integer> indexList) {
        if (splitIndex.length == 5) {
            indexList.add(getIndex(splitIndex[1]));
            indexList.add(getIndex(splitIndex[2]));
            indexList.add(getIndex(splitIndex[4]));
            indexList.add(getIndex(splitIndex[4]));
            indexList.add(getIndex(splitIndex[2]));
            indexList.add(getIndex(splitIndex[3]));
        } else {
            indexList.add(getIndex(splitIndex[1]));
            indexList.add(getIndex(splitIndex[2]));
            indexList.add(getIndex(splitIndex[3]));
        }
    }

    private static int getIndex(String expandedIndex) {
        String index = expandedIndex;
        if (expandedIndex.contains("/")) {
            index = expandedIndex.substring(0, expandedIndex.indexOf("/"));
        }
        return Integer.parseInt(index);
    }

    public static void main(String []args) throws URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource("obj/Wolf_obj.obj").toURI());
        loadModel(path);
    }
}
