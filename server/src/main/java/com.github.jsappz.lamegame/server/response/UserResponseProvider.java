package com.github.jsappz.lamegame.server.response;

import com.github.jsappz.lamegame.server.model.User;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface UserResponseProvider {
    /**
     * Looks up the user from the database and formats to response as json
     *
     * @param username the user to look up
     * @return the json body of a get user request
     */
    StreamingResponseBody getUser(String username);

    /**
     * Creates the specified user in the database
     *
     * @param user the user to create in the database
     * @return a json response indicating success or failure
     */
    StreamingResponseBody createUser(User user);
}
