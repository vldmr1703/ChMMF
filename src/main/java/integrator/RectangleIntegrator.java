package integrator;

public class RectangleIntegrator extends Integrator {

    @Override
    protected double integral(int n, double a, double b, Function func) {
        double i = 0;
        double h = (b - a) / n;
        for (int k = 1; k < n + 1; k++) {
            i += func.getResult(a + (2 * k - 1) / 2.0 * h);
        }
        i = i * h;
        return i;
    }
}
