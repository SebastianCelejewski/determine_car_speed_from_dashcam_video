package pl.sebce.csfdv.gui;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.events.NavigationListener;

import java.io.File;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameDisplay extends JPanel implements NavigationListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    private JLabel label = new JLabel();

    private File[] movieClipFrames;
    private int currentFrameIdx;
    private Project project;

    public FrameDisplay() {
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.CENTER);
        this.addMouseMotionListener(this);
    }

    public void openProject(Project project) {

        this.project = project;
    }

    public void closeProject() {
        project = null;
        movieClipFrames = null;
        currentFrameIdx = 0;
        label.setIcon(null);
        this.repaint();
    }

    public void setFiles(File[] movieClipFrames) {
        this.movieClipFrames = movieClipFrames;
    }

    public void setFrameIdx(int frameIdx) {
        this.currentFrameIdx = frameIdx;
        this.repaint();
    }

    public void repaint() {
        if (movieClipFrames != null) {
            File file = movieClipFrames[currentFrameIdx];
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            label.setIcon(icon);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
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