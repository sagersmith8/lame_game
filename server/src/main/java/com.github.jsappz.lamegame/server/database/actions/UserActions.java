package com.github.jsappz.lamegame.server.database.actions;

import com.github.jsappz.lamegame.server.database.DatabaseAction;
import com.github.jsappz.lamegame.server.model.User;

import java.sql.*;

public class UserActions {
    private UserActions() {}

    public static DatabaseAction<User, Exception> getUser(String username) {
        return (Connection conn) -> {
            String query = "SELECT * FROM users WHERE username= '" + username + "'";
            User.Builder user = User.builder();
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    assert resultSet.isLast();
                    user.id(resultSet.getInt("id"));
                    user.username(resultSet.getString("username"));
                }
            }
            return user.build();
        };
    }

    public static DatabaseAction<Boolean, Exception> createUser(User user) {
        return (Connection conn) -> {
            String query = " INSERT INTO users (username) VALUES (?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.execute();
                return true;
            }
        };
    }
}
