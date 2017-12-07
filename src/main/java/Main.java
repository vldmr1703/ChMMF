import integrator.RectangleIntegrator;
import rits.RitsMethod;


public class Main {
    public static void main(String[] args) {
        int n = 10;
        RectangleIntegrator rec = new RectangleIntegrator();
        RitsMethod rits = new RitsMethod(0, 1, n, rec, 0.0001, 100);
        double[] x = rits.getX();
        double[] u = rits.getSolution(Main::p, Main::q, Main::f);
        rits.print();
        for (int i = 0; i < n; i++) {
            System.out.println(x[i]);
        }
        System.out.println("---------");
        for (int i = 0; i < n; i++) {
            System.out.println(exactF(x[i]));
        }
        System.out.println("---------");
        for (int i = 0; i < n-1; i++) {
            System.out.println(u[i]);
        }
    }

    public static double p(double x) {
        return Math.exp(-x);
    }

    public static double q(double x) {
        return Math.exp(-x);
    }

    public static double f(double x) {
        return x - 1;
    }

    public static double exactF(double x) {
        return Math.exp(x) * x;
    }
}

