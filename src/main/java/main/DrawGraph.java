package main;

import integrator.RectangleIntegrator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import rits.RitsMethod;

public class DrawGraph extends ApplicationFrame {

    public DrawGraph(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Points", "Values",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        int n = 20;
        RectangleIntegrator rec = new RectangleIntegrator();
        RitsMethod rits = new RitsMethod(0, 1, n, rec, 0.00001, 1000);
        double[] u = rits.getSolution(DrawGraph::p, DrawGraph::q, DrawGraph::f);
        rits.print();
        double[] x = rits.getX();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double fault = 0;
        for (int i = 0; i < n - 1; i++) {
            dataset.addValue((Number) exactF(x[i + 1]), "exact solution", x[i + 1]);
            dataset.addValue((Number) u[i], "approximation", x[i + 1]);
            double temp = Math.abs(u[i] - exactF(x[i + 1]));
            if (temp > fault)
                fault = temp;
        }
        for (int i = 0; i < n - 1; i++) {
            System.out.println("--------------");
            System.out.print(exactF(x[i + 1]) + "\t");
            System.out.println(u[i]);
        }
        System.out.println("Max fault = " + fault);
        return dataset;
    }

    public static void main(String[] args) {
        DrawGraph chart = new DrawGraph(
                "Ritz method",
                "Exact solution vs approximation");

        chart.pack();
        chart.setVisible(true);
    }

    public static double p(double x) {
        return Math.exp(-x);
    }

    public static double q(double x) {
        return Math.exp(-x);
    }

    public static double f(double x) {
        return 1 - Math.exp(-x) * (1 + Math.E * (1 + x));
    }

    public static double exactF(double x) {
        return Math.exp(x) - Math.E * x - 1;
    }
}