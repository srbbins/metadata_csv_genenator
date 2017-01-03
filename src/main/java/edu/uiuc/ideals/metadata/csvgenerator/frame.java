package edu.uiuc.ideals.metadata.csvgenerator;

import java.awt.*;
import javax.swing.*;
/**
 * Created by srobbins on 1/2/17.
 */
public class frame {


    /**
     * @version 1.32 2007-06-12
     * @author Cay Horstmann
     */

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                SimpleFrame frame = new SimpleFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
class SimpleFrame extends JFrame
  {
      private static final int DEFAULT_WIDTH = 300;
      private static final int DEFAULT_HEIGHT = 200;

      public SimpleFrame()
      {
          setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      }
  }
