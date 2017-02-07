package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.events.FrameSelected;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Presents calculation results as a table
 */
@Singleton
public class ResultsTable extends JPanel {

    private JPanel buttonsPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table = new JTable();

    @Inject
    private ResultsTableModel tableModel;

    @Inject
    private Event<FrameSelected> frameSelectedEvent;

    @PostConstruct
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Results Table"));

        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.scrollPane.setViewportView(table);
        this.table.setModel(tableModel);
        this.table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getSelectionModel().addListSelectionListener(e -> {
            if (table.getSelectedRow() != -1) {
                int selectedFrameIdx = (int) tableModel.getValueAt(table.getSelectedRow(), 0);
                frameSelectedEvent.fire(new FrameSelected(selectedFrameIdx));
            }
        });
    }
}