package edu.javacourse.register.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {
    public Connection getConnection() throws SQLException;
}
