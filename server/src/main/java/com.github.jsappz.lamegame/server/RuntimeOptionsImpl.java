package com.github.jsappz.lamegame.server;

import org.springframework.beans.factory.annotation.Value;

public class RuntimeOptionsImpl implements RuntimeOptions {
    private static final String DEFAULT_CONNECTION_URL = "jdbc:mysql://mysql/lamegame?createDatabaseIfNotExist=true";
    private static final String H2_CONNECTION_URL = "jdbc:h2:mem:lamegame;MODE=MYSQL;DB_CLOSE_DELAY=-1";

    @Value("${db-embedded:false}")
    boolean dbEmbedded;

    @Value("${db-url:}")
    String dbUrl;

    @Value("${db-user:root}")
    String dbUser;

    @Value("${db-password:secret}")
    String dbPassword;

    @Override
    public String getDbUrl() {
        if (dbUrl.isEmpty()) {
            return dbEmbedded ? H2_CONNECTION_URL : DEFAULT_CONNECTION_URL;
        }
        return dbUrl;
    }

    @Override
    public String getDbUser() {
        return dbUser;
    }

    @Override
    public String getDbPassword() {
        return dbPassword;
    }
}
