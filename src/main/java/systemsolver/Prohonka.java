package systemsolver;

import rits.RitsMethod;

public class Prohonka {
    private int n;
    private double[] a;
    private double[] b;
    private double[] c;
    private double[] alpha;
    private double[] beta;
    private double[] ksi;
    private double[] eta;
    private double[] y;
    private double e = 0.001;

    public Prohonka(double[][] a, double[] b) {
        n = b.length;
        this.a = new double[n];
        this.b = new double[n];
        c = new double[n];
        alpha = new double[n];
        beta = new double[n];
        ksi = new double[n];
        eta = new double[n];
        y = new double[n];
        for (int i = 0; i < n - 1; i++) {
            this.b[i] = a[i][i + 1];
        }
        for (int i = 0; i < n; i++) {
            c[i] = a[i][i];
            y[i] = b[i];
        }
        for (int i = 1; i < n; i++) {
            this.a[i] = a[i][i - 1];
        }
    }

    public double[] getX() {
        double[] x = new double[n];
        int p;
        p = (int) ((double) n / 2);
        alpha[1] = -b[0] / c[0];
        beta[1] = y[0] / c[0];
        for (int i = 1; i < p; i++) {
            alpha[i + 1] = -b[i] / (a[i] * alpha[i] + c[i]);
            beta[i + 1] = (y[i] - a[i] * beta[i]) / (a[i] * alpha[i] + c[i]);
        }
        ksi[n - 1] = -a[n - 1] / c[n - 1];
        eta[n - 1] = y[n - 1] / c[n - 1];
        for (int i = n - 2; i >= p - 1; i--) {
            ksi[i] = -a[i] / (c[i] + b[i] * ksi[i + 1]);
            eta[i] = (y[i] - b[i] * eta[i + 1]) / (c[i] + b[i] * ksi[i + 1]);
        }
        x[p - 1] = (alpha[p] * eta[p] + beta[p]) / (1 - alpha[p] * ksi[p]);
        for (int i = p - 2; i >= 0; i--) {
            x[i] = alpha[i + 1] * x[i + 1] + beta[i + 1];
        }
        x[p] = (y[p] - b[p] * eta[p + 1]) / (b[p] * ksi[p + 1] + c[p]);
        for (int i = p - 1; i < n - 1; i++) {
            x[i + 1] = ksi[i + 1] * x[i] + eta[i + 1];
        }
        return x;
    }

    void print(double[] x) {
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(x[i] + " ");
        }
        System.out.println();
    }

//    private double[] ex() {
//        double[] t = RitsMethod.x;
//        double[] m = new double[t.length];
//        for (int i = 1; i < t.length; i++) {
//            m[i] = main.Main.exactF(t[i]) + Math.pow(0.1, i + 1);
//        }
//        return m;
//    }
}