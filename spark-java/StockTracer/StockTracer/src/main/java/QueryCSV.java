import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.expressions.DayOfMonth;

public class QueryCSV {
    public static void main(String[] args) throws Exception {

        SparkSession sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example").master("local")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();
        Dataset<Row> csv = sparkSession.read().format("csv").option("header","true").option("inferSchema","true")
                .load("D:\\stockData\\RELIANCE.NS.csv");
        csv.show();
        csv.printSchema();
        //get highest


    }
}
