package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by srobbins on 1/15/17.
 */
public interface CsvGenerator {
    public void doGeneration(String inputDirectory, JTextArea console) throws FileNotFoundException, UnsupportedEncodingException;

}
