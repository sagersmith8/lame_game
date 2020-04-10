package com.github.jsappz.lamegame.server;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManagerTest {
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new DatabaseManager(DatabaseConfig.builder()
                .jdbcUrl("jdbc:h2:mem:lamegame;database_to_upper=false;db_close_delay=-1;mode=mysql")
                .user("root")
                .password("secret")
                .sqlDialect(new StandardDialect())
                .build());
        databaseManager.migrate();
    }

    @Test
    public void testWriteAndRead() throws Exception {
        assertThat(databaseManager.write(writeUser("savage_smith")), is(false));
        assertThat(databaseManager.read(readUser("savage_smith")), is("success"));
    }

    public static DatabaseAction<Object, Exception> writeUser(String user) {
        return (Connection conn) -> {
            String query = " insert into users (username) values (?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, "savage_smith");
                return preparedStatement.execute();
            }
        };
    }

    public static DatabaseAction<Object, Exception> readUser(String user) {
        return (Connection conn) -> {
            String query = "SELECT * FROM users";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    assertThat(resultSet.getString("username"), is(user));
                }
            }
            return "success";
        };
    }
}
