package pl.sebcel.csfdv.events;

import pl.sebcel.csfdv.domain.Project;

/**
 * Fired when project data is changes, i.e. when FPS or reference point distance is changed or when
 * a movie clip frame is selected or deselected
 */
public class ProjectChanged {

    private Project project;

    public ProjectChanged(Project project) {

        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}