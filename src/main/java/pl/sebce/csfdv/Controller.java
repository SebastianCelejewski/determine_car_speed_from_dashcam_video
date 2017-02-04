package pl.sebce.csfdv;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.gui.*;
import pl.sebce.csfdv.utils.FileOperations;

import java.awt.*;
import java.io.File;

public class Controller {

    private Project project;
    private FileOperations fileOperations;

    private NavigationPanel navigationPanel;
    private FrameDisplay frameDisplay;
    private DataPanel dataPanel;
    private MainMenu mainMenu;
    private MainFrame mainFrame;
    private ResultsPanel resultsPanel;

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        fileOperations = new FileOperations();

        navigationPanel = new NavigationPanel();
        frameDisplay = new FrameDisplay();
        mainMenu = new MainMenu();
        dataPanel = new DataPanel();
        resultsPanel = new ResultsPanel();
        navigationPanel.addNavigationListener(frameDisplay);
        navigationPanel.addNavigationListener(dataPanel);
        mainMenu.setController(this);
        dataPanel.addProjectDataListener(resultsPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new MainFrame(mainMenu, navigationPanel, frameDisplay, dataPanel, resultsPanel);
        mainFrame.setBounds(0, 0, screenSize.width, screenSize.height);
        mainFrame.setVisible(true);
    }

    public void initProject(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        project = new Project();
        project.setImagesDirectory(directoryPath);

        initializeGUI(files);
    }

    public void openProject(File file) {
        project = fileOperations.loadProject(file);

        File directory = new File(project.getImagesDirectory());
        File[] files = directory.listFiles();

        initializeGUI(files);
    }

    public void saveProject(File file) {
        fileOperations.saveProject(file, project);
    }

    public void closeProject() {
        dataPanel.closeProject();
        frameDisplay.closeProject();
        navigationPanel.closeProject();
        resultsPanel.closeProject();
    }

    private void initializeGUI(File[] files) {
        navigationPanel.setNumberOfFrames(files.length);
        project.setNumberOfFrames(files.length);
        navigationPanel.openProject(project);
        frameDisplay.openProject(project);
        frameDisplay.setFiles(files);
        frameDisplay.setFrameIdx(0);
        mainFrame.repaint();
        dataPanel.openProject(project);
        resultsPanel.openProject(project);
    }
}