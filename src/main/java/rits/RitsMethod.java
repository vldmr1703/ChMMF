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
    private double[] x;
    private Integrator integrator;
    private double eps;

    public RitsMethod(double a, double b, int n, Integrator integrator, double eps, int m) {
        this.n = n + 1;
        this.h = (b - a) / n;
        x = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            x[i] = a + i * h;
        }
        this.a = new double[this.n][this.n];
        this.b = new double[this.n];
        this.integrator = integrator;
        this.eps = eps;
        this.m = m;
    }

    public double[] getX() {
        return x;
    }

    private double phi(int i, double t) {
        if (x[0] <= t && t < x[i - 1]) {
            return 0;
        } else if (x[i - 1] <= t && t < x[i]) {
            return (t - x[i - 1]) / h;
        } else if (x[i] <= t && t < x[i + 1]) {
            return -(t - x[i + 1]) / h;
        } else return 0;
    }

    private double phiDiff(int i, double t) {
        if (x[0] <= t && t < x[i - 1]) {
            return 0;
        } else if (x[i - 1] <= t && t < x[i]) {
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
                    a[i][j] = integrator.calculate(eps, m, x[i - 1], x[i], integratorCore::getPhiResult);
                    a[i][j] += integrator.calculate(eps, m, x[i], x[i + 1], integratorCore::getPhiResult);
                } else if (j == i - 1) {
                    a[i][j] = integrator.calculate(eps, m, x[i - 1], x[i], integratorCore::getPhiResult);
                } else if (j == i + 1) {
                    a[i][j] = integrator.calculate(eps, m, x[i], x[i + 1], integratorCore::getPhiResult);
                }
            }
            IntegratorCore integratorCore = new IntegratorCore(i, f);
            b[i] = integrator.calculate(eps, m, x[i - 1], x[i], integratorCore::getFResult);
            b[i] += integrator.calculate(eps, m, x[i], x[i + 1], integratorCore::getFResult);
        }
    }

    public double[] getSolution(Function p, Function q, Function f) {
        createSystem(p, q, f);
        double[][] a1 = new double[n - 2][n - 2];
        double[] b1 = new double[n - 2];
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                a1[i][j] = a[i + 1][j + 1];
            }
            b1[i] = b[i + 1];
        }
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                System.out.print(a1[i][j] + " ");
            }
            System.out.println(b1[i]);
        }
        System.out.println();
//        Gauss gauss = new Gauss(a1, b1);
//        return gauss.getX();
        Prohonka pr = new Prohonka(a1, b1);
        return pr.getX();
    }

    public void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
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


