package com.github.jsappz.lamegame.server.database.dialects;

import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SqlDialectsTest {

    @Test
    public void test_MY_SQL_get() {
        assertThat(SqlDialects.MY_SQL.get(), is(instanceOf(MySqlDialect.class)));
    }

    @Test
    public void test_STANDARD_get() {
        assertThat(SqlDialects.STANDARD.get(), is(instanceOf(StandardDialect.class)));
    }
}
