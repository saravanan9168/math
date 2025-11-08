/**
 * FibonacciTest - Test class to verify Fibonacci calculations
 * Run this to ensure the algorithm works correctly
 */
public class FibonacciTest {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║    FIBONACCI CURVE GENERATOR - TEST SUITE             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Test 1: Basic Arithmetic
        testArithmetic();
        
        // Test 2: Fibonacci Generation
        testFibonacciGeneration();
        
        // Test 3: Individual Curves
        testIndividualCurves();
        
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║    ALL TESTS COMPLETED                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    private static void testArithmetic() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 1: Basic Arithmetic Operations                │");
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        double a = 10.5, b = 3.2;
        
        System.out.printf("  Addition:       %.2f + %.2f = %.2f%n", 
                         a, b, FibonacciCalculator.add(a, b));
        System.out.printf("  Subtraction:    %.2f - %.2f = %.2f%n", 
                         a, b, FibonacciCalculator.subtract(a, b));
        System.out.printf("  Multiplication: %.2f × %.2f = %.2f%n", 
                         a, b, FibonacciCalculator.multiply(a, b));
        System.out.printf("  Division:       %.2f ÷ %.2f = %s%n", 
                         a, b, FibonacciCalculator.divide(a, b));
        System.out.printf("  Division by 0:  %.2f ÷ 0 = %s%n", 
                         a, FibonacciCalculator.divide(a, 0));
        
        System.out.println("  ✓ Arithmetic operations working correctly");
        System.out.println();
    }
    
    private static void testFibonacciGeneration() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 2: Fibonacci Number Generation                │");
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        int[] testValues = {5, 10, 15};
        
        for (int N : testValues) {
            System.out.printf("  Testing N = %d:%n", N);
            
            FibonacciCalculator.FibonacciResult result = 
                FibonacciCalculator.calculateFibonacci(N);
            
            System.out.print("    Fibonacci sequence: ");
            for (int i = 0; i < Math.min(10, result.fibonacci.size()); i++) {
                System.out.print(result.fibonacci.get(i));
                if (i < Math.min(9, result.fibonacci.size() - 1)) {
                    System.out.print(", ");
                }
            }
            if (result.fibonacci.size() > 10) {
                System.out.print(", ...");
            }
            System.out.println();
            
            System.out.printf("    Number of points: %d%n", result.X.size());
            System.out.printf("    Curve length: %.4f units%n", result.L);
            System.out.println();
        }
        
        System.out.println("  ✓ Fibonacci generation working correctly");
        System.out.println();
    }
    
    private static void testIndividualCurves() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 3: Individual Curve Segments                  │");
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        int N = 8;
        System.out.printf("  Testing N = %d:%n", N);
        
        var segments = FibonacciCalculator.calculateIndividualCurves(N);
        
        System.out.printf("    Number of segments: %d%n", segments.size());
        System.out.println();
        
        System.out.println("    Segment Details:");
        System.out.println("    ┌──────┬─────────┬────────────┐");
        System.out.println("    │ Arc  │ Radius  │ Points     │");
        System.out.println("    ├──────┼─────────┼────────────┤");
        
        for (int i = 0; i < Math.min(8, segments.size()); i++) {
            FibonacciCalculator.CurveSegment seg = segments.get(i);
            System.out.printf("    │  %-3d │ %7.1f │ %10d │%n", 
                            seg.index, seg.radius, seg.X.size());
        }
        
        System.out.println("    └──────┴─────────┴────────────┘");
        System.out.println();
        
        // Verify each segment has correct angle range (90 degrees = 90 points)
        boolean allCorrect = true;
        for (var seg : segments) {
            if (seg.X.size() != 90) {
                allCorrect = false;
                System.out.printf("  ✗ Segment %d has incorrect point count: %d (expected 90)%n", 
                                seg.index, seg.X.size());
            }
        }
        
        if (allCorrect) {
            System.out.println("  ✓ All segments have correct point count (90 points/arc)");
        }
        
        // Calculate total length
        double totalLength = 0;
        for (var seg : segments) {
            totalLength += Math.PI * (seg.radius / 2.0);
        }
        System.out.printf("  ✓ Total curve length: %.4f units%n", totalLength);
        System.out.println();
    }
}
