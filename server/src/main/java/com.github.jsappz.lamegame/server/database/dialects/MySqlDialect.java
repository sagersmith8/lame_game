package com.github.jsappz.lamegame.server.database.dialects;

import com.github.jsappz.lamegame.server.database.SqlDialect;
import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.mysql.cj.jdbc.Driver;

import java.sql.SQLException;

public class MySqlDialect implements SqlDialect {
    static {
        try {
            new Driver();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public MySqlDialect() {}

    @Override
    public boolean isRetryable(Throwable t) {
        if (t instanceof SQLException) {
            String sqlState = ((SQLException) t).getSQLState();
            return MysqlErrorNumbers.SQL_STATE_COMMUNICATION_LINK_FAILURE.equals(sqlState) ||
                    MysqlErrorNumbers.SQL_STATE_ROLLBACK_SERIALIZATION_FAILURE.equals(sqlState);
        }
        return false;
    }

    @Override
    public String getCreateTableOptions() {
        return "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci Engine=InnoDB";
    }
}
