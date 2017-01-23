package edu.uiuc.ideals.metadata.csvgenerator;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by srobbins on 1/16/17.
 */
public class MarcRecord {
    public String retrieve(String ID) throws IOException, TransformerException {
        URL url = getURL(ID);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        return transform(in);
        //String body = IOUtils.toString(in, encoding);
        //System.out.println(body);

    }
    private URL getURL(String ID) throws IOException {
        String prefix = ConfigurationManager.getProperties().getProperty("edu.marc.url.prefix");
        return new URL(prefix+"/"+ID+".marc");
    }

    private String transform(InputStream dataXML) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        InputStream inputXSL  = MarcRecord.class.getClassLoader().getResourceAsStream("MARC21slim2IDEALSDC.xsl");
        StreamSource xslStream = new StreamSource(inputXSL);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(dataXML);
        StringWriter writer = new StringWriter();

        StreamResult out = new StreamResult(writer);
        transformer.transform(in, out);
        System.out.println("The generated xml is:\n" + writer.toString());
        return writer.toString();
    }



}
