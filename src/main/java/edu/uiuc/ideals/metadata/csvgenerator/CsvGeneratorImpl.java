package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by srobbins on 1/15/17.
 */
public class CsvGeneratorImpl implements CsvGenerator{

    public void doGeneration(String inputDirectory, final JTextArea console) {
        console.append("got here");
        Path inputDirectoryObj  = Paths.get(inputDirectory);
        SimpleFileVisitor<Path> traverser = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                console.append(file.toFile().getAbsolutePath()+"\n");
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
}
