package edu.uiuc.ideals.metadata.csvgenerator;

import com.sun.org.apache.xpath.internal.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by srobbins on 1/19/17.
 */
public class SAFMetadata {
    private NodeList dcNodes;

    public SAFMetadata(String xmlString) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document document = builder.parse( new InputSource( new StringReader( xmlString ) ) );
        NodeList metadata = XPathAPI.selectNodeList(document, "/dublin_core");
        Node schemaAttr = metadata.item(0).getAttributes().getNamedItem(
                "schema");
        String schema;
        if (schemaAttr == null)
        {
            schema = "dc";
        }
        else
        {
            schema = schemaAttr.getNodeValue();
        }
        dcNodes = XPathAPI.selectNodeList(document,
                "/dublin_core/dcvalue");

    }

    public NodeList getDcNodes(){
        return dcNodes;
    }

}
