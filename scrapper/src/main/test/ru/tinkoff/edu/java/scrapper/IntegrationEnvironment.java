package ru.tinkoff.edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class IntegrationEnvironment {
    protected static final PostgreSQLContainer<?> DB_CONTAINER;

    static {
        DB_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        DB_CONTAINER.start();
        runMigrations();
    }

    private static void runMigrations() {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_CONTAINER.getJdbcUrl(),
                    DB_CONTAINER.getUsername(),
                    DB_CONTAINER.getPassword()
            );
            Path path = new File("migrations").toPath().toAbsolutePath();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("master.xml", new DirectoryResourceAccessor(path), database);
            liquibase.update(new Contexts(), new LabelExpression());

        } catch (SQLException | LiquibaseException | FileNotFoundException exc) {
            throw new RuntimeException(exc);
        }
    }
}
