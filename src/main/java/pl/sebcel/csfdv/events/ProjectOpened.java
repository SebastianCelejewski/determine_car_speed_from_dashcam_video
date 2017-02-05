package pl.sebcel.csfdv.events;

import pl.sebcel.csfdv.domain.Project;

import java.io.File;

public class ProjectOpened {

    private Project project;

    private File[] files;

    public ProjectOpened(Project project, File[] files) {
        this.project = project;
        this.files = files;
    }

    public Project getProject() {
        return project;
    }

    public File[] getFiles() {
        return files;
    }
}