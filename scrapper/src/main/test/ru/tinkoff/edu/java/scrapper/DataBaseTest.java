package ru.tinkoff.edu.java.scrapper;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataBaseTest extends IntegrationEnvironment {
    @Test
    public void tablesCreatedAfterMigrationsTest() {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_CONTAINER.getJdbcUrl(),
                    DB_CONTAINER.getUsername(),
                    DB_CONTAINER.getPassword()
            );

            List<String> tables = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLE_NAME"));
            }

            assertTrue(tables.stream().anyMatch(x -> x.equals("chat")));
            assertTrue(tables.stream().anyMatch(x -> x.equals("link")));
            assertTrue(tables.stream().anyMatch(x -> x.equals("chat_link")));
        }
        catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }
}
