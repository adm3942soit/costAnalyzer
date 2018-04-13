package com.adonis.costAnalyzer.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by oksdud on 19.04.2017.
 */
@Slf4j
public class DatabaseUtils {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String databaseName = "costanalyzer";
    static final String USER = "root";
    static final String PASS = "root";
    static final String DB_URL = "jdbc:mysql://localhost/?user=" + USER + "&password=" + PASS;
    static final String createDatabaseSql = "CREATE DATABASE " + databaseName;


    public static boolean createDatabase() {
        //STEP 1: Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //STEP 2: Open a connection
        System.out.println("Connecting to database...");

        try(
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {
            //STEP 3: Execute a query
            System.out.println("Creating database...");
            try {
                stmt.executeUpdate(createDatabaseSql);
            } catch (Exception e) {
                log.error("Database exist already!");
            }
            System.out.println("Database created successfully...");

            try {
                stmt.execute(FileReader.readFromFileFromResources("address.sql"));
            } catch (Exception e) {
                log.error("Table ADDRESS exist already!");
            }
            System.out.println("Table address created successfully...");
            try {
                stmt.execute(FileReader.readFromFileFromResources("credit_card.sql"));
            } catch (Exception e) {
                log.error("Table CREDIT_CARD exist already!");
            }
            System.out.println("Table CREDIT_CARD created successfully...");
            try {
                stmt.execute(FileReader.readFromFileFromResources("persons.sql"));
            } catch (Exception e) {
                log.error("Table PERSONS exist already!");
            }
            System.out.println("Table PERSONS created successfully...");

        } catch (Exception e) {
            log.error("Database exist already!");
        }
        return false;
    }
}
