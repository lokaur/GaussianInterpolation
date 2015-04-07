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

    private int k[] = new int[5];
    private XYPlot plot;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private ValueAxis yAxis;
    private ValueAxis xAxis;
    private double a = 0, b = 10, c = 0, d = 10;

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

    public void setKey(int k[]) {
        this.k = k;
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

        yAxis = plot.getRangeAxis();
        xAxis = plot.getDomainAxis();
        yAxis.setRange(c, d);
        xAxis.setRange(a, b);

        final XYSplineRenderer renderer = new XYSplineRenderer(20);
        Shape shape = new Ellipse2D.Double(0, 0, 0.01, 0.01);
        Shape shape1 = new Ellipse2D.Double(0, 0, 5, 5);

        int j = 0;
        for(int i = 0; i < 2; i++)
            j += k[i];
        for(int i = 0; i < 6; i++) {
            renderer.setSeriesLinesVisible(i, true);
            if (k[2] == 1) {
                renderer.setSeriesShape(j+1, shape1);
            } else {
                renderer.setSeriesShape(j+1, shape);
            }
            renderer.setSeriesShape(i, shape);
        }

        for (int i = 0; i < 5; i++)
            plot.setRenderer(i, renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart.setAntiAlias(true);
        return chart;
    }
}