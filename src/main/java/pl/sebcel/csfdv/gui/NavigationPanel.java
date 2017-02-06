package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.events.FrameSelected;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ProjectOpened;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@Singleton
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
    private int currentFrameIdx = 0;

    @Inject
    private Event<FrameSelected> frameSelectedEvent;

    @PostConstruct
    public void initialize() {
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

    public void openProject(@Observes ProjectOpened projectOpened) {
        this.numberOfFrames = projectOpened.getFiles().length;
        this.frameInfoLabel.setText("Frame " + currentFrameIdx + " of " + numberOfFrames);
        enableAllComponents();
    }

    public void closeProject(@Observes ProjectClosed projectClosed) {
        this.numberOfFrames = 0;
        setFrameIdx(0);
        disableAllComponents();
    }

    public void setFrameIdx(@Observes FrameSelected frameSelected) {
        setFrameIdxInternal(frameSelected.getFrameIdx());
    }

    private void setFrameIdx(Integer frameIdx) {
        setFrameIdxInternal(frameIdx);
        frameSelectedEvent.fire(new FrameSelected(frameIdx));
    }

    private void setFrameIdxInternal(Integer frameIdx) {
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