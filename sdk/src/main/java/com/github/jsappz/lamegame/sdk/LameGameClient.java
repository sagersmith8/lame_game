package com.github.jsappz.lamegame.sdk;

import com.github.jsappz.lamegame.model.User;

public interface LameGameClient {
    User getUser(String username);

    boolean createUser(User user);
}
