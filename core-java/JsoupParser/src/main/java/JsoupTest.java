import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
        File fw = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\acc_op\\append.csv");
        //LinkedHashSet<String> distBalSheetElements = new LinkedHashSet<String>();
        LinkedHashMap<String ,Integer> distBalSheetElements = new LinkedHashMap<>();
        File f = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\sampleAccountFile");

        for (File file : f.listFiles()) {
            if(file.isDirectory()) continue;
            FileWriter fr = new FileWriter(fw, true);
            BufferedWriter br = new BufferedWriter(fr);
            System.out.println(file.getAbsolutePath());
            String htmlString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            Document doc = Jsoup.parse(htmlString);
            Element body = doc.body();
            String regNumber = getRegNumber(body);

            System.out.println("regNumber=" + regNumber);
            String stsmtForYr = getstsmtForYr(body);
            System.out.println("stsmtForYr=" + stsmtForYr);

            String stsmtFor = getstsmtFor(body);
            System.out.println("stsmtFor=" + stsmtFor);
            String accountants = getAccountants(body);
            System.out.println("accountants=" + accountants);
            // Balance sheet
            LinkedHashMap BalanceSheet = new LinkedHashMap();
            getBalanceSheet(distBalSheetElements, doc, BalanceSheet);

            br.write(file.getName()+","+regNumber+","+stsmtFor+","+stsmtForYr+","+accountants+","+BalanceSheet.size());
            br.newLine();
            br.close();
            fr.close();
        }
        // System.out.println(distBalSheetElements);
        for ( Map.Entry s : distBalSheetElements.entrySet()) {
            System.out.println(s.getKey()+"=>>"+s.getValue());
        }


    }

    private static void getBalanceSheet(LinkedHashMap<String,Integer> distBalSheetElements, Document doc, LinkedHashMap balanceSheet) {
        try {

            Node bl = getBalanceSheetNode(doc);

            int index = 0;
            for (Node b : bl.childNodes()) {
                Elements trs = Jsoup.parse(b.toString()).select("table tr");

                for (Element tr : trs) {
                    HashMap row = new HashMap<Integer, String>();
                    Elements tds = tr.getElementsByTag("td");

                    int i = 0;
                    for (Element td : tds) {
                        row.put(i, td.text());
                        i++;
                       System.out.print(" | " + td.text());
                    }
                    String value = tds.get(0).text().replace(",", "").replace("(", "").replace(")", "").replace(".", "").trim().toUpperCase();

                    balanceSheet.put(value, row);
                    if (value != null && StringUtils.isNotEmpty(value) && !value.contains("DIRECTOR") && value.length() < 30 && !StringUtils.isNumeric(value)) {
                        if (distBalSheetElements.containsKey(value)) {
                            distBalSheetElements.put(value, distBalSheetElements.get(value) + 1);
                        } else {
                            distBalSheetElements.put(value, 1);
                        }
                    }
                    index++;
                   System.out.println("|");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getBalanceSheetNode(Document doc) {
        Node BalanaceSheetNode =null;
        try {
            BalanaceSheetNode = doc.getElementsMatchingOwnText("CURRENT ASSETS").parents().first().parentNode().parent().parent().parent();
        }  catch (NullPointerException ex) {
            // System.out.println(ex.getMessage());
        }
        if (BalanaceSheetNode == null) {
            try {
                BalanaceSheetNode = doc.getElementsMatchingOwnText("Current assets").parents().first().parentNode().parent().parent().parent();
            } catch (IndexOutOfBoundsException ex) {
                // System.out.println(ex);
            }
        }
        return BalanaceSheetNode;
    }

    public static String getRegNumber(Element e) {
        String regNumber = null;
        try {
            regNumber = e.getElementsByAttributeValue("name", "uk-bus:UKCompaniesHouseRegisteredNumber").get(0).childNodes().get(0)
                    .toString().replace("\n", "").trim();
            //System.out.println("1--- regNumber=" + regNumber);
        } catch (IndexOutOfBoundsException ex) {
            // System.out.println(ex.getMessage());
        }
        if (StringUtils.isEmpty(regNumber)) {
            try {
                regNumber = e.getElementsByAttributeValue("name", "ns10:UKCompaniesHouseRegisteredNumber").get(0).childNodes().get(0)
                        .toString().replace("\n", "").trim();
                //System.out.println("2--regNumber=" + regNumber);
            } catch (IndexOutOfBoundsException ex) {
                // System.out.println(ex);
            }
        }
        return regNumber;
    }

    public static String getstsmtForYr(Element e) {
        String stsmtForYr = null;
        try {
            stsmtForYr = e.getElementsByAttributeValue("name", "uk-bus:EndDateForPeriodCoveredByReport").get(0).text().replace("FINANCIAL STATEMENTS FOR THE YEAR ENDED", "").trim();

            // System.out.println("1--- stsmtForYr=" + stsmtForYr);
        } catch (IndexOutOfBoundsException ex) {
            //  System.out.println(ex.getMessage());
        }
        if (StringUtils.isEmpty(stsmtForYr)) {
            try {
                stsmtForYr = e.getElementsByAttributeValue("name", "ns10:ReportTitle").get(0).getElementsByTag("span").text().replace("FINANCIAL STATEMENTS FOR THE YEAR ENDED", "").trim();
                //  System.out.println("2--stsmtForYr=" + stsmtForYr);
            } catch (IndexOutOfBoundsException ex) {
                // System.out.println(ex);
            }
        }
        return stsmtForYr;
    }

    public static String getstsmtFor(Element e) {
        String stsmtFor = null;
        try {
            stsmtFor = e.getElementsByAttributeValue("name", "uk-bus:EntityCurrentLegalOrRegisteredName").get(0).text().trim();

            //System.out.println("1--- stsmtFor=" + stsmtFor);
        } catch (IndexOutOfBoundsException ex) {
            // System.out.println(ex.getMessage());
        }
        if (StringUtils.isEmpty(stsmtFor)) {
            try {
                stsmtFor = e.getElementsByAttributeValue("name", "ns10:EntityCurrentLegalOrRegisteredName").get(0).getElementsByTag("span").text().trim();
                // System.out.println("2--stsmtFor=" + stsmtFor);
            } catch (IndexOutOfBoundsException ex) {
                //  System.out.println(ex);
            }
        }
        return stsmtFor;
    }

    public static String getAccountants(Element e) {
        String accountants = null;
        try {
            accountants = e.getElementsByAttributeValue("name", "ns10:NameEntityAccountants").text().trim();

            // System.out.println("1--- accountants=" + accountants);
        } catch (IndexOutOfBoundsException ex) {
            //  System.out.println(ex.getMessage());
        }

        return accountants;
    }
}
