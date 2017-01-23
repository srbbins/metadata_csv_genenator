package edu.uiuc.ideals.metadata.csvgenerator;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by srobbins on 1/23/17.
 * Copied from DSpace ItemImport class
 */
public class XMLUtils {
    // XML utility methods
    /**
     * Lookup an attribute from a DOM node.
     * @param n
     * @param name
     * @return
     */
    public static String getAttributeValue(Node n, String name)
    {
        NamedNodeMap nm = n.getAttributes();

        for (int i = 0; i < nm.getLength(); i++)
        {
            Node node = nm.item(i);

            if (name.equals(node.getNodeName()))
            {
                return node.getNodeValue();
            }
        }

        return "";
    }


    /**
     * Return the String value of a Node.
     * @param node
     * @return
     */
    public static String getStringValue(Node node)
    {
        String value = node.getNodeValue();

        if (node.hasChildNodes())
        {
            Node first = node.getFirstChild();

            if (first.getNodeType() == Node.TEXT_NODE)
            {
                return first.getNodeValue();
            }
        }

        return value;
    }
}
