package pl.sebcel.csfdv;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import pl.sebcel.csfdv.domain.Project;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ProjectOpened;
import pl.sebcel.csfdv.gui.*;
import pl.sebcel.csfdv.utils.FileOperations;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.io.File;
import java.util.List;

@ApplicationScoped
public class Controller {

    @Inject
    private MainFrame mainFrame;

    @Inject
    private FileOperations fileOperations;

    @Inject
    private Event<ProjectOpened> projectOpenedEvent;

    @Inject
    private Event<ProjectClosed> projectClosedEvent;

    private Project project;

    public void initialize(@Observes ContainerInitialized event, @Parameters List<String> parameters) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setBounds(0, 0, screenSize.width, 650);
        mainFrame.setVisible(true);
    }

    public void initProject(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        project = new Project();
        project.setImagesDirectory(directoryPath);

        projectOpenedEvent.fire(new ProjectOpened(project, files));
    }

    public void openProject(File file) {
        project = fileOperations.loadProject(file);

        File directory = new File(project.getImagesDirectory());
        File[] files = directory.listFiles();

        projectOpenedEvent.fire(new ProjectOpened(project, files));
    }

    public void saveProject(File file) {
        fileOperations.saveProject(file, project);
    }

    public void closeProject() {
        projectClosedEvent.fire(new ProjectClosed());
    }
}