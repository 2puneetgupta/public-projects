import au.com.bytecode.opencsv.CSVReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CVSOrderToSingleColumn {
    public static void main(String atgs[]) throws IOException {
        String inputCSV  = "C:\\Users\\Lenovo\\Downloads\\orders.csv";

        SparkSession sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example").master("local")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        Dataset<Row> csv = sparkSession.read().format("csv").option("header","true").option("inferSchema","true")
                .load(inputCSV);

        csv.show();

       /* List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(inputCSV));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }

        System.out.println(records);
        Map<String,List<String>> treeMap = new HashMap<>();
        records.forEach( list -> list.get());*/

    }
}
