package pl.sebce.csfdv.gui;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.events.NavigationListener;

import javax.swing.*;
import java.io.File;

public class FrameDisplay extends JPanel implements NavigationListener {

    private Project project;
    private File[] files;
    private int frameIdx;

    public void setProject(Project project) {
        this.project = project;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public void setFrameIdx(int frameIdx) {
        this.frameIdx = frameIdx;
    }
}
