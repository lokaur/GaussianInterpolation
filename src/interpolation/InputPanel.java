package interpolation;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private JTextField alphaText = new JTextField(3);
    private JTextField bettaText = new JTextField(3);
    private JTextField gammaText = new JTextField(3);
    private JTextField epsilonText = new JTextField(3);

    private JTextField fieldA = new JTextField(4);
    private JTextField fieldB = new JTextField(4);
    private JTextField fieldC = new JTextField(4);
    private JTextField fieldD = new JTextField(4);

    private JTextField intPointsField = new JTextField(3);

    private JCheckBox fx = new JCheckBox("f(x)");
    private JCheckBox pnx = new JCheckBox("Pn(x)");
    private JCheckBox dfx = new JCheckBox("∂f(x)");
    private JTextField delta = new JTextField(3);

    public JButton btn = new JButton("Построить");

    public InputPanel() {
        setLayout(new GridLayout(12, 1));

        JLabel label1 = new JLabel("Действительные параметры функции");
        label1.setHorizontalAlignment(JLabel.CENTER);
        add(label1);
        fillFunctionParameters();

        JLabel label2 = new JLabel("Действительные размеры окна графика");
        label2.setHorizontalAlignment(JLabel.CENTER);
        add(label2);
        fillPlotWindowSize();

        JLabel label3 = new JLabel("Количество узлов интерполяции");
        label3.setHorizontalAlignment(JLabel.CENTER);
        add(label3);
        fillIntPointsNum();

        fillDelta();

        JLabel label4 = new JLabel("Отображаемые на графике функции");
        label4.setHorizontalAlignment(JLabel.CENTER);
        add(label4);
        fillShownFunc();

        btn.setHorizontalAlignment(JButton.CENTER);
        add(btn);

        JLabel label5 = new JLabel("Мартынов Андрей, гр.ИВТ-32БО");
        label5.setHorizontalAlignment(JLabel.LEFT);
        add(label5);

//        JLabel label6 = new JLabel("Полином Гаусса");
//        label6.setHorizontalAlignment(JLabel.LEFT);
//        add(label6);

    }

    public double getAlpha() {
        return Double.parseDouble(alphaText.getText());
    }

    public double getBetta() {
        return Double.parseDouble(bettaText.getText());
    }

    public double getGamma() {
        return Double.parseDouble(gammaText.getText());
    }

    public double getEpsilon() {
        return Double.parseDouble(epsilonText.getText());
    }

    public double getA() {
        return Double.parseDouble(fieldA.getText());
    }

    public double getB() {
        return Double.parseDouble(fieldB.getText());
    }

    public double getC() {
        return Double.parseDouble(fieldC.getText());
    }

    public double getD() {
        return Double.parseDouble(fieldD.getText());
    }

    public double getDelt() {
        return Double.parseDouble(delta.getText());
    }

    public boolean getFx() {
        return fx.isSelected();
    }

    public boolean getPnx() {
        return pnx.isSelected();
    }

    public boolean getDfx() {
        return dfx.isSelected();
    }

    public int getNumPointsField() {
        return Integer.parseInt(intPointsField.getText());
    }

    private void fillFunctionParameters() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(new JLabel("α"));
        panel.add(alphaText);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(new JLabel("β"));
        panel.add(bettaText);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(new JLabel("γ"));
        panel.add(gammaText);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(new JLabel("ε"));
        panel.add(epsilonText);
        add(panel);
    }

    private void fillPlotWindowSize() {
        JPanel line1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        line1.add(new JLabel("A"));
        line1.add(fieldA);
        line1.add(Box.createHorizontalStrut(10));
        line1.add(new JLabel("B"));
        line1.add(fieldB);
        add(line1);

        JPanel line2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        line2.add(new JLabel("C"));
        line2.add(fieldC);
        line2.add(Box.createHorizontalStrut(10));
        line2.add(new JLabel("D"));
        line2.add(fieldD);
        add(line2);
    }

    private void fillIntPointsNum() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(new JLabel("n"));
        panel.add(intPointsField);
        add(panel);
    }

    private void fillDelta() {
        JPanel panel = new JPanel(new FlowLayout((FlowLayout.CENTER)));
        panel.add(new JLabel("Значение Δ"));
        panel.add(delta);
        add(panel);
    }

    private void fillShownFunc() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(fx);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(pnx);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(dfx);
        add(panel);
    }
}
