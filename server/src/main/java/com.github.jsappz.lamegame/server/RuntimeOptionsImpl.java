package com.github.jsappz.lamegame.server;

import org.springframework.beans.factory.annotation.Value;

public class RuntimeOptionsImpl implements RuntimeOptions {
    private static final String DEFAULT_CONNECTION_URL = "jdbc:mysql://mysql/lamegame?createDatabaseIfNotExist=true";
    private static final String H2_CONNECTION_URL = "jdbc:h2:mem:lamegame;MODE=MYSQL;DB_CLOSE_DELAY=-1";

    @Value("${db-embedded:false}")
    boolean dbEmbedded;

    @Value("${db-user:root}")
    String dbUser;

    @Value("${db-password:secret}")
    String dbPassword;

    @Value("${db-migrate:false}")
    boolean dbMigrate;

    @Value("${db-enabled:true}")
    boolean dbEnabled;

    @Override
    public String getDbUrl() {
        return dbEmbedded ? H2_CONNECTION_URL : DEFAULT_CONNECTION_URL;
    }

    @Override
    public String getDbUser() {
        return dbUser;
    }

    @Override
    public String getDbPassword() {
        return dbPassword;
    }

    @Override
    public boolean isDbEnabled() {
        return dbEnabled;
    }

    @Override
    public boolean isDbMigrate() {
        return dbMigrate;
    }
}
