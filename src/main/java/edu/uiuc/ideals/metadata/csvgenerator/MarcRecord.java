package edu.uiuc.ideals.metadata.csvgenerator;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * Created by srobbins on 1/16/17.
 */
public class MarcRecord {
    public void retrieve(String ID) throws IOException {
        URL url = getURL(ID);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
        System.out.println(body);
    }
    private URL getURL(String ID) throws IOException {
        String prefix = ConfigurationManager.getProperties().getProperty("edu.marc.url.prefix");
        return new URL(prefix+"/"+ID+".marc");
    }

}
