package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by srobbins on 1/8/17.
 */
public class GeneratorGUI extends JFrame {
    private final JButton chooseSourceDirectoryBtn = new JButton("Select source files directory");
    private final JButton generateButton = new JButton("Generate the CSV");
    private final JFileChooser inputDirectoryChooser = new JFileChooser();
    private final JTextField inputDirectoryNameField = new JTextField("", 40);
    private static String inputFileName;
    private JPanel inputPanel = new JPanel();
    private final JPanel statusPanel = new JPanel();
    private final JTextArea console = new JTextArea(20, 50);
    private final JScrollPane scrollPane;
    private final CsvGenerator generator;

    public GeneratorGUI(){
        generator = new CsvGeneratorImpl();
        inputPanel = buildInputPanel();
        add(inputPanel);
        console.setEditable(false);
        console.setLineWrap(true);
        scrollPane = new JScrollPane(console);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        statusPanel.add(scrollPane);
        add(statusPanel, BorderLayout.PAGE_END);

        pack();

    }

    private JPanel buildInputPanel() {
        inputDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        inputPanel.add(chooseSourceDirectoryBtn);
        inputPanel.add(inputDirectoryNameField);
        inputPanel.add(generateButton);

        buildInputListeners();

        return inputPanel;
    }

    private void buildInputListeners() {
        chooseSourceDirectoryBtn.addActionListener
                (
                        new ActionListener()
                        {
                            public void actionPerformed( final ActionEvent e )
                            {
                                if( inputDirectoryChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
                                {
                                    console.append("You have selected this source directory: " + inputDirectoryChooser.getSelectedFile().getName() + "\n");
                                    inputFileName = inputDirectoryChooser.getSelectedFile().getAbsolutePath();
                                    inputDirectoryNameField.setText(inputFileName);
                                }
                            }
                        }
                );
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    generator.doGeneration(inputFileName, console);
                } catch (FileNotFoundException e1) {
                    console.append(e1.getMessage());
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    console.append(e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });

    }


}
