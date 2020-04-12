package com.github.jsappz.lamegame.server.database;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class DatabaseManager implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);
    private final SqlDialect sqlDialect;
    private final DataSource dataSource;

    public DatabaseManager(DatabaseConfig config) {
        requireNonNull(config, "database config");
        this.sqlDialect = config.getDialect();
        this.dataSource = new HikariDataSource(config.getConfig("lamegame"));
    }

    private Connection getInitializedConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void migrate() {
        // TODO add retries
        doMigrate();
    }

    private void doMigrate() {
        LOGGER.info("Starting database migration");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .placeholders(getMigratePlaceholders())
                .load();
        flyway.migrate();
        LOGGER.info("Finished database migration");
    }

    public <T, Ex extends Exception> T write(DatabaseAction<T, Ex> action) throws SQLException, Ex {
        // TODO add retries
        return doWrite(action);
    }

    private <T, Ex extends Exception> T doWrite(DatabaseAction<T, Ex> action) throws SQLException, Ex {
        try (Connection conn = getInitializedConnection()) {
            try {
                T result = action.execute(conn);

                LOGGER.debug("Committing");
                conn.commit();
                LOGGER.debug("Committed");
                return result;
            } catch (Throwable t) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    t.addSuppressed(e);
                }
                throw t;
            }
        }
    }

    public <T, Ex extends Exception> T read(DatabaseAction<T, Ex> action) throws SQLException, Ex {
        // TODO add retries
        return doRead(action);
    }

    private  <T, Ex extends Exception> T doRead(DatabaseAction<T, Ex> action) throws SQLException, Ex {
        try (Connection conn = getInitializedConnection()) {
            return action.execute(conn);
        }
    }

    private Map<String, String> getMigratePlaceholders() {
        Map<String, String> map = new HashMap<>();
        map.put("createTableOptions", sqlDialect.getCreateTableOptions());
        return map;
    }

    @Override
    public void close() throws Exception {
        try (AutoCloseable closeDataSource = ((Closeable)dataSource)) {
            LOGGER.debug("Closing data source");
        }
    }
}
