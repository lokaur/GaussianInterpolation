package interpolation;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    InputPanel butPan = new InputPanel();
    GraphingData graph = new GraphingData();
    Gauss func = new Gauss();

    public GUI() {
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setTitle("Interpolation");
        setLayout(new FlowLayout());
        add(butPan);
        add(graph);
        butPan.btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                func.setA(butPan.getAlpha());
                func.setB(butPan.getBetta());
                func.setG(butPan.getGamma());
                func.setE(butPan.getEpsilon());
                func.setDelta(butPan.getDelt());
                func.setN(butPan.getNumPointsField());
                graph.setAxis(butPan.getA(),
                        butPan.getB(),
                        butPan.getC(),
                        butPan.getD());
                graph.update(createDataset(func));
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private XYDataset createDataset(Gauss func) {
        int k[] = new int[5];
        final XYSeriesCollection dataset = new XYSeriesCollection();
        double step = 0.001;
        if (butPan.getFx()) {
            k[0] = 1;
            final XYSeries series = new XYSeries("f(x)");
            for (double x = butPan.getA(); x < butPan.getB(); x += step) {
                series.add(x, func.fx(x));
            }
            dataset.addSeries(series);
        }

//        if (butPan.getFx()) {
//            k[0] = 1;
//            int i = 0;
//            double x = -100;
//            func.storeFx(step);
//            final XYSeries series = new XYSeries("f(x)");
//            double ay[] = func.getAy();
//            double ax[] = func.getAx();
//            while (x < 201) {
//                i++;
//                x += step;
//            }
////            for (x = butPan.getA(); x < butPan.getB(); x += step, i++) {
////                series.add(x, ay[i]);
////            }
//            for ( int j = 0; j < i; j++)
//                series.add(ax[j], ay[j]);
//            dataset.addSeries(series);
//        }

        double newStep;
        if(butPan.getPnx()) {
            k[1] = 1;
            final XYSeries series = new XYSeries("Pn(x)");
            func.setPnx(step, butPan.getA(), butPan.getB());
            newStep = (double)(Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
            double x = butPan.getA();
            do {
                series.add(x, func.pnx(x));
                x += newStep;
            } while (x < butPan.getB());
            series.add(butPan.getB(), func.pnx(butPan.getB()));
            dataset.addSeries(series);
        }

        if (butPan.getRnx()) {
            k[2] = 1;
            final XYSeries series = new XYSeries(("rn(x)"));
            func.setPnx(step, butPan.getA(), butPan.getB());
            newStep = (double)(Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
            double x = butPan.getA();
            do {
                series.add(x, func.rnx(x));
                x += newStep;
            } while (x < butPan.getB());
            series.add(butPan.getB(), func.rnx(butPan.getB()));
            dataset.addSeries(series);
//            for (double x = butPan.getA(); x < butPan.getB(); x += step)
//                series.add(x, func.rnx(x));
//            dataset.addSeries(series);

            double maxX = 0;
            double maxY = 0;
            final XYSeries series1 = new XYSeries("δ(rn(x))");
            for (double i = butPan.getA(); i < butPan.getB(); i += step) {
                if(func.rnx(i) > maxY) {
                    maxX =  i;
                    maxY = func.rnx(i);
                }
            }
            maxX -= step;
            series1.add(maxX, maxY);
            dataset.addSeries(series1);
        }

        if (butPan.getDfx()) {
            k[3] = 1;
            final XYSeries series = new XYSeries(("∂f(x)"));
            for (double x = butPan.getA(); x < butPan.getB(); x += step) {
                series.add(x, func.dfx(x));
            }
            dataset.addSeries(series);
        }

        if (butPan.getDpnx()) {
            k[4] = 1;
            final XYSeries series = new XYSeries(("∂Pn(x)"));
            func.setPnx(step, butPan.getA(), butPan.getB());
            newStep = (double)(Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
            double x = butPan.getA();
            do {
                series.add(x, func.dpnx(x));
                x += newStep;
            } while (x < butPan.getB());
            series.add(butPan.getB(), func.dpnx(butPan.getB()));
            dataset.addSeries(series);
        }

        graph.setKey(k);

        return dataset;
    }

    public static void main(String[] args) {
        new GUI();
    }
}
