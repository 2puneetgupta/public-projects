import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadStockData {

    public List<Stock> processInputFile(String inputFilePath) {

        List<Stock> inputList = new ArrayList<Stock>();

        try{

            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

            // skip the header of the csv
            inputList = br.lines().skip(1).filter(x -> (! x.contains("null"))  && x.split(",").length > 6 )
                    .map(x -> {

                String[] split = x.split(",");

                return new Stock(inputF.getName(),inputF.getName(),split);

            }).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
          System.out.println(e);
        }

        return inputList ;
    }

}
