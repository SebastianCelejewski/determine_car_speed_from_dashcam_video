package pl.sebce.csfdv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame(NavigationPanel navigationPanel, FrameDisplay frameDisplay, MainMenu mainMenu) {
        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(navigationPanel);

        this.setLayout(new BorderLayout());
        this.setJMenuBar(mainMenu);

        this.add(frameDisplay, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.NORTH);

        this.setTitle("KOD Counter");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }


}