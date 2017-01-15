package edu.uiuc.ideals.metadata.csvgenerator;

import javax.swing.*;

/**
 * Created by srobbins on 1/10/17.
 */
public class InputDirectoryComponent extends JComponent{
    private final JButton chooseSourceDirectoryBtn = new JButton("Select source files directory");
    private final JFileChooser inputFileChooser = new JFileChooser(".");
    private final JPanel inputPanel;

    public InputDirectoryComponent() {
        inputPanel = new JPanel();
        inputPanel.add(chooseSourceDirectoryBtn);
        inputPanel.add(inputFileChooser);
        add(inputPanel);


    }

}
