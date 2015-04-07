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
        final XYSeriesCollection dataset = new XYSeriesCollection();
        double step = 0.001;
        final XYSeries seriesFx = new XYSeries("f(x)");
        final XYSeries seriesPnx = new XYSeries("Pn(x)");
        final XYSeries seriesRnx = new XYSeries("rn(x)");
        final XYSeries seriesDelta = new XYSeries("δ(rn(x))");
        final XYSeries seriesDFx = new XYSeries(("∂f(x)"));
        final XYSeries seriesDPnx = new XYSeries(("∂Pn(x)"));

        if (butPan.getFx()) {
            for (double x = butPan.getA(); x < butPan.getB(); x += step) {
                seriesFx.add(x, func.fx(x));
            }
            dataset.addSeries(seriesFx);
        } else {
            dataset.addSeries(seriesFx);
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
            func.setPnx(step, butPan.getA(), butPan.getB());
            newStep = (Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
            double x = butPan.getA();
            do {
                seriesPnx.add(x, func.pnx(x));
                x += newStep;
            } while (x < butPan.getB());
            seriesPnx.add(butPan.getB(), func.pnx(butPan.getB()));
            dataset.addSeries(seriesPnx);
        } else {
            dataset.addSeries(seriesPnx);
        }

        if (butPan.getRnx()) {
            func.setPnx(step, butPan.getA(), butPan.getB());
            newStep = (Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
            double x = butPan.getA();
            do {
                seriesRnx.add(x, func.rnx(x));
                x += newStep;
            } while (x < butPan.getB());
            seriesRnx.add(butPan.getB(), func.rnx(butPan.getB()));
            dataset.addSeries(seriesRnx);
//            for (double x = butPan.getA(); x < butPan.getB(); x += step)
//                series.add(x, func.rnx(x));
//            dataset.addSeries(series);

            double maxX = 0;
            double maxY = 0;
            for (double i = butPan.getA(); i < butPan.getB(); i += step) {
                if(func.rnx(i) > maxY) {
                    maxX =  i;
                    maxY = func.rnx(i);
                }
            }
            maxX -= step;
            seriesDelta.add(maxX, maxY);
            dataset.addSeries(seriesDelta);
        } else {
            dataset.addSeries(seriesRnx);
            dataset.addSeries(seriesDelta);
        }

        if (butPan.getDfx()) {
            for (double x = butPan.getA(); x < butPan.getB(); x += step) {
                seriesDFx.add(x, func.dfx(x));
            }
            dataset.addSeries(seriesDFx);
        } else {
            dataset.addSeries(seriesDFx);
        }

        if (butPan.getDpnx()) {
            func.setPnx(step, butPan.getA(), butPan.getB());
            newStep = (Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
            double x = butPan.getA();
            do {
                seriesDPnx.add(x, func.dpnx(x));
                x += newStep;
            } while (x < butPan.getB());
            seriesDPnx.add(butPan.getB(), func.dpnx(butPan.getB()));
            dataset.addSeries(seriesDPnx);
        } else {
            dataset.addSeries(seriesDPnx);
        }

        return dataset;
    }

    public static void main(String[] args) {
        new GUI();
    }
}
