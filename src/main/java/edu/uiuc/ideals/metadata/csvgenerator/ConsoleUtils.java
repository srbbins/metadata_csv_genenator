package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by srobbins on 1/28/17.
 */
public class ConsoleUtils {
    public static void printExceptionInfo(Exception e, JTextArea console) {
        console.append(e.getMessage());
        printStackTrace(e, console);
    }

    public static void printTransformExceptionInfo(TransformerException e, JTextArea console){
        console.append(e.getMessageAndLocation());
        printStackTrace(e, console);
    }

    public static void printStackTrace(Exception e, JTextArea console) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        console.append(pw.toString());
    }
}
