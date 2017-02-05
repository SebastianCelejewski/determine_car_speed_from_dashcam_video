package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.events.FrameSelected;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ProjectOpened;

import java.io.File;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@Singleton
public class FrameDisplay extends JPanel implements MouseMotionListener {

    private static final long serialVersionUID = 1L;

    private JLabel label = new JLabel();

    private File[] movieClipFrames;

    private int currentFrameIdx;

    @PostConstruct
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.CENTER);
        this.addMouseMotionListener(this);
    }

    public void openProject(@Observes ProjectOpened projectOpened) {
        this.movieClipFrames = projectOpened.getFiles();
        this.setFrameIdx(0);
    }

    public void closeProject(@Observes ProjectClosed projectClosed) {
        movieClipFrames = null;
        currentFrameIdx = 0;
        label.setIcon(null);
        this.repaint();
    }

    public void setFrameIdx(@Observes FrameSelected frameSelected) {
        this.setFrameIdx(frameSelected.getFrameIdx());
    }

    public void setFrameIdx(int frameIdx) {
        this.currentFrameIdx = frameIdx;
        this.repaint();
    }

    public void repaint() {
        super.repaint();
        if (movieClipFrames != null) {
            File file = movieClipFrames[currentFrameIdx];
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            label.setIcon(icon);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.repaint();
    }
}