package pl.sebce.csfdv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame(MainMenu mainMenu, NavigationPanel navigationPanel, FrameDisplay frameDisplay, DataPanel dataPanel, ResultsPanel resultsPanel) {
        JPanel infoPanel = new JPanel();
        JPanel viewPanel = new JPanel();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(navigationPanel);
        infoPanel.add(dataPanel);

        viewPanel.setLayout(new GridBagLayout());
        viewPanel.add(frameDisplay, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        viewPanel.add(resultsPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));

        this.setLayout(new BorderLayout());
        this.setJMenuBar(mainMenu);

        this.add(infoPanel, BorderLayout.NORTH);
        this.add(viewPanel, BorderLayout.CENTER);

        this.setTitle("KOD Counter");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}