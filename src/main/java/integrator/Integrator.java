package integrator;

public abstract class Integrator {
    protected double eps = 0.001;
    protected int n = 100;

    protected abstract double integral(int n, double a, double b, Function func);

    public double calculate(double eps, int n, double a, double b, Function func) {
        double i0;
        double i1;
        i0 = integral(n, a, b, func);
        n = n * 2;
        i1 = integral(n, a, b, func);
        while ((i1 - i0) / i1 > eps) {
            i0 = i1;
            n = n * 2;
            i1 = integral(n, a, b, func);
        }
        return i1;
    }
}