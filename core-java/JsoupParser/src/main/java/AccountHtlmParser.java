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

public class AccountHtlmParser {
    static String prefixes= "ns5:,ns6:,frs-core:,uk-frs102:,uk-core:,uk-gaap:,core:,c:,d:,e:,pt:";
    public static void main(String[] args) throws IOException {
        File fw = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\acc_op\\append.psv");
        LinkedHashSet<String> distBalSheetElements = new LinkedHashSet<String>();
        File f = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\sampleAccountFile");
        FileWriter tmp = new FileWriter(fw);
        tmp.write("fileName|CompanyNumber|AccountDate|TURNOVER|CURRENT ASSETS|SHAREHOLDERS FUNDS|FIXED ASSETS|CREDITORS|CALLED UP SHARE CAPITAL|DEBTORS|NET ASSETS |CASH|RETAINED EARNINGS|STOCKS");
        tmp.write("\n");
        tmp.close();
        int NullCounts=0;
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
            record.setCALLED_UP_SHARE_CAPITAL(getBlSheetAttrValue(body,"CalledUpShareCapital,Equity"));

            record.setSHAREHOLDERS_FUNDS(getBlSheetAttrValue(body,"ShareholderFunds,Equity"));
            record.setNET_ASSETS(getBlSheetAttrValue(body,"NetAssetsLiabilities"));
            record.setCASH(getBlSheetAttrValue(body,"CashBankOnHand"));
            record.setRETAINED_EARNINGS(getBlSheetAttrValue(body,"Equity"));

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
    }

    public static Map<String, String> getBlSheetAttrValue(Element e, String attrVals) {
        Map<String,String> var = null;
        var = new HashMap<>();

        for(String attrVal: attrVals.split(","))
            for(String attr : prefixes.split(",")) {
                try {
                    Elements elements = e.getElementsByAttributeValue("name", attr+attrVal);
                    for (Element element : elements) {
                        String contextRef = element.attributes().get("contextRef");
                        String ca = element.text().trim();
                        var.put(contextRef, ca);
                    }

                } catch (IndexOutOfBoundsException ex) {
                    //  System.out.println(ex.getMessage());
                }
            }
        return var;
    }
}
