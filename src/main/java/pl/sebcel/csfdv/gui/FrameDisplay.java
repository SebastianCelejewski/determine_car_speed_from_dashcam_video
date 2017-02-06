package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.events.FrameSelected;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ProjectOpened;

import java.awt.*;
import java.io.File;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.*;

/**
 * Displays frames of a dashcam video clip
 */
@Singleton
public class FrameDisplay extends JPanel {

    private JLabel label = new JLabel();

    private File[] movieClipFrames;

    @PostConstruct
    public void initialize() {
        this.setBorder(BorderFactory.createTitledBorder("Frame Display"));
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.CENTER);
    }

    public void openProject(@Observes ProjectOpened projectOpened) {
        this.movieClipFrames = projectOpened.getFiles();
        this.setFrameIdx(0);
    }

    public void closeProject(@Observes ProjectClosed projectClosed) {
        movieClipFrames = null;
        label.setIcon(null);
        this.repaint();
    }

    public void setFrameIdx(@Observes FrameSelected frameSelected) {
        this.setFrameIdx(frameSelected.getFrameIdx());
    }

    public void setFrameIdx(int frameIdx) {
        if (movieClipFrames != null) {
            File file = movieClipFrames[frameIdx];
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            label.setIcon(icon);
            label.setSize(icon.getIconWidth(), icon.getIconHeight());
        }

        this.repaint();
    }
}