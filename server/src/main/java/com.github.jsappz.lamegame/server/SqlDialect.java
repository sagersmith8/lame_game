package com.github.jsappz.lamegame.server;

public interface SqlDialect {
    default boolean isRetryable(Throwable t) {
        return false;
    }

    default String getCreateTableOptions() {
        return null;
    }
}
