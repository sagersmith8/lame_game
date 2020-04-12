package com.github.jsappz.lamegame.server.response;

import com.github.jsappz.lamegame.server.model.User;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface UserResponseProvider {
    StreamingResponseBody getUser(String username);

    StreamingResponseBody createUser(User user);
}
