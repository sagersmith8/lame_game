package com.github.jsappz.lamegame.server;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseAction<T, Ex extends Exception> {
    /**
     * Executes an sql action on the passed connection
     *
     * @param conn the connection to use when executing the database action
     * @return the result of the database action
     * @throws SQLException for malformed sql, or invalid connection
     * @throws Ex some unforseen error occurred when the action was executed
     */
    T execute(Connection conn) throws SQLException, Ex;
}
