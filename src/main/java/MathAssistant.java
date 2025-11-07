import java.util.*;

public class MathAssistant {
    // Fibonacci Curve Logic (unchanged from your code)
    public static class FibonacciCurve {
        public static List<Double> generateFibonacci(int N) {
            List<Double> F = new ArrayList<>();
            F.add(0.0);
            F.add(1.0);
            for (int i = 2; i <= N; i++) {
                F.add(F.get(i - 1) + F.get(i - 2));
            }
            return F.subList(1, F.size()); // Return from index 1 (like Python F[1:])
        }

        public static List<double[]> generateCentroids(int N, List<Double> F) {
            List<double[]> I = new ArrayList<>();
            I.add(new double[]{0, 0}); // Starting point
            for (int j = 3; j <= N + 2; j++) { // Adjusted loop to match Python range(3, N)
                int pivot = (j - 2) % 4;
                boolean X_flag, Sign_Flag;
                if (pivot == 1) {
                    X_flag = true; Sign_Flag = true;
                } else if (pivot == 2) {
                    X_flag = false; Sign_Flag = true;
                } else if (pivot == 3) {
                    X_flag = true; Sign_Flag = false;
                } else { // pivot == 0
                    X_flag = false; Sign_Flag = false;
                }

                double x_temp = I.get(I.size() - 1)[0];
                double y_temp = I.get(I.size() - 1)[1];
                double fibValue = F.get(j - 3); // F[j-2] in Python (0-indexed adjustment)

                if (Sign_Flag && X_flag) {
                    x_temp += fibValue;
                } else if (!Sign_Flag && X_flag) {
                    x_temp -= fibValue;
                } else if (Sign_Flag && !X_flag) {
                    y_temp += fibValue;
                } else {
                    y_temp -= fibValue;
                }
                I.add(new double[]{x_temp, y_temp});
            }
            return I.subList(1, I.size()); // Like Python I[1:]
        }

        public static List<double[]> generateCurvePoints(List<double[]> C, List<Double> F) {
            List<double[]> P = new ArrayList<>();
            P.add(new double[]{0, 0}); // Starting point
            int[][] angles = {{1, 91}, {91, 181}, {181, 271}, {271, 361}};

            for (int i = 0; i < C.size(); i++) {
                int pivot_a = i % 4;
                int[] AnR = angles[pivot_a];
                double r = F.get(i);
                double x_align = C.get(i)[0];
                double y_align = C.get(i)[1];

                for (int j = AnR[0]; j < AnR[1]; j++) {
                    double x_temp = r * Math.cos(Math.toRadians(j));
                    double y_temp = r * Math.sin(Math.toRadians(j));
                    P.add(new double[]{x_temp + x_align, y_temp + y_align});
                }
            }
            return P;
        }

        public static double calculateLength(List<Double> F) {
            double L = 0;
            for (double r_temp : F) {
                L += Math.PI * (r_temp / 2);
            }
            return L;
        }
    }

    // Basic Math Operations (unchanged, but not used in web version)
    public static double add(double a, double b) { return a + b; }
    public static double subtract(double a, double b) { return a - b; }
    public static double multiply(double a, double b) { return a * b; }
    public static String divide(double a, double b) {
        if (b == 0) return "❌ Cannot divide by zero";
        return String.valueOf(a / b);
    }

    // Entry point (now just for local testing; web uses servlet)
    public static void main(String[] args) {
        // For console testing: same as your original
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of terms (N, 1-100, default 6): ");
        int N = scanner.nextInt();
        if (N < 1 || N > 100) N = 6;

        List<Double> F = FibonacciCurve.generateFibonacci(N);
        List<double[]> C = FibonacciCurve.generateCentroids(N, F);
        List<double[]> points = FibonacciCurve.generateCurvePoints(C, F);
        double length = FibonacciCurve.calculateLength(F);

        System.out.println("\nFibonacci Curve Points (first 10 for brevity):");
        for (int i = 0; i < Math.min(10, points.size()); i++) {
            System.out.printf("Point %d: (%.2f, %.2f)\n", i, points.get(i)[0], points.get(i)[1]);
        }
        System.out.printf("Total Curve Length: %.2f units\n", length);
        scanner.close();
    }
}