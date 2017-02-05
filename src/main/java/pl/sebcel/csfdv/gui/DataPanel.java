package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.domain.Project;
import pl.sebcel.csfdv.events.*;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.enterprise.event.Event;
import java.awt.*;


@Singleton
public class DataPanel extends JPanel implements ValueChangeListener, ChangeListener {

    private Project project;

    private NumberTextField fpsTextField = new NumberTextField();
    private NumberTextField distanceTextField = new NumberTextField();
    private JCheckBox frameSelectedCheckBox = new JCheckBox();

    private int frameIdx;

    @Inject
    private Event<ProjectChanged> projectChangedEvent;

    @PostConstruct
    public void initialize() {

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

    public void openProject(@Observes ProjectOpened projectOpened) {
        this.project = projectOpened.getProject();
        setValue(fpsTextField, project.getFps());
        setValue(distanceTextField, project.getReferencePointDistance());
        fpsTextField.setEnabled(true);
        distanceTextField.setEnabled(true);
        frameSelectedCheckBox.setEnabled(true);
    }

    public void closeProject(@Observes ProjectClosed projectClosed) {
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

    public void setFrameIdx(@Observes FrameSelected frameSelected) {
        this.frameIdx = frameSelected.getFrameIdx();
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

        projectChangedEvent.fire(new ProjectChanged());
    }
}