import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FindIFExists {
    public static void main(String args[]) throws IOException {
        File f = new File("C:\\Users\\Lenovo\\Downloads\\root\\scripts\\puneet\\apps\\downloads\\accountzip\\unzipped\\Accounts_Bulk_Data-2020-03-16.zip");
        int count=0;
        for (File file : f.listFiles()) {
            if(file.isFile() ) {
                String s = FileUtils.readFileToString(file);
                if (s.contains("Fixed assets"))
                count++;
            Document doc = Jsoup.parse(s);
            Element body = doc.body();
                String profit_and_loss_account = getValuesFromText(body, "PROFIT AND LOSS ACCOUNT");
                System.out.println("profit_and_loss_account--"+profit_and_loss_account);
            }
        }
       // System.out.println(count);
    }

    public static String getValuesFromText(Element body,String searchString){
        Elements tr = body.getElementsByTag("tr");
        for (Element row :tr)
            if(row.text().toUpperCase().startsWith(searchString)) {
        String replace = row.text().toUpperCase().replace(searchString,  "");
        for( String str : replace.split(" ")){
            str= str.replaceAll(",","");
            System.out.println("checking--"+str);
            if(StringUtils.isNoneEmpty(str) && Pattern.matches("[(]?[0-9]*[)]?", str))
                return str;
        }
    }
        return null;
    }
}
