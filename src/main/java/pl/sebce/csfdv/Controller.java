package pl.sebce.csfdv;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.gui.FrameDisplay;
import pl.sebce.csfdv.gui.MainFrame;
import pl.sebce.csfdv.gui.MainMenu;
import pl.sebce.csfdv.gui.NavigationPanel;
import pl.sebce.csfdv.utils.FileOperations;

import java.awt.*;
import java.io.File;

public class Controller {

    private Project project;
    private FileOperations fileOperations;

    private NavigationPanel navigationPanel;
    private FrameDisplay frameDisplay;
    private MainMenu mainMenu;
    private MainFrame mainFrame;

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        fileOperations = new FileOperations();

        navigationPanel = new NavigationPanel();
        frameDisplay = new FrameDisplay();
        mainMenu = new MainMenu();
        navigationPanel.addNavigationListener(frameDisplay);
        mainMenu.setController(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new MainFrame(navigationPanel, frameDisplay, mainMenu);
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

    private void initializeGUI(File[] files) {
        navigationPanel.setNumberOfFrames(files.length);
        navigationPanel.setProject(project);
        frameDisplay.setProject(project);
        frameDisplay.setFiles(files);
        frameDisplay.setFrameIdx(0);

        mainFrame.repaint();
    }
}