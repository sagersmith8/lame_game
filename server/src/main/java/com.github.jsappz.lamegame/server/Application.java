package com.github.jsappz.lamegame.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.github.jsappz.lamegame.server.database.DatabaseConfig;
import com.github.jsappz.lamegame.server.database.DatabaseManager;
import com.github.jsappz.lamegame.server.database.SqlDialect;
import com.github.jsappz.lamegame.server.database.dialects.SqlDialects;
import com.github.jsappz.lamegame.server.database.dialects.StandardDialect;
import com.github.jsappz.lamegame.server.response.UserResponseProvider;
import com.github.jsappz.lamegame.server.response.providers.DatabaseUserResponseProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket lamegameApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.github.jsappz.lamegame.server"))
                .build();
    }

    @Bean
    public RuntimeOptions options() {
        return new RuntimeOptionsImpl();
    }

    @Bean
    public SqlDialect sqlDialect() {
        // TODO Add database type detection
        return SqlDialects.STANDARD.get();
    }

    @Bean
    public DatabaseConfig databaseConfig(RuntimeOptions options) {
        return DatabaseConfig.builder()
                .jdbcUrl(options.getDbUrl())
                .password(options.getDbPassword())
                .user(options.getDbUser())
                // TODO Add database type detection
                .hikariConfig(DatabaseConfig.getDefaultHikariConfig())
                .sqlDialect(sqlDialect())
                .build();
    }

    @Bean
    public DatabaseManager databaseManager(DatabaseConfig databaseConfig) {
        DatabaseManager manager = new DatabaseManager(databaseConfig);
        manager.migrate();
        return manager;
    }

    @Bean
    public UserResponseProvider userResponseProvider(DatabaseManager manager) {
        return new DatabaseUserResponseProvider(manager);
    }
}
