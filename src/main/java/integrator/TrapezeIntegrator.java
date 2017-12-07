package integrator;

public class TrapezeIntegrator extends Integrator {

    @Override
    protected double integral(int n, double a, double b, Function func) {
        double i = 0;
        double h = (b - a) / n;
        for (int k = 1; k < n; k++) {
            i += func.getResult(a + k * h);
        }
        i = (i + func.getResult(a)/2 + func.getResult(b)/2) * h;
        return i;
    }
}
