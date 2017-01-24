package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by srobbins on 1/23/17.
 */
public class CsvData {
    Set<String> header = new HashSet<String>();
    Map<String, SAFMetadata> csvRows = new HashMap<String, SAFMetadata>();

    public void addHeader(String headerElement){
        header.add(headerElement);
    }

    public void createRow(String filename, SAFMetadata metadata){
        csvRows.put(filename, metadata);
        for(String header : metadata.getHeaders()){
            addHeader(header);
        }
    }

    public void writeCSV(JTextArea console){
        console.append("header is \n");
        for(String headerField:header){
            console.append(headerField+"||");
        }
        console.append("\n\nRows: \n");
        for(String filename: csvRows.keySet()){
            console.append("\nFilename: "+filename+"\n");
            console.append("Metadata entries: \n");
            Map<String, String> mdValues = csvRows.get(filename).getMetadataValues();
            for(String headerEntry: mdValues.keySet()){
                console.append(headerEntry+": "+mdValues.get(headerEntry)+"\n");
            }
        }

    }
}
