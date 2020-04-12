package com.github.jsappz.lamegame.server.database.dialects;

import com.github.jsappz.lamegame.server.database.SqlDialect;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class StandardDialectTest {
    private static final SqlDialect DIALECT = SqlDialects.STANDARD.get();

    @Test
    public void test_isRetryable() {
        assertThat(DIALECT.isRetryable(new SQLException()), is(false));
    }

    @Test
    public void test_getCreateTableOptions() {
        assertThat(DIALECT.getCreateTableOptions(), is(nullValue()));
    }
}
