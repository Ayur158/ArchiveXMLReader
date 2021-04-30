package reader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReadXmlDomParser {
    public static int errorCounter;
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\a.chimbeev\\Desktop\\cSV\\output.csv");
        writer.append("created");
        writer.append(';');
        writer.append("uid");
        writer.append(';');
        writer.append("organization");
        writer.append('\n');
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        File dir = new File("C:\\Users\\a.chimbeev\\Desktop\\XMLS");
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
                String FILENAME = files[i].getAbsolutePath();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(FILENAME));
                Element root = doc.getDocumentElement();
                String prefix = root.getPrefix();
                doc.getDocumentElement().normalize();
                NodeList list = doc.getElementsByTagName(prefix + ":organization");
                String org = list.item(0).getTextContent();
                list = doc.getElementsByTagName(prefix +":header");
                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String uid = element.getAttribute(prefix +":uid");
                        String created = element.getAttribute(prefix +":created");
                        writer.append(created);
                        writer.append(";");
                        writer.append(uid);
                        writer.append(";");
                        writer.append(org);
                        writer.append("\n");
                    }
                }
            } catch (ParserConfigurationException | SAXException | IOException | NullPointerException e) {
                System.out.println(files[i].getAbsolutePath() + " ERROR");
            }
            System.out.println(files[i].getAbsolutePath() + " VALID");
        }
        writer.flush();
        writer.close();
    }
}