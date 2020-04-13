package com.github.jsappz.lamegame.server;

import com.github.jsappz.lamegame.server.model.User;
import com.github.jsappz.lamegame.server.response.UserResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import static java.util.Objects.requireNonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController {
    private final UserResponseProvider userResponseProvider;

    @Autowired
    public MainController(UserResponseProvider userResponseProvider) {
        this.userResponseProvider = requireNonNull(userResponseProvider, "userResponseProvider");
    }

    @RequestMapping(
            value = "/user/{username}",
            method = GET,
            produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> getUser(
            @PathVariable("username")
            String username
    ) {
        return new ResponseEntity<>(userResponseProvider.getUser(username), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/user",
            method =POST,
            produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> createUser(
            @RequestHeader("username")
            String username) {
        User user = User.builder()
                .username(username)
                .build();
        return new ResponseEntity<>(userResponseProvider.createUser(user), HttpStatus.CREATED);
    }
}
