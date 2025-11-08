import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * FibonacciGUI - Mathematical Assistant with Fibonacci Curve visualization
 * Replicates the Streamlit Python application in Java Swing
 */
public class FibonacciGUI extends JFrame {
    
    private JComboBox<String> operationCombo;
    private JTextField num1Field, num2Field, nField;
    private JButton calculateButton;
    private JLabel resultLabel;
    private JPanel inputPanel, displayPanel;
    private FibonacciPlotPanel plotPanel;
    
    public FibonacciGUI() {
        setTitle("🧮 SS AI Lab's Mathematical Assistant");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Set background color
        getContentPane().setBackground(new Color(240, 248, 255));
        
        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("🧮 SS AI Lab's Mathematical Assistant");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Control Panel (Left)
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        controlPanel.setPreferredSize(new Dimension(350, 600));
        
        // Operation selection
        JLabel opLabel = new JLabel("✅ Choose the operation:");
        opLabel.setFont(new Font("Arial", Font.BOLD, 14));
        opLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(opLabel);
        controlPanel.add(Box.createVerticalStrut(10));
        
        String[] operations = {"Addition", "Subtraction", "Multiplication", "Division", "Fibonacci Curve"};
        operationCombo = new JComboBox<>(operations);
        operationCombo.setMaximumSize(new Dimension(300, 30));
        operationCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        operationCombo.addActionListener(e -> updateInputFields());
        controlPanel.add(operationCombo);
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Input Panel
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(inputPanel);
        
        // Calculate Button
        controlPanel.add(Box.createVerticalStrut(20));
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.setBackground(new Color(70, 130, 180));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setMaximumSize(new Dimension(300, 40));
        calculateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        calculateButton.addActionListener(e -> performCalculation());
        controlPanel.add(calculateButton);
        
        // Result Label
        controlPanel.add(Box.createVerticalStrut(20));
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(new Color(0, 100, 0));
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(resultLabel);
        
        mainPanel.add(controlPanel, BorderLayout.WEST);
        
        // Display Panel (Right) - for plotting
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(Color.WHITE);
        displayPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        
        plotPanel = new FibonacciPlotPanel();
        displayPanel.add(plotPanel, BorderLayout.CENTER);
        
        mainPanel.add(displayPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateInputFields();
    }
    
    private void updateInputFields() {
        inputPanel.removeAll();
        String operation = (String) operationCombo.getSelectedItem();
        
        if (operation.equals("Fibonacci Curve")) {
            JLabel nLabel = new JLabel("Enter the number of terms (N):");
            nLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            nLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(nLabel);
            inputPanel.add(Box.createVerticalStrut(5));
            
            nField = new JTextField("10");
            nField.setMaximumSize(new Dimension(300, 30));
            nField.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(nField);
        } else {
            JLabel num1Label = new JLabel("Enter the Value of A:");
            num1Label.setFont(new Font("Arial", Font.PLAIN, 12));
            num1Label.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(num1Label);
            inputPanel.add(Box.createVerticalStrut(5));
            
            num1Field = new JTextField("0.0");
            num1Field.setMaximumSize(new Dimension(300, 30));
            num1Field.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(num1Field);
            inputPanel.add(Box.createVerticalStrut(10));
            
            JLabel num2Label = new JLabel("Enter the Value of B:");
            num2Label.setFont(new Font("Arial", Font.PLAIN, 12));
            num2Label.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(num2Label);
            inputPanel.add(Box.createVerticalStrut(5));
            
            num2Field = new JTextField("0.0");
            num2Field.setMaximumSize(new Dimension(300, 30));
            num2Field.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(num2Field);
        }
        
        inputPanel.revalidate();
        inputPanel.repaint();
    }
    
    private void performCalculation() {
        String operation = (String) operationCombo.getSelectedItem();
        
        try {
            if (operation.equals("Fibonacci Curve")) {
                int N = Integer.parseInt(nField.getText());
                if (N < 1 || N > 100) {
                    JOptionPane.showMessageDialog(this, "N must be between 1 and 100", 
                                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Generate individual curve segments
                List<FibonacciCalculator.CurveSegment> segments = 
                    FibonacciCalculator.calculateIndividualCurves(N);
                
                // Calculate total length
                double totalLength = 0;
                for (FibonacciCalculator.CurveSegment segment : segments) {
                    totalLength += Math.PI * (segment.radius / 2.0);
                }
                
                plotPanel.plotIndividualCurves(segments);
                resultLabel.setText(String.format("Fibonacci Curve Length: %.2f units", totalLength));
                
            } else {
                double num1 = Double.parseDouble(num1Field.getText());
                double num2 = Double.parseDouble(num2Field.getText());
                String result = "";
                
                switch (operation) {
                    case "Addition":
                        result = String.format("Result: %.2f", FibonacciCalculator.add(num1, num2));
                        break;
                    case "Subtraction":
                        result = String.format("Result: %.2f", FibonacciCalculator.subtract(num1, num2));
                        break;
                    case "Multiplication":
                        result = String.format("Result: %.2f", FibonacciCalculator.multiply(num1, num2));
                        break;
                    case "Division":
                        result = "Result: " + FibonacciCalculator.divide(num1, num2);
                        break;
                }
                
                resultLabel.setText(result);
                plotPanel.clear();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers", 
                                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Panel for plotting Fibonacci curves
     */
    class FibonacciPlotPanel extends JPanel {
        private List<FibonacciCalculator.CurveSegment> segments;
        private boolean showPlot = false;
        
        public FibonacciPlotPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(600, 600));
        }
        
        public void plotIndividualCurves(List<FibonacciCalculator.CurveSegment> segments) {
            this.segments = segments;
            this.showPlot = true;
            repaint();
        }
        
        public void clear() {
            this.showPlot = false;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (!showPlot || segments == null || segments.isEmpty()) {
                // Show placeholder text
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.ITALIC, 16));
                String msg = "Fibonacci Curve will appear here";
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(msg)) / 2;
                int y = getHeight() / 2;
                g.drawString(msg, x, y);
                return;
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Find bounds
            double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
            
            for (FibonacciCalculator.CurveSegment segment : segments) {
                for (double x : segment.X) {
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                }
                for (double y : segment.Y) {
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                }
            }
            
            // Add padding
            double rangeX = maxX - minX;
            double rangeY = maxY - minY;
            double padding = 0.1;
            minX -= rangeX * padding;
            maxX += rangeX * padding;
            minY -= rangeY * padding;
            maxY += rangeY * padding;
            
            // Calculate scale
            int plotWidth = getWidth() - 60;
            int plotHeight = getHeight() - 60;
            
            double scaleX = plotWidth / (maxX - minX);
            double scaleY = plotHeight / (maxY - minY);
            double scale = Math.min(scaleX, scaleY);
            
            // Center the plot
            int offsetX = 30;
            int offsetY = 30;
            
            // Draw grid
            g2d.setColor(new Color(220, 220, 220));
            for (int i = 0; i <= 10; i++) {
                int x = offsetX + (int) (i * plotWidth / 10.0);
                int y = offsetY + (int) (i * plotHeight / 10.0);
                g2d.drawLine(x, offsetY, x, offsetY + plotHeight);
                g2d.drawLine(offsetX, y, offsetX + plotWidth, y);
            }
            
            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            int zeroX = offsetX + (int) ((-minX) * scale);
            int zeroY = offsetY + plotHeight - (int) ((-minY) * scale);
            g2d.drawLine(offsetX, zeroY, offsetX + plotWidth, zeroY); // X-axis
            g2d.drawLine(zeroX, offsetY, zeroX, offsetY + plotHeight); // Y-axis
            
            // Draw each curve segment individually with different colors
            Color[] colors = {
                new Color(0, 0, 128),      // Navy
                new Color(220, 20, 60),    // Crimson
                new Color(0, 128, 0),      // Green
                new Color(255, 140, 0),    // Dark Orange
                new Color(138, 43, 226),   // Blue Violet
                new Color(255, 20, 147),   // Deep Pink
                new Color(0, 191, 255),    // Deep Sky Blue
                new Color(255, 215, 0)     // Gold
            };
            
            g2d.setStroke(new BasicStroke(2.5f));
            
            for (int idx = 0; idx < segments.size(); idx++) {
                FibonacciCalculator.CurveSegment segment = segments.get(idx);
                g2d.setColor(colors[idx % colors.length]);
                
                for (int i = 0; i < segment.X.size() - 1; i++) {
                    int x1 = offsetX + (int) ((segment.X.get(i) - minX) * scale);
                    int y1 = offsetY + plotHeight - (int) ((segment.Y.get(i) - minY) * scale);
                    int x2 = offsetX + (int) ((segment.X.get(i + 1) - minX) * scale);
                    int y2 = offsetY + plotHeight - (int) ((segment.Y.get(i + 1) - minY) * scale);
                    
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
            
            // Draw title and labels
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String title = "Fibonacci Curve (Individual Quadrants)";
            FontMetrics fm = g2d.getFontMetrics();
            int titleX = (getWidth() - fm.stringWidth(title)) / 2;
            g2d.drawString(title, titleX, 20);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("X-axis (one unit)", getWidth() - 120, getHeight() - 10);
            
            // Rotate for Y-axis label
            Graphics2D g2dRotated = (Graphics2D) g2d.create();
            g2dRotated.rotate(-Math.PI / 2);
            g2dRotated.drawString("Y-axis (one unit)", -getHeight() / 2 - 50, 15);
            g2dRotated.dispose();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FibonacciGUI();
        });
    }
}
