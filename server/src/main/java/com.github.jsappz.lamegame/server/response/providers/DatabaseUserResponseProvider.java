package com.github.jsappz.lamegame.server.response.providers;

import com.github.jsappz.lamegame.server.database.DatabaseManager;
import com.github.jsappz.lamegame.server.database.actions.UserActions;
import com.github.jsappz.lamegame.model.JsonConverter;
import com.github.jsappz.lamegame.model.User;
import com.github.jsappz.lamegame.server.response.UserResponseProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;

import static java.util.Objects.requireNonNull;

public class DatabaseUserResponseProvider implements UserResponseProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUserResponseProvider.class);
    private final DatabaseManager databaseManager;

    public DatabaseUserResponseProvider(DatabaseManager databaseManager) {
        this.databaseManager = requireNonNull(databaseManager, "database manager");
    }

    @Override
    public StreamingResponseBody getUser(String username) {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                User user;
                try {
                    user = databaseManager.read(UserActions.getUser(username));
                } catch (Exception e) {
                    LOGGER.error("Error reading from database " + e, e);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                JsonConverter.writeToStream(outputStream, user);
            }
        };
    }

    @Override
    public StreamingResponseBody createUser(User user) {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                boolean success;
                try {
                    success = databaseManager.write(UserActions.createUser(user));
                } catch (Exception e) {
                    LOGGER.error("Error writing to database " + e, e);
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if (!success) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        };
    }
}
