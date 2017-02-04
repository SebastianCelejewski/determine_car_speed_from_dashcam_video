package pl.sebce.csfdv.gui;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.events.NavigationListener;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class NavigationPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton firstFrame = new JButton("First");
    private JButton left100 = new JButton("<<<");
    private JButton left10 = new JButton("<<");
    private JButton left1 = new JButton("<");
    private JLabel frameInfoLabel = new JLabel();
    private JButton right1 = new JButton(">");
    private JButton right10 = new JButton(">>");
    private JButton right100 = new JButton(">>>");
    private JButton lastFrame = new JButton("Last");

    private int numberOfFrames = 0;
    private Project project;
    private int currentFrameIdx = 0;

    private Set<NavigationListener> navigationListeners = new HashSet<>();

    public NavigationPanel() {
        setBorder(new TitledBorder("Navigation"));
        frameInfoLabel.setPreferredSize(new Dimension(120, 21));

        add(firstFrame);
        add(left100);
        add(left10);
        add(left1);
        add(frameInfoLabel);
        add(right1);
        add(right10);
        add(right100);
        add(lastFrame);

        firstFrame.addActionListener(e -> setFrameIdx(0));
        left100.addActionListener(e -> moveFrameIdx(-100));
        left10.addActionListener(e -> moveFrameIdx(-10));
        left1.addActionListener(e -> moveFrameIdx(-1));
        right1.addActionListener(e -> moveFrameIdx(1));
        right10.addActionListener(e -> moveFrameIdx(10));
        right100.addActionListener(e -> moveFrameIdx(100));
        lastFrame.addActionListener(e -> setFrameIdx(numberOfFrames - 1));

        disableAllComponents();
    }

    public void openProject(Project project) {
        this.project = project;
        enableAllComponents();
    }

    public void closeProject() {
        project = null;
        setNumberOfFrames(0);
        setFrameIdx(0);
        disableAllComponents();
    }

    public void addNavigationListener(NavigationListener navigationListener) {
        navigationListeners.add(navigationListener);
    }

    public void setNumberOfFrames(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
        this.frameInfoLabel.setText("Frame " + currentFrameIdx + " of " + numberOfFrames);
    }

    private void setFrameIdx(Integer frameIdx) {
        if (frameIdx == null) {
            return;
        }
        currentFrameIdx = frameIdx;
        if (currentFrameIdx < 0) {
            currentFrameIdx = 0;
        }
        if (currentFrameIdx >= numberOfFrames) {
            currentFrameIdx = numberOfFrames - 1;
        }

        for (NavigationListener navigationListener : navigationListeners) {
            navigationListener.setFrameIdx(currentFrameIdx);
        }

        this.frameInfoLabel.setText("Frame " + currentFrameIdx + " of " + numberOfFrames);
        this.repaint();
    }

    private void moveFrameIdx(int delta) {
        setFrameIdx(currentFrameIdx + delta);
    }

    private void enableAllComponents() {
        Arrays.stream(getComponents()).forEach(component -> {component.setEnabled(true);});
    }

    private void disableAllComponents() {
        Arrays.stream(getComponents()).forEach(component -> {component.setEnabled(false);});
    }
}