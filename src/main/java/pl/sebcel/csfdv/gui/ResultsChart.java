package pl.sebcel.csfdv.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.sebcel.csfdv.domain.SpeedDataRow;
import pl.sebcel.csfdv.events.ResultsRecalculated;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;

@Singleton
public class ResultsChart extends JPanel {

    private JFreeChart xylineChart;

    @PostConstruct
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Results Chart"));

        xylineChart = ChartFactory.createXYLineChart(
                null,
                "Time, s" ,
                "Speed, km/h" ,
                new XYSeriesCollection( ),
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
//        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        plot.setRenderer( renderer );

        this.add(chartPanel, BorderLayout.CENTER );
    }

    public void setData(@Observes ResultsRecalculated resultsRecalculated) {
        XYSeries speedPointsSeries = new XYSeries( "Data points" );
        for (SpeedDataRow p : resultsRecalculated.getData()) {
            speedPointsSeries.add(p.getTime(), p.getSpeed());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( speedPointsSeries );
        xylineChart.getXYPlot().setDataset(dataset);

        this.repaint();
    }
}