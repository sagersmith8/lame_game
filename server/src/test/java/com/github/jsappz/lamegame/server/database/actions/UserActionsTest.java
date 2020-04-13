package com.github.jsappz.lamegame.server.database.actions;

import com.github.jsappz.lamegame.server.database.DatabaseConfig;
import com.github.jsappz.lamegame.server.database.DatabaseManager;
import com.github.jsappz.lamegame.server.database.dialects.StandardDialect;
import com.github.jsappz.lamegame.server.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserActionsTest {
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
        databaseManager.write(UserActions.createUser(User.builder().username("user").build()));
        User user = databaseManager.read(UserActions.getUser("user"));
        assertThat(user.getUsername(), is("user"));
        assertThat(user.getId(), is(greaterThan(0)));
    }
}
