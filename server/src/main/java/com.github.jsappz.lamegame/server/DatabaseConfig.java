package com.github.jsappz.lamegame.server;

import com.zaxxer.hikari.HikariConfig;

import static java.util.Objects.requireNonNull;

public class DatabaseConfig {
    private HikariConfig baseConfig;
    private SqlDialect dialect;

    private DatabaseConfig(Builder b) {
        this.baseConfig = requireNonNull(b.config, "config");
        this.baseConfig.setJdbcUrl(requireNonNull(b.jdbcUrl, "jdbc url"));
        this.baseConfig.setUsername(requireNonNull(b.user, "user"));
        this.baseConfig.setPassword(requireNonNull(b.password, "password"));
        this.dialect = requireNonNull(b.sqlDialect, "sql dialect");
    }

    public SqlDialect getDialect() {
        return dialect;
    }

    // This is used to allow the allow our base config to not
    // affect the configuration if the builder is used to create
    // multiple DatabaseConfig's
    public HikariConfig getConfig(String poolName) {
        HikariConfig hikariConfig = new HikariConfig();
        baseConfig.copyStateTo(hikariConfig);
        hikariConfig.setPoolName(poolName);
        return hikariConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String user;
        private String password;
        private String jdbcUrl;
        private HikariConfig config = getDefaultHikariConfig();
        private SqlDialect sqlDialect;

        private Builder() {}

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder jdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        public Builder hikariConfig(HikariConfig config) {
            this.config = config;
            return this;
        }

        public Builder sqlDialect(SqlDialect dialect) {
            this.sqlDialect = dialect;
            return this;
        }

        public DatabaseConfig build() {
            return new DatabaseConfig(this);
        }
    }

    public static HikariConfig getDefaultHikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setInitializationFailTimeout(0);
        config.setAutoCommit(false);
        return config;
    }

    public static HikariConfig getMySqlConfig() {
        HikariConfig config = getDefaultHikariConfig();
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);
        return config;
    }
}
