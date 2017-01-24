package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by srobbins on 1/15/17.
 */
public class CsvGeneratorImpl implements CsvGenerator{

    private static CSVPrinter printer = null;
    private JTextArea console;
    //private final Iterable<CSVRecord> records;
    private List<SAFMetadata> SAFList= new ArrayList<SAFMetadata>();
    private CsvData csvData = new CsvData();

    public void doGeneration(String inputDirectory, final JTextArea console) {
        this.console = console;
        final Path inputDirectoryObj  = Paths.get(inputDirectory);
        SimpleFileVisitor<Path> traverser = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
                console.append(file.toFile().getAbsolutePath()+"\n");
                if(file.toFile().getAbsolutePath().endsWith(".pdf")) {
                    String ID = getCatalodIdFromFilename(file.toFile().getAbsolutePath());
                    console.append(ID);
                    MarcRecord record = new MarcRecord();
                    try {
                        SAFMetadata metadata = new SAFMetadata(record.retrieve(ID));
                        SAFList.add(metadata);
                        csvData.createRow(inputDirectoryObj.relativize(file).toString(),metadata);
                        csvData.writeCSV(console);

                    } catch (TransformerException e) {
                        console.append(e.getMessageAndLocation()+"\n");
                        e.printStackTrace();
                    } catch (SAXException e) {
                        console.append(e.getMessage()+"\n");
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        console.append(e.getMessage()+"\n");
                        e.printStackTrace();
                    }
                }
                //showDSpaceMetadata();
                return FileVisitResult.CONTINUE;
            }
        };
        if(!(inputDirectoryObj.toFile().exists() && inputDirectoryObj.toFile().isDirectory()))
        {
            console.append("Source file directory " + inputDirectoryObj+ " is not a readable directory.\n");

        } else {
            try {
                Files.walkFileTree(inputDirectoryObj, traverser);
            } catch (IOException e) {
                console.append(e.getMessage());
            }
        }


    }
    private void showDSpaceMetadata(){
        for(SAFMetadata metadata:SAFList){
            console.append("Processing new DSpace metadata xml.\n");
            NodeList nodes = metadata.getDcNodes();
            for(int i = 0 ; i < nodes.getLength(); i++){
                Node n = nodes.item(i);
                console.append("node element is "+XMLUtils.getAttributeValue(n, "element")+"\n");
                console.append("node qualifier is "+XMLUtils.getAttributeValue(n, "qualifier")+"\n");
                console.append("node value is "+XMLUtils.getStringValue(n)+"\n\n");
            }
            console.append("\n");
        }
    }

    private String getCatalodIdFromFilename(String filename) {
        int lastIndex=filename.split("/").length-1;
        String name = filename.split("/")[lastIndex];

        return name.split("\\.")[0];
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
            printer = CSVFormat.DEFAULT.withHeader("metadata_value_id", "text_value", "item_id", "metadata_field_id").print(printerStream);
        } catch (IOException e) {
            console.append(e.getMessage());
            e.printStackTrace();
        }

    }
}
