package systemsolver;

public class SystemSolver {

    public class LU {
        private int n;
        private double[][] a;
        private double[] b;
        private double[][] l;
        private double[][] u;

        public LU(double[][] a, double[] b) {
            this.a = a;
            this.b = b;
            n = b.length;
            l = new double[n][n];
            u = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) l[i][j] = 1;
                    else if (i > j)
                        l[i][j] = -1;
                    else if (i < j)
                        u[i][j] = -1;
                }
            }
        }

        public double[] getX() {
            double[] x = new double[n];
            double[] y = new double[n];
            double sum;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    {

                        if (i <= j) {
                            sum = 0;
                            for (int k = 0; k < i; k++) {
                                sum += l[i][k] * u[k][j];
                            }
                            u[i][j] = a[i][j] - sum;
                        } else {
                            sum = 0;
                            for (int k = 0; k < j; k++) {
                                sum += l[i][k] * u[k][j];
                            }
                            l[i][j] = 1 / u[j][j] * (a[i][j] - sum);
                        }
                    }
                }

            }
            for (int i = 0; i < n; i++) {
                sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += l[i][k] * y[k];
                }
                y[i] = b[i] - sum;
            }
            for (int i = n - 1; i > -1; i--) {
                sum = 0;
                for (int k = i + 1; k < n; k++) {
                    sum += u[i][k] * x[k];
                }
                x[i] = 1 / u[i][i] * (y[i] - sum);
            }
            return x;
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
    }


}
