package main;

import integrator.RectangleIntegrator;
import rits.*;

public class Main {
    public static void main(String[] args) {
        int n = 10;
        RectangleIntegrator rec = new RectangleIntegrator();
        RitsMethod rits = new RitsMethod(0, 1, n, rec, 0.00001, 1000);

        double[] u = rits.getSolution(Main::p, Main::q, Main::f);
//        rits.print();
        double[] x = rits.getX();
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println("---------");
        for (int i = 0; i < x.length - 1; i++) {
            System.out.println(exactF(x[i + 1]));
        }
        System.out.println("---------");
        for (int i = 0; i < u.length; i++) {
            System.out.println(u[i]);
        }
    }

    public static double p(double x) {
//        return 1;
        return Math.exp(-x);
//        return  1;
//        return Math.pow(2, x);
    }

    public static double q(double x) {
//        return Math.pow(Math.sin(x), 2);
        return Math.exp(-x);
//        return 3;
//        return x * x;
    }

    public static double f(double x) {
        return 1 - Math.exp(-x) * (1 + Math.E * (1 + x));
//        return Math.sin(x) + Math.pow(Math.sin(x), 3);
//        return x - 1;
//        return 2;
//        return -Math.pow(2, x) * (6 * x + (3 * x * x - 3) * Math.log(2)) + x * x * (x * x * x - 3 * x);
    }

    public static double exactF(double x) {
        return Math.exp(x) - Math.E * x - 1;
//        return Math.sin(x);
//        return Math.exp(x) * x;
//        return Math.pow(x, 3) - 3 * x;
    }
}

