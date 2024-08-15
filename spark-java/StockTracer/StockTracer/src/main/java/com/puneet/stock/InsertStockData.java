/*
package com.puneet.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertStockData {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "root");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO stock_price_hist\n" +
                    "VALUES\n" +
                    "('7/1/2012',\t63.549999,\t65.449997,\t63.549999,\t64.974998,\t54.247997,\t107294\n" +
                    ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}*/
