package pl.sebcel.csfdv.gui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main application window
 */
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
    private ResultsTable resultsTable;

    @Inject
    private ResultsChart resultsChart;

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

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.X_AXIS));
        viewPanel.add(frameDisplay);
        viewPanel.add(resultsTable);
        viewPanel.add(resultsChart);

        this.setLayout(new BorderLayout());
        this.setJMenuBar(mainMenu);
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(viewPanel, BorderLayout.CENTER);
    }
}