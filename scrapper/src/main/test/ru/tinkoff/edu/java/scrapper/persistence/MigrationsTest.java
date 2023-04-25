package ru.tinkoff.edu.java.scrapper.persistence;

import lombok.SneakyThrows;
import org.junit.Test;
import ru.tinkoff.edu.java.scrapper.persistence.IntegrationEnvironment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigrationsTest extends IntegrationEnvironment {
    @Test
    @SneakyThrows
    public void tablesCreatedAfterMigrationsTest() {
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

        assertTrue(tables.stream().anyMatch(table -> table.equals("chat")));
        assertTrue(tables.stream().anyMatch(table -> table.equals("link")));
        assertTrue(tables.stream().anyMatch(table -> table.equals("chat_link")));
    }
}
