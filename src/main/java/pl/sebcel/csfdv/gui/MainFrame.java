package pl.sebcel.csfdv.gui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Singleton
public class MainFrame extends JFrame {

    @Inject
    private MainMenu mainMenu;

    @Inject
    private NavigationPanel navigationPanel;

    @Inject
    private FrameDisplay frameDisplay;

    @Inject
    private DataPanel dataPanel;

    @Inject
    private ResultsPanel resultsPanel;

    @PostConstruct
    public void initialize() {
        this.setTitle("Determine car speed from dashcam video");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(navigationPanel);
        infoPanel.add(dataPanel);

        JSplitPane viewPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        viewPanel.add(frameDisplay);
        viewPanel.add(resultsPanel);
        viewPanel.setDividerLocation(0.5);

        this.setLayout(new BorderLayout());
        this.setJMenuBar(mainMenu);
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(viewPanel, BorderLayout.CENTER);
    }
}