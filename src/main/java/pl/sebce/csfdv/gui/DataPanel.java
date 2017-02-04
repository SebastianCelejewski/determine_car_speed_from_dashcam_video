package pl.sebce.csfdv.gui;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.events.NavigationListener;
import pl.sebce.csfdv.events.ProjectDataListener;
import pl.sebce.csfdv.events.ValueChangeListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class DataPanel extends JPanel implements ValueChangeListener, ChangeListener, NavigationListener {

    private Project project;

    private NumberTextField fpsTextField = new NumberTextField();
    private NumberTextField distanceTextField = new NumberTextField();
    private JCheckBox frameSelectedCheckBox = new JCheckBox();

    private int frameIdx;

    private Set<ProjectDataListener> dataListeners = new HashSet<>();

    public DataPanel() {

        fpsTextField.setEnabled(false);
        fpsTextField.setPreferredSize(new Dimension(50, 21));
        fpsTextField.setValueChangeListener(this);

        distanceTextField.setEnabled(false);
        distanceTextField.setPreferredSize(new Dimension(50, 21));
        distanceTextField.setValueChangeListener(this);

        frameSelectedCheckBox.setEnabled(false);
        frameSelectedCheckBox.addChangeListener(this);

        setBorder(new TitledBorder("Data"));

        this.add(new JLabel("FPS:"));
        this.add(fpsTextField);
        this.add(new JLabel("Distance:"));
        this.add(distanceTextField);
        this.add(new JLabel("Frame selected:"));
        this.add(frameSelectedCheckBox);
    }

    public void addProjectDataListener(ProjectDataListener listener) {
        dataListeners.add(listener);
    }

    public void openProject(Project project) {
        this.project = project;
        setValue(fpsTextField, project.getFps());
        setValue(distanceTextField, project.getReferencePointDistance());
        fpsTextField.setEnabled(true);
        distanceTextField.setEnabled(true);
        frameSelectedCheckBox.setEnabled(true);
        notifyListeners();
    }

    public void closeProject() {
        fpsTextField.setText("");
        distanceTextField.setText("");
        fpsTextField.setEnabled(false);
        distanceTextField.setEnabled(false);
        frameSelectedCheckBox.setEnabled(false);
    }

    private void setValue(JTextField textField, Integer value) {
        if (value != null) {
            textField.setText(Integer.toString(value));
        } else {
            textField.setText("");
        }
    }

    @Override
    public void valueChanged(Object source, Integer value) {
        if (source == fpsTextField) {
            project.setFps(value);
        }
        if (source == distanceTextField) {
            project.setReferencePointDistance(value);
        }
    }

    @Override
    public void setFrameIdx(int frameIdx) {
        this.frameIdx = frameIdx;
        Integer value = (Integer) frameIdx;
        boolean isSelected = project.getSelectedFrames().contains(value);
        frameSelectedCheckBox.setSelected(isSelected);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        boolean isSelected = frameSelectedCheckBox.isSelected();
        Integer value = (Integer) frameIdx;
        if (isSelected) {
            if (!project.getSelectedFrames().contains(value)) {
                project.getSelectedFrames().add(value);
            }
        } else {
            project.getSelectedFrames().remove((Integer) frameIdx);
        }
        notifyListeners();
    }

    private void notifyListeners() {
        dataListeners.forEach(x -> x.projectDataChanged());
    }
}