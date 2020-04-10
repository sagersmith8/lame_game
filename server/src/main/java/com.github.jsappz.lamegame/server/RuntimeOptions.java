package com.github.jsappz.lamegame.server;

public interface RuntimeOptions {
    String getDbUrl();
    String getDbUser();
    String getDbPassword();
    boolean isDbEnabled();
    boolean isDbMigrate();
}
