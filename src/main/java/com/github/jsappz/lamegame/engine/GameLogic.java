package com.github.jsappz.lamegame.engine;

import com.github.jsappz.lamegame.engine.Window;

public interface GameLogic {
    void init() throws Exception;
    void input(Window window);
    void update(float interval);
    void render(Window window);
}
