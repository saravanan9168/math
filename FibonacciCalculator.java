import java.util.*;

/**
 * FibonacciCalculator - Core logic for Fibonacci curve generation
 * This class handles all mathematical operations including basic arithmetic
 * and Fibonacci curve generation with individual quadrant curves
 */
public class FibonacciCalculator {
    
    /**
     * Result class to hold the Fibonacci curve data
     */
    public static class FibonacciResult {
        public List<double[]> P;  // Points array
        public List<Double> X;     // X coordinates
        public List<Double> Y;     // Y coordinates
        public double L;           // Total length
        public List<Integer> fibonacci; // Fibonacci numbers
        public List<double[]> centroids; // Centroid positions
        
        public FibonacciResult(List<double[]> P, List<Double> X, List<Double> Y, 
                              double L, List<Integer> fibonacci, List<double[]> centroids) {
            this.P = P;
            this.X = X;
            this.Y = Y;
            this.L = L;
            this.fibonacci = fibonacci;
            this.centroids = centroids;
        }
    }
    
    /**
     * Generate Fibonacci curve with individual quadrant curves (not connected)
     * @param N - Number of Fibonacci terms to generate
     * @return FibonacciResult containing curve data
     */
    public static FibonacciResult calculateFibonacci(int N) {
        // Generate Fibonacci numbers
        List<Integer> F = new ArrayList<>();
        F.add(0);
        F.add(1);
        
        for (int i = 0; i < N; i++) {
            F.add(F.get(F.size() - 1) + F.get(F.size() - 2));
        }
        
        // Calculate centroids for each arc
        List<double[]> I = new ArrayList<>();
        I.add(new double[]{0, 0});
        I.add(new double[]{0, 0});
        I.add(new double[]{0, 0});
        
        for (int j = 3; j < N; j++) {
            int pivot = (j - 2) % 4;
            boolean X_flag = false;
            boolean Sign_Flag = false;
            
            if (pivot == 1) {
                X_flag = true;
                Sign_Flag = true;
            } else if (pivot == 2) {
                X_flag = false;
                Sign_Flag = true;
            } else if (pivot == 3) {
                X_flag = true;
                Sign_Flag = false;
            } else if (pivot == 0) {
                X_flag = false;
                Sign_Flag = false;
            }
            
            double x_temp = I.get(j - 1)[0];
            double y_temp = I.get(j - 1)[1];
            
            if (Sign_Flag && X_flag) {
                x_temp += F.get(j - 2);
            } else if (Sign_Flag && !X_flag) {
                y_temp += F.get(j - 2);
            } else if (!Sign_Flag && X_flag) {
                x_temp -= F.get(j - 2);
            } else if (!Sign_Flag && !X_flag) {
                y_temp -= F.get(j - 2);
            }
            
            I.add(new double[]{x_temp, y_temp});
        }
        
        // Remove first element from F and I
        F.remove(0);
        I.remove(0);
        
        // Generate curve points
        List<double[]> P = new ArrayList<>();
        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();
        
        P.add(new double[]{0, 0});
        
        int[][] Angle = {{1, 91}, {91, 181}, {181, 271}, {271, 361}};
        
        for (int i = 0; i < I.size(); i++) {
            int pivot_a = i % 4;
            int[] AnR = Angle[pivot_a];
            double r = F.get(i);
            
            double x_align = I.get(i)[0];
            double y_align = I.get(i)[1];
            
            for (int j = AnR[0]; j < AnR[1]; j++) {
                double x_temp = r * Math.cos(Math.toRadians(j));
                double y_temp = r * Math.sin(Math.toRadians(j));
                
                P.add(new double[]{x_temp, y_temp});
                X.add(x_temp + x_align);
                Y.add(y_temp + y_align);
            }
        }
        
        // Calculate total length
        double L = 0;
        for (int k = 0; k < F.size(); k++) {
            double r_temp = F.get(k);
            L += Math.PI * (r_temp / 2.0);
        }
        
        return new FibonacciResult(P, X, Y, L, F, I);
    }
    
    /**
     * Generate individual Fibonacci curve segments (not connected)
     * Each Fibonacci number creates one quadrant arc
     */
    public static List<CurveSegment> calculateIndividualCurves(int N) {
        List<CurveSegment> segments = new ArrayList<>();
        
        // Generate Fibonacci numbers
        List<Integer> F = new ArrayList<>();
        F.add(0);
        F.add(1);
        
        for (int i = 0; i < N; i++) {
            F.add(F.get(F.size() - 1) + F.get(F.size() - 2));
        }
        
        // Calculate centroids
        List<double[]> I = new ArrayList<>();
        I.add(new double[]{0, 0});
        I.add(new double[]{0, 0});
        I.add(new double[]{0, 0});
        
        for (int j = 3; j < N; j++) {
            int pivot = (j - 2) % 4;
            boolean X_flag = false;
            boolean Sign_Flag = false;
            
            if (pivot == 1) {
                X_flag = true;
                Sign_Flag = true;
            } else if (pivot == 2) {
                X_flag = false;
                Sign_Flag = true;
            } else if (pivot == 3) {
                X_flag = true;
                Sign_Flag = false;
            } else if (pivot == 0) {
                X_flag = false;
                Sign_Flag = false;
            }
            
            double x_temp = I.get(j - 1)[0];
            double y_temp = I.get(j - 1)[1];
            
            if (Sign_Flag && X_flag) {
                x_temp += F.get(j - 2);
            } else if (Sign_Flag && !X_flag) {
                y_temp += F.get(j - 2);
            } else if (!Sign_Flag && X_flag) {
                x_temp -= F.get(j - 2);
            } else if (!Sign_Flag && !X_flag) {
                y_temp -= F.get(j - 2);
            }
            
            I.add(new double[]{x_temp, y_temp});
        }
        
        // Remove first element
        F.remove(0);
        I.remove(0);
        
        // Generate individual curve segments
        int[][] Angle = {{1, 91}, {91, 181}, {181, 271}, {271, 361}};
        
        for (int i = 0; i < I.size(); i++) {
            List<Double> segmentX = new ArrayList<>();
            List<Double> segmentY = new ArrayList<>();
            
            int pivot_a = i % 4;
            int[] AnR = Angle[pivot_a];
            double r = F.get(i);
            
            double x_align = I.get(i)[0];
            double y_align = I.get(i)[1];
            
            for (int j = AnR[0]; j < AnR[1]; j++) {
                double x_temp = r * Math.cos(Math.toRadians(j));
                double y_temp = r * Math.sin(Math.toRadians(j));
                
                segmentX.add(x_temp + x_align);
                segmentY.add(y_temp + y_align);
            }
            
            segments.add(new CurveSegment(segmentX, segmentY, r, i));
        }
        
        return segments;
    }
    
    /**
     * CurveSegment - Represents one individual arc of the Fibonacci curve
     */
    public static class CurveSegment {
        public List<Double> X;
        public List<Double> Y;
        public double radius;
        public int index;
        
        public CurveSegment(List<Double> X, List<Double> Y, double radius, int index) {
            this.X = X;
            this.Y = Y;
            this.radius = radius;
            this.index = index;
        }
    }
    
    // Basic arithmetic operations
    public static double add(double a, double b) {
        return a + b;
    }
    
    public static double subtract(double a, double b) {
        return a - b;
    }
    
    public static double multiply(double a, double b) {
        return a * b;
    }
    
    public static String divide(double a, double b) {
        if (b == 0) {
            return "❌ Cannot divide by zero";
        }
        return String.format("%.2f", a / b);
    }
}
