package com.puneet.stock.chart;

import com.puneet.stock.MyConnectionPool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class LineChartExample2 extends Application {

    @Override public void start(Stage stage) throws SQLException {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        TreeMap<String, Double> trasctions = getTrasctions("TCS");
        trasctions.forEach((x,y)-> series1.getData().add(new XYChart.Data(x, y)));
/*        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));*/

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 2");
        TreeMap<String, Double> trasctions2 = getTrasctions("ONGC");
        trasctions2.forEach((x,y)-> series2.getData().add(new XYChart.Data(x, y)));
        /*series2.getData().add(new XYChart.Data("Jan", 33));
        series2.getData().add(new XYChart.Data("Feb", 34));
        series2.getData().add(new XYChart.Data("Mar", 25));
        series2.getData().add(new XYChart.Data("Apr", 44));
        series2.getData().add(new XYChart.Data("May", 39));
        series2.getData().add(new XYChart.Data("Jun", 16));
        series2.getData().add(new XYChart.Data("Jul", 55));
        series2.getData().add(new XYChart.Data("Aug", 54));
        series2.getData().add(new XYChart.Data("Sep", 48));
        series2.getData().add(new XYChart.Data("Oct", 27));
        series2.getData().add(new XYChart.Data("Nov", 37));
        series2.getData().add(new XYChart.Data("Dec", 29));*/

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Portfolio 3");
        TreeMap<String, Double> trasctions3 = getTrasctions("BAJFINANCE");
        trasctions3.forEach((x,y)-> series3.getData().add(new XYChart.Data(x, y)));
/*
        series3.getData().add(new XYChart.Data("Jan", 44));
        series3.getData().add(new XYChart.Data("Feb", 35));
        //series3.getData().add(new XYChart.Data("Mar", 36));
        series3.getData().add(new XYChart.Data("Apr", 33));
        series3.getData().add(new XYChart.Data("May", 31));
        series3.getData().add(new XYChart.Data("Jun", 26));
        series3.getData().add(new XYChart.Data("Jul", 22));
        series3.getData().add(new XYChart.Data("Aug", 25));
        series3.getData().add(new XYChart.Data("Sep", 43));
        series3.getData().add(new XYChart.Data("Oct", 44));
        series3.getData().add(new XYChart.Data("Nov", 45));
        series3.getData().add(new XYChart.Data("Dec", 44));
*/

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2, series3);

        stage.setScene(scene);
        stage.show();
    }

    private static TreeMap<String, Double> getTrasctions(String stock) throws SQLException {

        Connection connection = MyConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("select  * from transaction where  stock_name like  ? ;");

        stmt.setString(1,"%"+stock+"%");

        ResultSet rs = stmt.executeQuery();
        TreeMap<String,Double> list = new TreeMap<>();

        while ( rs.next() ) {
            list.put(rs.getString("date"),rs.getDouble("price"));
        }
        rs.close();
        stmt.close();

        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

