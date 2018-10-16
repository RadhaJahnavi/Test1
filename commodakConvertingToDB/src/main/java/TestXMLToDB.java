import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestXMLToDB {
    public static void main(String[] args) {
        Connection connectionobj = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");



            connectionobj = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?autoReconnect=true&useSSL=false", "root", "root");

            connectionobj.createStatement()
                    .executeUpdate("CREATE TABLE books(\n" +
                            "  id varchar(25) not null ,\n" +
                            "  author varchar(50) not null,\n" +
                            "  title varchar(250) not null,\n" +
                            "  genre varchar(25) not null,\n" +
                            "  price varchar(25) not null,\n" +
                            "  publish_date date not null,\n" +
                            "  description text not null\n" +
                            ")");



            File file;
            file = new File("C:/Users/MT1009/IdeaProjects/Project/books.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);




            XPath xpath = XPathFactory.newInstance().newXPath();

            Object res;

            res = xpath.evaluate("C:Users/MT1009/IdeaProjects/Project/Book.xml" ,
                    doc,
                    XPathConstants.NODESET);

            PreparedStatement stmt = connectionobj

                    .prepareStatement("INSERT INTO books(\n" +

                            " id, author, title, genre, price,\n" +

                            " publish_date, description)\n" +

                            "VALUES(?, ?, ?, ?, ?,\n" +

                            " str_to_date(?, '%Y-%m-%d'), ?)");


            Node rootElement = doc.getFirstChild();
            NodeList nlist = rootElement.getChildNodes();

            System.out.println(nlist.getLength());

            for (int i = 0 ; i < nlist.getLength() ; i++) {

                Node node = nlist.item(i);

                List<String> columns = Arrays

                        .asList(getAttributeValue(node, "id"),

                                getTextContent(node, "author"),

                                getTextContent(node, "title"),

                                getTextContent(node, "genre"),

                                getTextContent(node, "price"),

                                getTextContent(node, "publish_date"),

                                getTextContent(node, "description"));

                for (int n = 0 ; n < columns.size() ; n++) {

                    stmt.setString(n+1, columns.get(n));

                }

                stmt.execute();

            }
            stmt.close();
            connectionobj.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private String getAttributeValue(Node node, String attrName) {
        if (!node.hasAttributes()) return "";
        NamedNodeMap nmap = node.getAttributes();
        if (nmap == null) return "";
        Node n = nmap.getNamedItem(attrName);
        if (n == null) return "";
        return n.getNodeValue();
    }

    static private String getTextContent(Node parentNode, String childName) {
        NodeList nlist = parentNode.getChildNodes();
        //System.out.println( nlist.getLength());
        for (int i = 0; i < nlist.getLength(); i++) {
            Node n = nlist.item(i);
            String name = n.getNodeName();
            // System.out.println("hi");
            if (name != null && name.equals(childName)) {



                return n.getTextContent();
            }

        }
        return "";

    }
}