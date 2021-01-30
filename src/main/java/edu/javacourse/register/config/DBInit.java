package edu.javacourse.register.config;

import edu.javacourse.register.dao.ConnectionBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBInit {
    public static void initDB() throws URISyntaxException, IOException, SQLException {
        URL url =  DBInit.class.getClassLoader().getResource("registr.sql");
        List<String> stringsCreateTable = Files.readAllLines(Paths.get(url.toURI()));
        String strFromSQLCreateTable = stringsCreateTable.stream().collect(Collectors.joining());

        try (Connection connection = ConnectionBuilder.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(strFromSQLCreateTable);
        }

    }
}
