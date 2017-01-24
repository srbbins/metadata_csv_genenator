package edu.uiuc.ideals.metadata.csvgenerator;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import javax.xml.transform.*;
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
        factory.setURIResolver(new ClasspathResourceURIResolver());
        InputStream inputXSL  = MarcRecord.class.getClassLoader().getResourceAsStream("MARC21slim2IDEALSDC.xsl");
        StreamSource xslStream = new StreamSource(inputXSL);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(dataXML);
        StringWriter writer = new StringWriter();

        StreamResult out = new StreamResult(writer);
        transformer.transform(in, out);
        return writer.toString();
    }

    class ClasspathResourceURIResolver implements URIResolver {
        @Override
        public Source resolve(String href, String base) throws TransformerException {
            return new StreamSource(MarcRecord.class.getClassLoader().getResourceAsStream(href));
        }
    }



}
