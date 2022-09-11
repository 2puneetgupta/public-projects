import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {
    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile  = new File("C:\\Users\\Lenovo\\Desktop\\up latest docs\\sampleAccountFile\\Prod223_2586_11787315_20200131.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();
        String regNumber = getRegNumber(doc);
        System.out.println("regNumber=" + regNumber);
        System.out.println("regNumber=" + regNumber);
       // String stsmtForYr = getstsmtForYr(body);
       // System.out.println("stsmtForYr=" + stsmtForYr);

        String stsmtFor = getstsmtFor(doc);
        System.out.println("stsmtFor=" + stsmtFor);
       // String accountants = getAccountants(body);
       // System.out.println("accountants=" + accountants);
    }

    private static String getRegNumber(Document doc) {
        return doc.getElementsByTagName("ae:CompaniesHouseRegisteredNumber").item(0).getTextContent();
    }
    public static String getstsmtFor(Document doc) {
        return doc.getElementsByTagName("gc:EntityCurrentLegalName").item(0).getTextContent();
    }
}
