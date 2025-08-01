import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.collect_list;

public class CVSOrderToSingleColumn {
    public static void main(String atgs[]) throws IOException {
        String inputCSV  = "C:\\Users\\Lenovo\\Downloads\\orders (18).csv";

        SparkSession sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example").master("local")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        Dataset<Row> csv = sparkSession.read().format("csv").option("header","true").option("inferSchema","true")
                .load(inputCSV);

        csv.filter(col("Status").equalTo("COMPLETE")).groupBy("Instrument","Type")
                .agg(collect_list("`Avg. price`"))
                .orderBy("Instrument","Type")

                .show(false);


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
