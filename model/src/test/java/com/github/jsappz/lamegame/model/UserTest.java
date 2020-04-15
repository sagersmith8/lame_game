package com.github.jsappz.lamegame.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test(expected = NullPointerException.class)
    public void testUser_builder_NPE() {
        User.builder().build();
    }

    @Test
    public void testUser_builder() {
        User user = User.builder()
                .id(1)
                .username("user")
                .build();

        assertThat(user.getId(), is(1));
        assertThat(user.getUsername(), is("user"));
    }
}
