/*
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DecimalType;
import org.apache.spark.sql.types.DoubleType;
import org.apache.spark.sql.types.LongType$;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.LongType;

import org.apache.spark.sql.SparkSession;

public class StockTracker {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: StockTracker <file>");
            System.exit(1);
        }
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example").master("local")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();
        Dataset<Row> csv = sparkSession.read().format("csv").option("header","true").option("inferSchema","true").load(args[0]);
        csv = csv.filter("Date > '2020-01-01'").filter("Date < '2021-01-01'");
        csv = csv.withColumn("Open",col("Open").cast(DoubleType))
                .withColumn("High",col("High").cast(DoubleType))
                .withColumn("Low",col("Low").cast(DoubleType))
                .withColumn("Adj_Close",col("Adj Close").cast(DoubleType))
                .withColumn("Volume",col("Volume").cast(LongType))
                .withColumn("input_file_name",input_file_name())
                .withColumn("BUY_SELL_IND",lit("NA"));
       // csv.printSchema();
       // csv.select(max("Date"),min("Date")).show();

        //scenario 1 : buy at 5% down and sell at 5% up
        // print positions at last wiht profit and loss
        csv.registerTempTable("stockData");
        Dataset<Row> cv1 = csv.sqlContext().sql(
                "SELECT Date,Open,input_file_name " +
                        //", LAG(Open,1) OVER (PARTITION BY input_file_name ORDER BY Date) AS PREV_OPEN " +
                        ",( LAG(Open,1) OVER (PARTITION BY input_file_name ORDER BY Date) / Open) *100 " +
                        "AS PREV_OPEN_PRC " +
                        "FROM stockData "
        );
       // cv1.show();
        cv1.registerTempTable("cv1");
        */
/*cv1.sqlContext().sql(
                "SELECT Date,Open,PREV_OPEN_PRC " +
                        //", LAG(Open,1) OVER (PARTITION BY input_file_name ORDER BY Date) AS PREV_OPEN " +
                        ",case when (" +
                        "( LAG(PREV_OPEN_PRC,1) OVER (PARTITION BY input_file_name ORDER BY Date) ) - PREV_OPEN_PRC " +
                        ") > 0 THEN 'bigger' ELSE 'small' END  " +
                        "AS PREV_OPEN_PRC_diff " +
                        "FROM cv1"
        ).show();*//*


        Dataset<Row> cv2 = cv1.sqlContext().sql(
                "SELECT Date,Open,PREV_OPEN_PRC " +
                        //", LAG(Open,1) OVER (PARTITION BY input_file_name ORDER BY Date) AS PREV_OPEN " +
                        ",case when (" +
                        "( LAG(PREV_OPEN_PRC,1) OVER (PARTITION BY input_file_name ORDER BY Date) ) - PREV_OPEN_PRC " +
                        ") > 0 THEN 'bigger' ELSE 'small' END  " +
                        "AS PREV_OPEN_PRC_diff " +
                        ", case when PREV_OPEN_PRC < 98 then 'B' when PREV_OPEN_PRC > 102 then 'S' END BUY_SELL_IND" +
                        ", case when PREV_OPEN_PRC < 98 then Open when PREV_OPEN_PRC > 102 then Open   END BUY_SELL_PRC_LAST  " +
                        " FROM cv1 "
        );
        cv2.registerTempTable("cv2");
        cv2.sqlContext().sql(
                "SELECT BUY_SELL_IND,sum(BUY_SELL_PRC_LAST),count(*) from cv2 where BUY_SELL_PRC_LAST is not null " +
                        "group by BUY_SELL_IND"
        ).show();
    }
}
*/
