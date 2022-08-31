package com.edencoding.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Location of database
     */
    private static final String seed_file_location = "C:/Code/commitments.db";

    /**
     * Currently only table needed
     */
    private static final String[] requiredTable = {"Tasks", "Steps", "Stakeholders", "Actors"};

    public static boolean isOK() {
        if (!checkDrivers()) return false; //driver errors

        if (!checkConnection()) return false; //can't connect to db

        return checkTables(); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            System.out.println("SETUP:\tSQLite Drivers have been started");
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            System.out.println("ERROR:\tCould not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean checkTables() {
        System.out.println("SETUP:\tChecking Tables");
        boolean allTablesPresent = true;

        for (String tableName : requiredTable) {
            String checkTables =
                    "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'";

            try (Connection connection = Database.connect()) {
                PreparedStatement statement = connection.prepareStatement(checkTables);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    System.out.println("SETUP:\tTable \"" + tableName + "\" present");
                } else {
                    System.out.println("ERROR:\tTable \"" + tableName + "\" NOT PRESENT");
                    allTablesPresent = false;
                }
            } catch (SQLException exception) {
                System.out.println("ERROR:\tSQL Exception encountered in checking tables");
                return false;
            }
        }
        return allTablesPresent;


    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + seed_file_location);
        } catch (SQLException exception) {
            System.out.println("ERROR:\tCould not connect to SQLite DB at " + seed_file_location);
            return null;
        }
        return connection;
    }
}