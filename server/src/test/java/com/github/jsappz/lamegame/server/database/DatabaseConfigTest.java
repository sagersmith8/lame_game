package com.github.jsappz.lamegame.server.database;

import com.github.jsappz.lamegame.server.database.dialects.SqlDialects;
import com.github.jsappz.lamegame.server.database.dialects.StandardDialect;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DatabaseConfigTest {

    @Test(expected = NullPointerException.class)
    public void testDatabaseConfig_builder_nullValue() {
       DatabaseConfig.builder().build();
    }

    @Test
    public void testDatabaseConfig_builder() {
        DatabaseConfig config = DatabaseConfig.builder()
                .user("user")
                .password("password")
                .sqlDialect(SqlDialects.STANDARD.get())
                .jdbcUrl("url")
                .build();

        assertThat(config.getDialect(), is(instanceOf(StandardDialect.class)));
        assertThat(config.getConfig("").getUsername(), is("user"));
        assertThat(config.getConfig("").getPassword(), is("password"));
        assertThat(config.getConfig("").getJdbcUrl(), is("url"));
    }
}
