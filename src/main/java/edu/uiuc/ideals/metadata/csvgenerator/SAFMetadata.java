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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by srobbins on 1/19/17.
 */
public class SAFMetadata {
    private NodeList dcNodes;

    private List<DspaceMetadataValue> metadata = new ArrayList<DspaceMetadataValue>();
    private String schema;

    public SAFMetadata(String xmlString) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document document = builder.parse( new InputSource( new StringReader( xmlString ) ) );
        NodeList metadata = XPathAPI.selectNodeList(document, "/dublin_core");
        Node schemaAttr = metadata.item(0).getAttributes().getNamedItem(
                "schema");
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
        buildMetadataMap();
    }

    private void buildMetadataMap(){
        for(int i = 0 ; i < dcNodes.getLength(); i++){
            Node n = dcNodes.item(i);
            metadata.add(new DspaceMetadataValue(schema,
                    XMLUtils.getAttributeValue(n, "element"),
                    XMLUtils.getAttributeValue(n, "qualifier"),
                    XMLUtils.getStringValue(n)
            ));
        }
    }

    public List<String> getHeaders(){
        List<String> headers = new ArrayList<String>();
        for(DspaceMetadataValue metadatum:metadata){
            headers.add(metadatum.getLabel());
        }
        return headers;

    }
    public Map<String, String> getMetadataValues(){
        Map<String, String> metadataValues = new HashMap<String, String>();
        for(DspaceMetadataValue metadatum: metadata){
            if(metadataValues.containsKey(metadatum.getLabel())){
                metadataValues.put(metadatum.getLabel(),
                        metadataValues.get(metadatum.getLabel())+"||"+metadatum.getValue());
            } else {
                metadataValues.put(metadatum.getLabel(), metadatum.getValue());
            }
        }
        return metadataValues;
    }

    public NodeList getDcNodes(){
        return dcNodes;
    }
    private class DspaceMetadataValue
    {
        private String schema;
        private String element;
        private String qualifier;
        private String textValue;
        public DspaceMetadataValue(String schema, String element, String qualifier, String textValue){
              this.schema = schema;
              this.element = element;
              this.qualifier = qualifier;
              this.textValue = textValue;
        }
        public String getLabel(){
            return schema+"."+element+"."+qualifier;
        }
        public String getValue(){
            return textValue;
        }
    }
}
