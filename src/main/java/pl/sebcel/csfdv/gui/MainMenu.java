package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.Controller;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ProjectOpened;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.io.File;

/**
 * Menu for creation, opening, saving, and closing projects
 */
@Singleton
public class MainMenu extends JMenuBar {

    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuFileNew = new JMenuItem("New");
    private JMenuItem menuFileOpen = new JMenuItem("Open");
    private JMenuItem menuFileSave = new JMenuItem("Save");
    private JMenuItem menuFileSaveAs = new JMenuItem("Save As");
    private JMenuItem menuFileClose = new JMenuItem("Close");

    private String projectFilename;

    @Inject
    private Controller controller;

    @PostConstruct
    public void initialize() {
        menuFile.add(menuFileNew);
        menuFile.add(menuFileOpen);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileSaveAs);
        menuFile.add(menuFileClose);

        this.add(menuFile);

        menuFileNew.addActionListener(e -> newProject());
        menuFileSaveAs.addActionListener(e -> saveProjectAs());
        menuFileSave.addActionListener(e -> saveProject());
        menuFileOpen.addActionListener(e -> openProject());
        menuFileClose.addActionListener(e -> closeProject());
    }

    private void newProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Select directory containing set of images");
        if (fileChooser.showDialog(MainMenu.this, "Select images directory") == JFileChooser.APPROVE_OPTION) {
            String selectedDirectory = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                controller.initProject(selectedDirectory);
                projectFilename = null;
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error while creating new project", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveProjectAs() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(MainMenu.this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                controller.saveProject(file);
                projectFilename = file.getAbsolutePath();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error while saving a file", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveProject() {
        if (projectFilename == null) {
            saveProjectAs();
        } else {
            try {
                controller.saveProject(new File(projectFilename));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error while saving a file", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openProject() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(MainMenu.this) == JFileChooser.APPROVE_OPTION) {
            try {
                controller.openProject(fileChooser.getSelectedFile());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error while loading a file", JOptionPane.ERROR_MESSAGE);
            }
            projectFilename = fileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private void closeProject() {
        projectFilename = null;
        controller.closeProject();
    }
}