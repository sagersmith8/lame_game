package com.github.jsappz.lamegame.server;

public interface RuntimeOptions {
    /**
     * Returns the jdbc url to use when connecting to the database
     *
     * @return the connection url to be used for the database
     */
    String getDbUrl();

    /**
     * Returns the user to use when connecting to the database
     *
     * @return the user to use to connect to the database
     */
    String getDbUser();

    /**
     * Returns the password to use when connecting to the database
     *
     * @return the password to use to connect to the database
     */
    String getDbPassword();
}
