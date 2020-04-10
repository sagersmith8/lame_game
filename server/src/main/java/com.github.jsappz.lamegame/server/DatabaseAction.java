package com.github.jsappz.lamegame.server;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseAction<T, Ex extends Exception> {
    T execute(Connection conn) throws SQLException, Ex;
}
