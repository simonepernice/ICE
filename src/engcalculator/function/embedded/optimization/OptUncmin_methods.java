package engcalculator.function.embedded.optimization;

public interface OptUncmin_methods {

    double f_to_minimize(double x[]);

    void gradient(double x[], double g[]);

    void hessian(double x[], double h[][]);
}
