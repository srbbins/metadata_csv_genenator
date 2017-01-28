package edu.uiuc.ideals.metadata.csvgenerator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.swing.*;
import java.io.*;
import java.util.*;

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
        writeOut(console);

    }
    private void writeOut(JTextArea console){
        PrintWriter printerStream = null;
        try {
            printerStream = new PrintWriter("metadata.csv", "UTF-8");
        } catch (FileNotFoundException e) {
            console.append(e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            console.append(e.getMessage());
            e.printStackTrace();
        }
        try {
            List<String> headerList = new ArrayList<String>(header);
            headerList.add(0, "bundle:ORIGINAL");
            String[] headerArray = headerList.toArray(new String[header.size()]);

           CSVPrinter printer = CSVFormat.DEFAULT.withHeader(headerArray).print(printerStream);
           for(String filename : csvRows.keySet()) {

               printer.printRecord(csvRows.get(filename).getRecord(filename, headerArray));
               printer.flush();
           }
           printer.close();

        } catch (IOException e) {
            ConsoleUtils.printExceptionInfo(e ,console);
        }
    }
}
