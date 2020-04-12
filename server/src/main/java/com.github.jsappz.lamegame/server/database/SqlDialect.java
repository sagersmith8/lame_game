package com.github.jsappz.lamegame.server.database;

public interface SqlDialect {
    /**
     * Specifies if a given exception is retryable
     *
     * @param t the exception to check if it is retryable
     * @return true if the passed exception is retryable otherwise false
     */
    default boolean isRetryable(Throwable t) {
        return false;
    }

    /**
     * Specifies create table options to use in conjunction with flyaway
     *
     * @return custom flyaway table create options
     */
    default String getCreateTableOptions() {
        return null;
    }
}
