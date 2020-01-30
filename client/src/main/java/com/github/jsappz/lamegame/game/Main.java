package com.github.jsappz.lamegame.game;

import com.github.jsappz.lamegame.engine.GameEngine;
import com.github.jsappz.lamegame.engine.GameLogic;

public class Main {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            GameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("Lame Game", 600, 480, vSync, gameLogic);
            gameEng.run();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}