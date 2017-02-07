package pl.sebcel.csfdv.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.sebcel.csfdv.domain.SpeedDataRow;
import pl.sebcel.csfdv.events.*;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;

/**
 * Presents calculation results as a chart
 */
@Singleton
public class ResultsChart extends JPanel {

    private JFreeChart xylineChart;

    private Integer fps;
    private double maximumSpeed;

    @PostConstruct
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Results Chart"));

        xylineChart = ChartFactory.createXYLineChart(null, "Time, s", "Speed, km/h", new XYSeriesCollection(), PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        XYPlot plot = xylineChart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);
        plot.setRenderer(renderer);

        this.add(chartPanel, BorderLayout.CENTER);
    }

    public void onProjectOpened(@Observes ProjectOpened projectOpened) {
        this.fps = projectOpened.getProject().getFps();
    }

    public void onProjectChanged(@Observes ProjectChanged projectChanged) {
        this.fps = projectChanged.getProject().getFps();
    }

    public void onProjectClosed(@Observes ProjectClosed projectClosed) {
        XYSeriesCollection dataset = (XYSeriesCollection) xylineChart.getXYPlot().getDataset();
        dataset.removeAllSeries();

        this.repaint();
    }

    public void onResultsRecalculated(@Observes ResultsRecalculated resultsRecalculated) {
        XYSeries speedPointsSeries = new XYSeries("Data points");
        XYSeries averagedSpeedLineSeries = new XYSeries("Averaged");

        for (SpeedDataRow p : resultsRecalculated.getData()) {
            speedPointsSeries.add(p.getTime(), p.getSpeed());
            averagedSpeedLineSeries.add(p.getTime(), p.getAveragedSpeed());
            if (p.getSpeed() > maximumSpeed) {
                maximumSpeed = p.getSpeed();
            }
        }

        XYSeries verticalLine = new XYSeries("Selected Frame");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(speedPointsSeries);
        dataset.addSeries(averagedSpeedLineSeries);
        dataset.addSeries(verticalLine);
        xylineChart.getXYPlot().setDataset(dataset);

        this.repaint();
    }

    public void onFrameSelected(@Observes FrameSelected frameSelected) {
        XYSeriesCollection dataset = (XYSeriesCollection) xylineChart.getXYPlot().getDataset();
        XYSeries verticalLine = dataset.getSeries("Selected Frame");

        verticalLine.clear();

        if (fps != null) {
            verticalLine.add((double) frameSelected.getFrameIdx() / fps, 0);
            verticalLine.add((double) frameSelected.getFrameIdx() / fps, maximumSpeed);
        }

        this.repaint();
    }
}