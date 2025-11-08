import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Enhanced Fibonacci Curve Visualizer
 * Shows both connected and individual curve options
 */
public class FibonacciAdvancedGUI extends JFrame {
    
    private JSpinner nSpinner;
    private JRadioButton connectedRadio, individualRadio;
    private JButton generateButton;
    private JTextArea infoArea;
    private FibonacciPanel plotPanel;
    
    public FibonacciAdvancedGUI() {
        setTitle("🌀 Advanced Fibonacci Curve Visualizer");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 60, 114));
        JLabel titleLabel = new JLabel("🌀 Advanced Fibonacci Curve Visualizer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        controlPanel.setPreferredSize(new Dimension(300, 800));
        
        // N value spinner
        JLabel nLabel = new JLabel("Number of Fibonacci Terms (N):");
        nLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(nLabel);
        controlPanel.add(Box.createVerticalStrut(10));
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(10, 3, 20, 1);
        nSpinner = new JSpinner(spinnerModel);
        nSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        nSpinner.setMaximumSize(new Dimension(280, 30));
        nSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(nSpinner);
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Curve type selection
        JLabel typeLabel = new JLabel("Curve Display Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(typeLabel);
        controlPanel.add(Box.createVerticalStrut(10));
        
        ButtonGroup curveTypeGroup = new ButtonGroup();
        
        individualRadio = new JRadioButton("Individual Arcs (Recommended)");
        individualRadio.setFont(new Font("Arial", Font.PLAIN, 12));
        individualRadio.setSelected(true);
        individualRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        individualRadio.setBackground(Color.WHITE);
        curveTypeGroup.add(individualRadio);
        controlPanel.add(individualRadio);
        controlPanel.add(Box.createVerticalStrut(5));
        
        connectedRadio = new JRadioButton("Connected Spiral");
        connectedRadio.setFont(new Font("Arial", Font.PLAIN, 12));
        connectedRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        connectedRadio.setBackground(Color.WHITE);
        curveTypeGroup.add(connectedRadio);
        controlPanel.add(connectedRadio);
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Generate button
        generateButton = new JButton("Generate Curve");
        generateButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateButton.setBackground(new Color(30, 60, 114));
        generateButton.setForeground(Color.WHITE);
        generateButton.setMaximumSize(new Dimension(280, 45));
        generateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        generateButton.addActionListener(e -> generateCurve());
        controlPanel.add(generateButton);
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Info area
        JLabel infoLabel = new JLabel("Curve Information:");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(infoLabel);
        controlPanel.add(Box.createVerticalStrut(10));
        
        infoArea = new JTextArea(15, 20);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setMaximumSize(new Dimension(280, 300));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(scrollPane);
        
        add(controlPanel, BorderLayout.WEST);
        
        // Plot Panel
        plotPanel = new FibonacciPanel();
        add(plotPanel, BorderLayout.CENTER);
        
        // Initial info
        updateInfo();
    }
    
    private void generateCurve() {
        int N = (Integer) nSpinner.getValue();
        boolean individual = individualRadio.isSelected();
        
        if (individual) {
            List<FibonacciCalculator.CurveSegment> segments = 
                FibonacciCalculator.calculateIndividualCurves(N);
            plotPanel.plotIndividualCurves(segments);
        } else {
            FibonacciCalculator.FibonacciResult result = 
                FibonacciCalculator.calculateFibonacci(N);
            plotPanel.plotConnectedCurve(result);
        }
        
        updateInfo();
    }
    
    private void updateInfo() {
        int N = (Integer) nSpinner.getValue();
        FibonacciCalculator.FibonacciResult result = 
            FibonacciCalculator.calculateFibonacci(N);
        
        StringBuilder info = new StringBuilder();
        info.append("FIBONACCI SEQUENCE\n");
        info.append("==================\n\n");
        
        info.append("Terms (N): ").append(N).append("\n\n");
        
        info.append("Fibonacci Numbers:\n");
        for (int i = 0; i < Math.min(result.fibonacci.size(), 15); i++) {
            info.append("F[").append(i).append("] = ")
                .append(result.fibonacci.get(i)).append("\n");
        }
        
        if (result.fibonacci.size() > 15) {
            info.append("... (").append(result.fibonacci.size() - 15)
                .append(" more)\n");
        }
        
        info.append("\n");
        info.append("Total Curve Length:\n");
        info.append(String.format("%.4f units\n\n", result.L));
        
        info.append("Number of Points:\n");
        info.append(result.X.size()).append(" points\n\n");
        
        info.append("Display Mode:\n");
        if (individualRadio.isSelected()) {
            info.append("Individual Arcs\n");
            info.append("(Each Fibonacci number\n");
            info.append("creates one arc)\n");
        } else {
            info.append("Connected Spiral\n");
            info.append("(All arcs connected)\n");
        }
        
        infoArea.setText(info.toString());
    }
    
    /**
     * Custom panel for drawing Fibonacci curves
     */
    class FibonacciPanel extends JPanel {
        private List<FibonacciCalculator.CurveSegment> segments;
        private FibonacciCalculator.FibonacciResult connectedResult;
        private boolean isIndividual = true;
        private boolean hasData = false;
        
        public FibonacciPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder("Fibonacci Curve Visualization"));
        }
        
        public void plotIndividualCurves(List<FibonacciCalculator.CurveSegment> segments) {
            this.segments = segments;
            this.isIndividual = true;
            this.hasData = true;
            repaint();
        }
        
        public void plotConnectedCurve(FibonacciCalculator.FibonacciResult result) {
            this.connectedResult = result;
            this.isIndividual = false;
            this.hasData = true;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (!hasData) {
                drawPlaceholder(g);
                return;
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isIndividual && segments != null) {
                drawIndividualCurves(g2d);
            } else if (!isIndividual && connectedResult != null) {
                drawConnectedCurve(g2d);
            }
        }
        
        private void drawPlaceholder(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Arial", Font.ITALIC, 20));
            String msg = "Click 'Generate Curve' to visualize";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g.drawString(msg, x, y);
        }
        
        private void drawIndividualCurves(Graphics2D g2d) {
            // Find bounds
            double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
            double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
            
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
            
            // Setup transform
            int margin = 60;
            int plotWidth = getWidth() - 2 * margin;
            int plotHeight = getHeight() - 2 * margin;
            
            double rangeX = maxX - minX;
            double rangeY = maxY - minY;
            minX -= rangeX * 0.1;
            maxX += rangeX * 0.1;
            minY -= rangeY * 0.1;
            maxY += rangeY * 0.1;
            
            double scaleX = plotWidth / (maxX - minX);
            double scaleY = plotHeight / (maxY - minY);
            double scale = Math.min(scaleX, scaleY);
            
            // Draw grid
            drawGrid(g2d, margin, plotWidth, plotHeight, minX, maxX, minY, maxY, scale);
            
            // Draw curves
            Color[] colors = {
                new Color(0, 0, 139),      // Dark Blue
                new Color(220, 20, 60),    // Crimson
                new Color(0, 128, 0),      // Green
                new Color(255, 140, 0),    // Dark Orange
                new Color(138, 43, 226),   // Blue Violet
                new Color(255, 20, 147),   // Deep Pink
                new Color(0, 191, 255),    // Deep Sky Blue
                new Color(255, 215, 0),    // Gold
                new Color(128, 0, 128),    // Purple
                new Color(0, 255, 127)     // Spring Green
            };
            
            g2d.setStroke(new BasicStroke(3.0f));
            
            for (int idx = 0; idx < segments.size(); idx++) {
                FibonacciCalculator.CurveSegment segment = segments.get(idx);
                g2d.setColor(colors[idx % colors.length]);
                
                for (int i = 0; i < segment.X.size() - 1; i++) {
                    int x1 = margin + (int) ((segment.X.get(i) - minX) * scale);
                    int y1 = margin + plotHeight - (int) ((segment.Y.get(i) - minY) * scale);
                    int x2 = margin + (int) ((segment.X.get(i + 1) - minX) * scale);
                    int y2 = margin + plotHeight - (int) ((segment.Y.get(i + 1) - minY) * scale);
                    
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
            
            // Draw legend
            drawLegend(g2d, colors, segments.size());
        }
        
        private void drawConnectedCurve(Graphics2D g2d) {
            List<Double> X = connectedResult.X;
            List<Double> Y = connectedResult.Y;
            
            // Find bounds
            double minX = X.stream().min(Double::compare).orElse(0.0);
            double maxX = X.stream().max(Double::compare).orElse(1.0);
            double minY = Y.stream().min(Double::compare).orElse(0.0);
            double maxY = Y.stream().max(Double::compare).orElse(1.0);
            
            // Setup transform
            int margin = 60;
            int plotWidth = getWidth() - 2 * margin;
            int plotHeight = getHeight() - 2 * margin;
            
            double rangeX = maxX - minX;
            double rangeY = maxY - minY;
            minX -= rangeX * 0.1;
            maxX += rangeX * 0.1;
            minY -= rangeY * 0.1;
            maxY += rangeY * 0.1;
            
            double scaleX = plotWidth / (maxX - minX);
            double scaleY = plotHeight / (maxY - minY);
            double scale = Math.min(scaleX, scaleY);
            
            // Draw grid
            drawGrid(g2d, margin, plotWidth, plotHeight, minX, maxX, minY, maxY, scale);
            
            // Draw connected curve
            g2d.setColor(new Color(0, 0, 128));
            g2d.setStroke(new BasicStroke(3.0f));
            
            for (int i = 0; i < X.size() - 1; i++) {
                int x1 = margin + (int) ((X.get(i) - minX) * scale);
                int y1 = margin + plotHeight - (int) ((Y.get(i) - minY) * scale);
                int x2 = margin + (int) ((X.get(i + 1) - minX) * scale);
                int y2 = margin + plotHeight - (int) ((Y.get(i + 1) - minY) * scale);
                
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        
        private void drawGrid(Graphics2D g2d, int margin, int plotWidth, int plotHeight,
                            double minX, double maxX, double minY, double maxY, double scale) {
            // Grid lines
            g2d.setColor(new Color(230, 230, 230));
            g2d.setStroke(new BasicStroke(1.0f));
            for (int i = 0; i <= 10; i++) {
                int x = margin + (int) (i * plotWidth / 10.0);
                int y = margin + (int) (i * plotHeight / 10.0);
                g2d.drawLine(x, margin, x, margin + plotHeight);
                g2d.drawLine(margin, y, margin + plotWidth, y);
            }
            
            // Axes
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));
            int zeroX = margin + (int) ((-minX) * scale);
            int zeroY = margin + plotHeight - (int) ((-minY) * scale);
            
            if (zeroX >= margin && zeroX <= margin + plotWidth) {
                g2d.drawLine(zeroX, margin, zeroX, margin + plotHeight);
            }
            if (zeroY >= margin && zeroY <= margin + plotHeight) {
                g2d.drawLine(margin, zeroY, margin + plotWidth, zeroY);
            }
            
            // Labels
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("X-axis", getWidth() - margin - 50, getHeight() - margin + 20);
            g2d.drawString("Y-axis", margin - 40, margin - 10);
        }
        
        private void drawLegend(Graphics2D g2d, Color[] colors, int numSegments) {
            int legendX = getWidth() - 150;
            int legendY = 60;
            int lineHeight = 20;
            
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Legend:", legendX, legendY);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i < Math.min(numSegments, 10); i++) {
                int y = legendY + 20 + i * lineHeight;
                
                g2d.setColor(colors[i % colors.length]);
                g2d.fillRect(legendX, y - 8, 15, 10);
                
                g2d.setColor(Color.BLACK);
                g2d.drawString("Arc " + i, legendX + 20, y);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FibonacciAdvancedGUI();
        });
    }
}
