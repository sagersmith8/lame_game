package com.github.jsappz.lamegame.server.database.dialects;

import com.github.jsappz.lamegame.server.database.SqlDialect;
import com.mysql.cj.exceptions.MysqlErrorNumbers;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MySqlDialectTest {
    private static final SqlDialect DIALECT = SqlDialects.MY_SQL.get();

    @Test
    public void testIsRetryable_SQL_STATE_COMMUNICATION_LINK_FAILURE() {
        SQLException e = new SQLException("reason", MysqlErrorNumbers.SQL_STATE_COMMUNICATION_LINK_FAILURE);
        assertThat(DIALECT.isRetryable(e), is(true));
    }

    @Test
    public void testIsRetryable_SQL_STATE_ROLLBACK_SERIALIZATION_FAILURE() {
        SQLException e = new SQLException("reason", MysqlErrorNumbers.SQL_STATE_ROLLBACK_SERIALIZATION_FAILURE);
        assertThat(DIALECT.isRetryable(e), is(true));
    }

    @Test
    public void testIsRetryable_nonRetryableError() {
        SQLException e = new SQLException();
        assertThat(DIALECT.isRetryable(e), is(false));
    }

    @Test
    public void test_createTableOptions() {
        assertThat(DIALECT.getCreateTableOptions(), is("CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci Engine=InnoDB"));
    }
}
