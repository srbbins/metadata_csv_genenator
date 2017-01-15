package edu.uiuc.ideals.metadata.csvgenerator;

import java.awt.*;

/**
 * Created by srobbins on 1/8/17.
 */
public class App {
    //static GeneratorGUI gui = new GeneratorGUI();

    public static void main( String[] args )
    {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                GeneratorGUI gui = new GeneratorGUI();
                gui.setSize(800, 640);
                gui.setResizable(false);
                gui.setVisible(true);
            }
        });
    }
}
