package com.puneet.stock;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.input_file_name;

public class CSVToPostresSpark {
    public static void main(String args[]){

        SparkSession sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example").master("local")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();
        Dataset<Row> csv = sparkSession.read().format("csv").option("header","true").option("inferSchema","true").load(args[0]);
        csv = csv.withColumn("name", input_file_name())
                .withColumnRenamed("Adj Close","adj_close")
                .withColumn("open",col("open").cast(DataTypes.DoubleType))
                .withColumn("high",col("high").cast(DataTypes.DoubleType))
                .withColumn("low",col("low").cast(DataTypes.DoubleType))
                .withColumn("close",col("close").cast(DataTypes.DoubleType))
                .withColumn("adj_close",col("adj_close").cast(DataTypes.DoubleType))
                .withColumn("volume",col("volume").cast(DataTypes.LongType));
        csv.show();;
        csv.write().format("jdbc")
    .option("url", "jdbc:postgresql://localhost:5432/postgres")
    .option("driver", "org.postgresql.Driver").option("dbtable", "stock_price_hist").mode("append")
    .option("user", "postgres").option("password", "root").save();

    }
}
