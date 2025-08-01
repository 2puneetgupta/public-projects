package com.puneet.stock;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static  Double amount= 10000.0;
    public static int  profitPc = 4;
    public static String user_day_price = "close";
    //Receive Rule from user

    //choose start day

    // 1. Apply rule for the day
    // 2. if true - buy/sell OR false - move to next day(goto 1)
    // 3. Buy/Sell - update trancatin table
    // 4. move to next day(goto 1)

    public static void main(String args[]) throws SQLException {

        long startTime = System.currentTimeMillis();

        ArrayList<String> stocksNames =  getStockList();
        resetTransactionAndPortfolio();
        stocksNames.parallelStream().forEach(currentStockname -> {
       // for(String currentStockname : stocksNames) {
            //String currentStockname = "file:///D:/stockData/APOLLOHOSP.NS.csv";
            LocalDate currentDate = LocalDate.parse("2020-11-20");
            System.out.println("Started simulation for : " + currentStockname);
            amount= 10000.0;

            //Rule - buy 10% down Sell 10% up from avg price


            Double priceFortheday = null;
            try {
                priceFortheday = getPriceFortheday(currentStockname, Date.valueOf(currentDate));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(priceFortheday);
            try {
                updateTransaction(currentStockname, Date.valueOf(currentDate), priceFortheday, 1, "B");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                updateCurrentHolding(currentStockname, priceFortheday, 1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            while (amount > 0 && currentDate.isBefore(LocalDate.now().minusYears(2))) {
                currentDate = currentDate.plusDays(1);
                try {
                    simulateBuySellPercentMove(currentStockname, currentDate);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            Double profit = null;
            try {
                profit = calculateProfit(currentStockname);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("profit : " + profit);
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken" + (endTime - startTime));
        });
     }

    private static ArrayList<String> getStockList() throws SQLException {

        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("select  distinct name from stock_price_hist;");

        ResultSet rs = stmt.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while ( rs.next() ) {
            list.add(rs.getString("name"));
        }
        rs.close();
        stmt.close();

        return list;
    }

    private static Double calculateProfit(String stock) throws SQLException {


        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("select  sum(case when buy_sell='S' then price * quantity else 0 end) - sum(case " +
                "when buy_sell='B' then price * quantity else 0 end ) as total_profit from transaction where stock_name = ?;");
        stmt.setString(1,stock);

        ResultSet rs = stmt.executeQuery();
        Double TransProfit =0.0;
        while ( rs.next() ) {
            double id = rs.getDouble("total_profit");
            TransProfit = id;
        }
        rs.close();
        stmt.close();

        PreparedStatement stmt2 = connection.prepareStatement("select  avg_price * quantity as hold_price from current_holding where stock_name = ?;");
        stmt2.setString(1,stock);

        ResultSet rs2 = stmt2.executeQuery();
        Double leftStockPrice =0.0;
        while ( rs2.next() ) {
            double id = rs2.getDouble("hold_price");
            leftStockPrice = id;
        }
        rs2.close();
        stmt2.close();

        connection.close();
        return TransProfit + leftStockPrice;
    }

    private static void resetTransactionAndPortfolio() throws SQLException {

        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("delete from transaction ; delete from current_holding;");

        stmt.executeUpdate();
        connection.close();
    }

    static long counter=0;
    private static void simulateBuySellPercentMove(String currentStockname, LocalDate currentDate) throws SQLException {
        counter ++;
        Double priceFortheday = getPriceFortheday(currentStockname, Date.valueOf(currentDate));
        //System.out.println("Current iteration .."+ counter + " .."+currentDate+"..."+priceFortheday);
        //10% B S on move
        ResultSet currentHolding = getCurrentHolding(currentStockname);
        if(!currentHolding.next() || priceFortheday == 0)
            return;
        double avg_price = currentHolding.getDouble("avg_price");

        long holdingQuantiy = currentHolding.getLong("quantity");


        if( holdingQuantiy > 0 && priceFortheday > avg_price + (avg_price * profitPc/100) ){
            //Sell
            updateTransaction(currentStockname,Date.valueOf(currentDate),priceFortheday,1,"S");
            if(holdingQuantiy > 1)
            updateCurrentHolding(currentStockname,priceFortheday,-1);
            else updateHoldingToZero(currentStockname,priceFortheday);

            amount = amount + priceFortheday;
        }else if(priceFortheday > 0 && priceFortheday < avg_price - (avg_price * profitPc/100)){
            //Buy
            updateTransaction(currentStockname,Date.valueOf(currentDate),priceFortheday,1,"B");
            updateCurrentHolding(currentStockname,priceFortheday,1);
            amount = amount - priceFortheday;
        }


    }

    static Double getPriceFortheday(String stock, Date date) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM stock_price_hist where name = ? and date = ? ;");
        stmt.setString(1,stock);
        stmt.setDate(2, (java.sql.Date) date);
        ResultSet rs = stmt.executeQuery();
        Double price =0.0;
        while ( rs.next() ) {

            double id = rs.getDouble(user_day_price);
            price = id;
        }
        rs.close();
        stmt.close();
        connection.close();
        return price;

    }

    static Double updateTransaction(String stock, Date date,Double price,int quantity,String buySell) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into transaction" +
                " (stock_name,date,price,quantity,buy_sell) " +
                "values(?,?,?,?,?);");
        stmt.setString(1,stock);
        stmt.setDate(2, (java.sql.Date) date);
        stmt.setDouble(3,price);
        stmt.setDouble(4,quantity);
        stmt.setString(5,buySell);

        stmt.executeUpdate();
        stmt.close();
        connection.close();
        return price;


    }

    static Double updateCurrentHolding(String stock,Double price,int quantity) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();

        ResultSet resultSet = getCurrentHolding(stock);
        if( !resultSet.next()) {

            insertNewHolding(stock, price, quantity);
        }else {

            updateExistingHolding(stock, price, quantity);
        }

        connection.close();
        return price;


    }

    private static void updateExistingHolding(String stock, Double price, int quantity) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("update current_holding " +
                "set quantity = quantity + ? , avg_price = ((avg_price * quantity) + ? * ?)/ (quantity + ?)" +
                " where stock_name = ? ;");


        stmt.setLong(1, quantity);
        stmt.setDouble(2, price);
        stmt.setLong(3, quantity);
        stmt.setLong(4, quantity);
        stmt.setString(5, stock);

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    private static void updateHoldingToZero(String stock, Double priceFortheday) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("update current_holding " +
                "set quantity = 0 , avg_price = ?" +
                " where stock_name = ? ;");

        stmt.setDouble(1, priceFortheday);

        stmt.setString(2, stock);

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    private static void insertNewHolding(String stock, Double price, int quantity) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into current_holding" +
                " (stock_name,avg_price,quantity) " +
                "values(?,?,?);");
        stmt.setString(1, stock);
        stmt.setDouble(2, price);
        stmt.setLong(3, quantity);

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    private static ResultSet getCurrentHolding(String stock) throws SQLException {
        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmtSelect  = connection.prepareStatement("SELECT * FROM current_holding where stock_name = ? ;");
        stmtSelect.setString(1, stock);

        ResultSet resultSet = stmtSelect.executeQuery();
        connection.close();
        return resultSet;
    }
}
