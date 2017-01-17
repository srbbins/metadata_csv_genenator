package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by srobbins on 1/15/17.
 */
public class CsvGeneratorImpl implements CsvGenerator{

    public void doGeneration(String inputDirectory, final JTextArea console) {
        console.append("got here");
        Path inputDirectoryObj  = Paths.get(inputDirectory);
        SimpleFileVisitor<Path> traverser = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
                console.append(file.toFile().getAbsolutePath()+"\n");
                if(file.toFile().getAbsolutePath().endsWith(".pdf")) {
                    String ID = getCatalodIdFromFilename(file.toFile().getAbsolutePath());
                    console.append(ID);
                    MarcRecord record = new MarcRecord();
                    record.retrieve(ID);
                }
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

    private String getCatalodIdFromFilename(String filename) {
        int lastIndex=filename.split("/").length-1;
        String name = filename.split("/")[lastIndex];

        return name.split("\\.")[0];
    }

}
