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
        setSize(1050, 500);
        setLocationRelativeTo(null);
        setTitle("Gaussian Interpolation");
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
        double step = 0.01;
        final XYSeries seriesFx = new XYSeries("f(x)");
        final XYSeries seriesPnx = new XYSeries("Pn(x)");
        final XYSeries seriesDFx = new XYSeries(("∂f(x)"));

        if (butPan.getFx()) {
            for (double x = butPan.getA(); x < butPan.getB(); x += step) {
                seriesFx.add(x, func.fx(x));
            }
            dataset.addSeries(seriesFx);
        } else {
            dataset.addSeries(seriesFx);
        }

        if(butPan.getPnx()) {
            func.setPnx(step, butPan.getA(), butPan.getB());
            double newStep = (Math.abs(butPan.getB()-butPan.getA()))/butPan.getNumPointsField();
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

        if (butPan.getDfx()) {
            for (double x = butPan.getA(); x < butPan.getB(); x += step) {
                seriesDFx.add(x, func.dfx(x));
            }
            dataset.addSeries(seriesDFx);
        } else {
            dataset.addSeries(seriesDFx);
        }

        return dataset;
    }

    public static void main(String[] args) {
        new GUI();
    }
}
