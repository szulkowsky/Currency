package bank;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Szymon Szulkowski
 * Project Name: Zadanie Rekrutacyjne Java
 * Created for: Currenda Sp. z o.o.
 * Parser Module
 */
public class Parser {
    private URL url = null;
    private String code = null;

    public Parser(String codeName, String beginningDate, String endingDate, long diff){
        if(diff > 93){
            System.out.println("Too many days in a request, the maximum number id 93, try again");
            return;
        }

        try {
            String urlString = "http://api.nbp.pl/api/exchangerates/rates/c/";
            url = new URL(urlString + codeName + "/"+ beginningDate + "/" + endingDate + "/?format=xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Url Error, Check URL");
            System.exit(1);
        }
    }

    public TwoFloatStruct[] getRates() throws ParserConfigurationException {
        TwoFloatStruct[] rates = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try {
            doc = db.parse(url.openStream());
        } catch (SAXException | IOException e) {
            System.out.println("Probably wrong currency code, try again");
            return null;
        }

        if(doc != null) {
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Bid");
            rates = new  TwoFloatStruct[nodeList.getLength()];

            for (int i = 0; i<nodeList.getLength(); i++) {
                rates[i] = new TwoFloatStruct();
                Node node = nodeList.item(i);

                String bidPrice = node.getFirstChild().getNodeValue();
                rates[i].bid = Float.parseFloat(bidPrice);
            }

            nodeList = doc.getElementsByTagName("Ask");

            for (int i = 0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String askPrice = node.getFirstChild().getNodeValue();
                rates[i].ask = Float.parseFloat(askPrice);
            }
        }
        else {
            System.out.println("Probably wrong currency code, try again");
            return null;
        }

        return rates;


    }
}
