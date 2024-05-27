package com.griddynamics;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/statements";
        String user = "dev";
        String password = "MyPass";
        String tableName = "students";
        int mean = 5;
        String firstName = "XD' OR 1 = 1; --";
        String lastName = "XD' OR 1 = 1";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            printAllRows(connection, tableName);
            int rowsUpdated;
            //sql injection attack failed
            String safeUpdateSQL = "UPDATE students SET mean = ? WHERE first_name = ? AND last_name = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(safeUpdateSQL)) {
                preparedStatement.setInt(1, mean);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);

                rowsUpdated = preparedStatement.executeUpdate();
                printSQLInjectionAttackSummary(rowsUpdated, connection, tableName);
            }

            //sql injection attack succeeded
            String hackingUpdateSQL = "UPDATE students SET mean = " + mean + " WHERE first_name = '" + firstName + "' AND " + "last_name = '" + lastName + "';";
            try (Statement statement = connection.createStatement()) {
                rowsUpdated = statement.executeUpdate(hackingUpdateSQL);
            }
            printSQLInjectionAttackSummary(rowsUpdated, connection, tableName);


        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error("unknown exception occurred");
            log.error(e.getMessage(), e);
        }
    }

    private static void printSQLInjectionAttackSummary(int rowsUpdated, Connection connection, String tableName) throws SQLException {
        if (rowsUpdated > 0) {
            log.info("sql injection attack succeeded");
            printAllRows(connection, tableName);
        } else {
            log.info("sql injection attack failed\n");
        }
    }

    private static void printAllRows(Connection connection, String table_name) throws SQLException {
        String selectAllSQL = "SELECT * FROM %s;".formatted(table_name);
        try (Statement selectAllStmt = connection.createStatement()) {
            ResultSet resultSet = selectAllStmt.executeQuery(selectAllSQL);
            printResultSet(resultSet);
            resultSet.close();
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        log.info("\n");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            float mean = resultSet.getFloat("mean");
            log.info("%d\t%s\t%s\t%.2f".formatted(id, firstName, last_name, mean));
        }
    }
}