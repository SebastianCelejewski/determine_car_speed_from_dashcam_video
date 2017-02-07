package pl.sebcel.csfdv.gui;

import pl.sebcel.csfdv.domain.SpeedDataRow;
import pl.sebcel.csfdv.events.ProjectClosed;
import pl.sebcel.csfdv.events.ResultsRecalculated;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class ResultsTableModel extends DefaultTableColumnModel implements TableModel {

    private java.util.List<SpeedDataRow> data = new ArrayList<>();
    private Set<TableModelListener> listeners = new HashSet<>();
    private NumberFormat nf = new DecimalFormat("0.00");

    public ResultsTableModel() {
        addColumn("Frame idx", 10);
        addColumn("Time, s", 10);
        addColumn("Speed, km/h", 20);
        addColumn("Avg. speed, km/h", 20);
    }

    private void addColumn(String name, int width) {
        TableColumn column = new TableColumn(0, width);
        column.setHeaderValue(name);
        this.addColumn(column);
    }

    public void onResultsRecalculated(@Observes ResultsRecalculated resultsRecalculated) {
        this.data = resultsRecalculated.getData();
        notifyListeners();
    }

    public void onProjectClosed(@Observes ProjectClosed projectClosed) {
        this.data = new ArrayList<>();
        notifyListeners();
    }

    @Override
    public int getRowCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getHeaderValue().toString();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return int.class;
            case 1:
                return double.class;
            case 2:
                return double.class;
            case 3:
                return double.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SpeedDataRow dataPoint = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dataPoint.getFrameIdx();
            case 1:
                return nf.format(dataPoint.getTime());
            case 2:
                return dataPoint.getSpeed();
            case 3:
                return dataPoint.getAveragedSpeed();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    private void notifyListeners() {
        for (TableModelListener l : listeners) {
            l.tableChanged(new TableModelEvent(this));
        }
    }
}