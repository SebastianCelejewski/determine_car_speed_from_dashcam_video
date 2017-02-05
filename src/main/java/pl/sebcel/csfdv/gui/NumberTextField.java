package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.events.ValueChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NumberTextField extends JTextField implements KeyListener {

    private Integer value;
    private ValueChangeListener valueChangeListener;

    public NumberTextField() {
        this.addKeyListener(this);
    }

    public void setValueChangeListener(ValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        System.out.println("Setting value " + value);
        if (value != null) {
            this.setText(Integer.toString(value));
        } else {
            this.setText("");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (getText().trim().equals("")) {
            value = null;
        } else {
            try {
                value = Integer.parseInt(getText());
                setBackground(Color.white);
            } catch (Exception ex) {
                setBackground(Color.red);
            }
        }
        if (valueChangeListener != null) {
            valueChangeListener.valueChanged(this, value);
        }
    }
}