package pl.sebce.csfdv.gui;

import pl.sebce.csfdv.domain.Project;
import pl.sebce.csfdv.events.ValueChangeListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DataPanel extends JPanel implements ValueChangeListener {

    private Project project;

    private NumberTextField fpsTextField = new NumberTextField();
    private NumberTextField distanceTextField = new NumberTextField();

    public DataPanel() {

        fpsTextField.setEnabled(false);
        fpsTextField.setPreferredSize(new Dimension(50, 21));
        fpsTextField.setValueChangeListener(this);

        distanceTextField.setEnabled(false);
        distanceTextField.setPreferredSize(new Dimension(50, 21));
        distanceTextField.setValueChangeListener(this);

        setBorder(new TitledBorder("Data"));

        this.add(new JLabel("FPS:"));
        this.add(fpsTextField);
        this.add(new JLabel("Distance:"));
        this.add(distanceTextField);
    }

    public void openProject(Project project) {
        this.project = project;
        setValue(fpsTextField, project.getFps());
        fpsTextField.setEnabled(true);
        distanceTextField.setEnabled(true);
    }

    public void closeProject() {
        fpsTextField.setText("");
        distanceTextField.setText("");
        fpsTextField.setEnabled(false);
        distanceTextField.setEnabled(false);
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
}