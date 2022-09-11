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

public class JsoupParser {
    public static void main(String[] args) throws IOException {
        File fw = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\acc_op\\append.psv");
        LinkedHashSet<String> distBalSheetElements = new LinkedHashSet<String>();
        File f = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\sampleAccountFile");
        FileWriter tmp = new FileWriter(fw);
        tmp.write("fileName|CompanyNumber|AccountDate|TURNOVER|CURRENT ASSETS|SHAREHOLDERS FUNDS|FIXED ASSETS|CREDITORS|CALLED UP SHARE CAPITAL|DEBTORS|NET ASSETS |CASH|RETAINED EARNINGS|STOCKS");
        tmp.write("\n");
        tmp.close();
        ArrayList<AccountPojo> accountRecords= new ArrayList<>();
        for (File file : f.listFiles()) {
            if(file.isDirectory()) continue;
            FileWriter fr = new FileWriter(fw, true);
            BufferedWriter br = new BufferedWriter(fr);
            AccountPojo record = new AccountPojo();
            String fileName = file.getName();

            System.out.println(fileName);

            String s1 = fileName.substring(0, fileName.lastIndexOf("."));
            String accountDate = s1.substring(s1.lastIndexOf("_") + 1);
            String s2 = s1.substring(0, s1.lastIndexOf("_"));
            String companyNumber= s2.substring(s2.lastIndexOf("_") + 1);
            record.setAccountDate(accountDate);
            record.setCompanyNumber(companyNumber);

            String htmlString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Document doc = Jsoup.parse(htmlString);
            Element body = doc.body();
            
            record.setCURRENT_ASSETS(getBlSheetAttrValue(body,"CurrentAssets"));
            record.setFIXED_ASSETS(getBlSheetAttrValue(body,"FixedAssets"));//
            record.setSTOCKS(getBlSheetAttrValue(body,"TotalInventories"));
            record.setDEBTORS(getBlSheetAttrValue(body,"Debtors"));
            record.setCREDITORS(getBlSheetAttrValue(body,"Creditors"));
            record.setCALLED_UP_SHARE_CAPITAL(getBlSheetAttrValue(body,"Equity"));

            record.setSHAREHOLDERS_FUNDS(getBlSheetAttrValue(body,"Equity"));
            record.setNET_ASSETS(getBlSheetAttrValue(body,"NetAssetsLiabilities"));
            record.setCASH(getBlSheetAttrValue(body,"CashBankOnHand"));
            record.setRETAINED_EARNINGS(getBlSheetAttrValue(body,"Equity"));




            String regNumber = getRegNumber(body);
            String stsmtForYr = getstsmtForYr(body);
            String stsmtFor = getstsmtFor(body);
            String accountants = getAccountants(body);
            // Balance sheet
            LinkedHashMap BalanceSheet = new LinkedHashMap();
            getBalanceSheet(distBalSheetElements, doc, BalanceSheet);
            if( regNumber!=null && BalanceSheet.size() > 30 ) {
                System.out.println(file.getAbsolutePath());
                System.out.println(BalanceSheet.size());
                System.out.println("regNumber=" + regNumber);
                System.out.println("stsmtForYr=" + stsmtForYr);
                System.out.println("stsmtFor=" + stsmtFor);
                System.out.println("accountants=" + accountants);
            }
            accountRecords.add(record);
            //fileName,CompanyNumber,AccountDate,TURNOVER,CURRENT ASSETS,SHAREHOLDERS FUNDS,FIXED ASSETS,
            // CREDITORS,CALLED UP SHARE CAPITAL,DEBTORS,NET ASSETS ,CASH,RETAINED EARNINGS,STOCKS
            br.write(fileName+"|"+record.getCompanyNumber()+"|"+record.getAccountDate()+"|"+
                    record.getTURNOVER()+"|"+record.getCURRENT_ASSETS()+"|"+record.getSHAREHOLDERS_FUNDS()+"|"+
                    record.getFIXED_ASSETS()+"|"+record.getCREDITORS()+"|"+record.getCALLED_UP_SHARE_CAPITAL()+"|"+
                    record.getDEBTORS()+"|"+record.getNET_ASSETS()+"|"+record.getCASH()+"|"+
                    record.getRETAINED_EARNINGS()+"|"+record.getSTOCKS());
            br.newLine();
            br.close();
            fr.close();
        }
        // System.out.println(BalanceSheet);
      /*  for (String s : distBalSheetElements)
            System.out.println(s);*/

    }

    private static void getBalanceSheet(LinkedHashSet<String> distBalSheetElements, Document doc, LinkedHashMap balanceSheet) {
        try {

            Node bl = getBalanceSheetNode(doc);

            int index = 0;
            if(bl !=null)
            for (Node b : bl.childNodes()) {
               Elements trs = Jsoup.parse(b.toString()).select("table tr");

                for (Element tr : trs) {
                    HashMap row = new HashMap<Integer, String>();
                    Elements tds = tr.getElementsByTag("td");

                    int i = 0;
                    for (Element td : tds) {
                        row.put(i, td.text());
                        i++;
                       // System.out.print(" | " + td.text());
                    }
                    String value = tds.get(0).text().replace(",", "").replace("(", "").replace(")", "").replace(".", "").trim().toUpperCase();

                    balanceSheet.put(value, row);
                    if (value != null && StringUtils.isNotEmpty(value) && !value.contains("DIRECTOR") && value.length() < 30 && !StringUtils.isNumeric(value))
                        distBalSheetElements.add(value);
                    index++;
                    //System.out.println("|");
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

    public static Map<String, String> getBlSheetAttrValue(Element e, String attrVals) {
        Map<String,String> var = null;
        var = new HashMap<>();
        String prefixes=  "ns5:,ns6:,frs-core:,uk-core:,uk-gaap:,core:,c:,d:";
        for(String attrVal: attrVals.split(","))
        for(String attr : prefixes.split(",")) {
            try {
                Elements elements = e.getElementsByAttributeValue("name", attr+attrVal);
                for (Element element : elements) {
                    String contextRef = element.attributes().get("contextRef");
                    String ca = element.text().trim();
                    var.put(contextRef, ca);
                }
                // var = e.getElementsByAttributeValue("name", "ns5:CurrentAssets").text().trim();
                // System.out.println("1--- accountants=" + accountants);

            } catch (IndexOutOfBoundsException ex) {
                //  System.out.println(ex.getMessage());
            }
        }
        return var;
    }
}
