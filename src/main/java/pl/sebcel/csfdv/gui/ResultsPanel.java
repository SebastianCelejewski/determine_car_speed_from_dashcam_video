package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.domain.Project;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ProjectChanged;
import pl.sebcel.csfdv.events.ProjectOpened;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.*;

@Singleton
public class ResultsPanel extends JPanel implements MouseMotionListener{

    private Project project;
    private java.util.List<Point> data;
    private int numberOfFrames;
    private Point crosshair;
    private int frameIdx;
    private Integer speed;

    @PostConstruct
    public void initialize() {
        this.setBorder(BorderFactory.createEtchedBorder());
        this.addMouseMotionListener(this);
    }

    public void openProject(@Observes ProjectOpened projectOpened) {
        this.project = projectOpened.getProject();
        this.numberOfFrames = projectOpened.getFiles().length;
        recalculate();
    }

    public void closeProject(@Observes ProjectClosed projectClosed) {
        this.project = null;
        this.data = null;
        this.numberOfFrames = 0;
        this.frameIdx = 0;
        this.crosshair = null;
        recalculate();
    }

    public void projectDataChanged(@Observes ProjectChanged projectChanged) {
        recalculate();
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (crosshair != null) {
            g.setColor(Color.green);
            g.drawLine(crosshair.x, 0, crosshair.x, getHeight());
            g.drawLine(0, crosshair.y, getWidth(), crosshair.y);

            g.setColor(Color.black);
            g.drawString("Frame idx: "+frameIdx, 20, 50);
            g.drawString("Car speed: " + (speed != null ? speed : "-") + " km/h", 20, 70);
        }

        if (data != null) {
            for (Point p : data) {
                g.fillOval(fx(p.x), fy(p.y), 5,5);
            }
        }
    }

    private void recalculate() {
        data = new ArrayList<>();

        if (project != null && project.getSelectedFrames() != null && project.getSelectedFrames().size() > 1 && project.getFps() != null && project.getReferencePointDistance() != null) {
            project.getSelectedFrames().sort((x,y) -> (x - y));
            for (int i = 1; i < project.getSelectedFrames().size(); i++) {
                int startIdx = project.getSelectedFrames().get(i-1);
                int endIdx = project.getSelectedFrames().get(i);
                int timeInFrames = endIdx - startIdx;
                double timeInSeconds = (double) timeInFrames / project.getFps();
                double speedInMetersPerSecond = project.getReferencePointDistance() / timeInSeconds;
                int speedInKPH = (int) (speedInMetersPerSecond * 3.6);
                data.add(new Point(endIdx, speedInKPH));
            }
        }
    }

    private int fx(int x) {
        double relX = (double) x / numberOfFrames;
        return (int) (this.getWidth() * relX);
    }

    private int xf(int x) {
        return (int) ((double) x * numberOfFrames / this.getWidth());
    }

    private int fy(int y) {
        double relY = 1 - (double) y / 200;
        return (int) (this.getHeight() * relY);
    }

    private int yf(int y) {
        return (int) (200 * (1 - (double) y / this.getHeight()));
    }

    private Integer getData(int x) {
        if (data.get(0).getX() > x) {
            return null;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getX() > x) {
                return (int) data.get(i).getY();
            }
        }
        return null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (project != null) {
            frameIdx = xf(e.getX());
            int yf = yf(e.getY());
            speed = getData(frameIdx);
            if (speed != null) {
                crosshair = new Point(e.getX(), fy(speed));
            } else {
                crosshair = new Point(e.getX(), -1);
            }
            this.repaint();
        }
    }
}