package com.puneet.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyConnectionPool {
    public static Connection getConnection(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "root");
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return c;
    }

}
