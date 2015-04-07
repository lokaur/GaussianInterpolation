package interpolation;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class GraphingData extends JPanel {

    private XYPlot plot;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private double a = 0, b = 10, c = -5, d = 5;

    public GraphingData() {
        final XYDataset dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    public void update(XYDataset dataset) {
        chart = createChart(dataset);
        chartPanel.setChart(chart);
        plot.setDataset(dataset);
    }

    public void setAxis(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    private XYDataset createDataset() {
        return new XYSeriesCollection();
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "f(x) = α*cos(εx)*sin(tg(β/(x-γ)))",    // chart title
                "x",                                    // x axis label
                "y",                                    // y axis label
                dataset,                                // data
                PlotOrientation.VERTICAL,
                true,                                   // include legend
                true,                                   // tooltips
                false                                   // urls
        );

        chart.setBackgroundPaint(Color.white);

        plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        ValueAxis yAxis = plot.getRangeAxis();
        ValueAxis xAxis = plot.getDomainAxis();
        yAxis.setRange(c, d);
        xAxis.setRange(a, b);

        final XYSplineRenderer renderer = new XYSplineRenderer(20);
        Shape shape = new Ellipse2D.Double(0, 0, 0.01, 0.01);
        Shape shape1 = new Ellipse2D.Double(0, 0, 5, 5);

        for (int i = 0; i < 6; i++) {
            renderer.setSeriesLinesVisible(i, true);
            if (i != 2)
                renderer.setSeriesShape(i, shape);
            else
                renderer.setSeriesShape(i, shape1);
        }

        //Set lines color
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.BLACK);
        renderer.setSeriesPaint(4, Color.CYAN);
        renderer.setSeriesPaint(5, Color.MAGENTA);

        for (int i = 0; i < 6; i++) {
            plot.setRenderer(i, renderer);
        }

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart.setAntiAlias(true);
        return chart;
    }
}