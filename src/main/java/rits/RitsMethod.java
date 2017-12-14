package rits;

import integrator.Function;
import integrator.Integrator;
import systemsolver.Gauss;
import systemsolver.Prohonka;

public class RitsMethod {

    private double[][] a;
    private double[] b;
    private int n;//count of intervals
    private int m;//count of sub-intervals
    private double h;
    private   double[] x;
    private Integrator integrator;
    private double eps;

    public RitsMethod(double a, double b, int n, Integrator integrator, double eps, int m) {
        this.n = n + 1;
        this.h = (b - a) / (n - 1);
        x = new double[this.n];
        for (int i = 0; i < n + 1; i++) {
            x[i] = a + i * h;
        }
        this.a = new double[n - 1][n - 1];
        this.b = new double[n - 1];
        this.integrator = integrator;
        this.eps = eps;
        this.m = m;
    }

    public double[] getX() {
        return x;
    }

    private double phi(int i, double t) {
        if (x[i - 1] <= t && t < x[i]) {
            return (t - x[i - 1]) / h;
        } else if (x[i] <= t && t < x[i + 1]) {
            return -(t - x[i + 1]) / h;
        } else return 0;
    }

    private double phiDiff(int i, double t) {
        if (x[i - 1] <= t && t < x[i]) {
            return 1 / h;
        } else if (x[i] <= t && t < x[i + 1]) {
            return -1 / h;
        } else return 0;
    }

    private void createSystem(Function p, Function q, Function f) {
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                IntegratorCore integratorCore = new IntegratorCore(i, j, p, q);
                if (i == j) {
                    a[i - 1][j - 1] = integrator.calculate(eps, m, x[i - 1], x[i], integratorCore::getPhiResult);
                    if (i != n - 2)
                        a[i - 1][j - 1] += integrator.calculate(eps, m, x[i], x[i + 1], integratorCore::getPhiResult);
                } else if (j == i - 1) {
                    a[i - 1][j - 1] = integrator.calculate(eps, m, x[i - 1], x[i], integratorCore::getPhiResult);
                } else if (j == i + 1) {
                    a[i - 1][j - 1] = integrator.calculate(eps, m, x[i], x[i + 1], integratorCore::getPhiResult);
                }
            }
            IntegratorCore integratorCore = new IntegratorCore(i, f);
            b[i - 1] = integrator.calculate(eps, m, x[i - 1], x[i], integratorCore::getFResult);
            if (i != n - 2)
                b[i - 1] += integrator.calculate(eps, m, x[i], x[i + 1], integratorCore::getFResult);
        }
    }

    public double[] getSolution(Function p, Function q, Function f) {
        createSystem(p, q, f);
        Prohonka pr = new Prohonka(a, b);
        return pr.getX();
    }

    public void print() {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                System.out.print(this.a[i][j] + " ");
            }
            System.out.println(this.b[i]);
        }
        System.out.println();
    }

    class IntegratorCore {
        private int i;
        private int j;
        private Function p;
        private Function q;
        private Function f;

        public IntegratorCore(int i, int j, Function p, Function q) {
            this.i = i;
            this.j = j;
            this.p = p;
            this.q = q;
        }

        public IntegratorCore(int i, Function f) {
            this.i = i;
            this.f = f;
        }

        private double getPhiResult(double x) {
            return p.getResult(x) * phiDiff(i, x) * phiDiff(j, x) + q.getResult(x) * phi(i, x) * phi(j, x);
        }

        private double getFResult(double x) {
            return f.getResult(x) * phi(i, x);
        }
    }
}


