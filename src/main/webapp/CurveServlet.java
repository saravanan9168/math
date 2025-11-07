import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.DoubleStream;

public class CurveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nStr = request.getParameter("n");
        int N = (nStr != null && !nStr.isEmpty()) ? Math.max(1, Math.min(100, Integer.parseInt(nStr))) : 6;

        // Generate curve data
        List<Double> F = MathAssistant.FibonacciCurve.generateFibonacci(N);
        List<double[]> C = MathAssistant.FibonacciCurve.generateCentroids(N, F);
        List<double[]> points = MathAssistant.FibonacciCurve.generateCurvePoints(C, F);

        // Compute bounds for scaling
        double minX = DoubleStream.of(points.stream().mapToDouble(p -> p[0]).toArray()).min().orElse(0);
        double maxX = DoubleStream.of(points.stream().mapToDouble(p -> p[0]).toArray()).max().orElse(0);
        double minY = DoubleStream.of(points.stream().mapToDouble(p -> p[1]).toArray()).min().orElse(0);
        double maxY = DoubleStream.of(points.stream().mapToDouble(p -> p[1]).toArray()).max().orElse(0);
        double width = maxX - minX + 10;  // Padding
        double height = maxY - minY + 10;

        // Create image
        int imgWidth = 800;
        int imgHeight = 800;
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, imgHeight);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f));

        if (points.size() > 1) {
            // Scale and translate
            double scaleX = (double) imgWidth / width * 0.9;
            double scaleY = (double) imgHeight / height * 0.9;
            double scale = Math.min(scaleX, scaleY);
            double offsetX = (imgWidth - width * scale) / 2 - minX * scale;
            double offsetY = (imgHeight - height * scale) / 2 - minY * scale;

            g2d.translate(offsetX, offsetY);
            g2d.scale(scale, scale);

            // Draw connected lines
            for (int i = 1; i < points.size(); i++) {
                double[] p1 = points.get(i - 1);
                double[] p2 = points.get(i);
                g2d.drawLine((int) p1[0], (int) p1[1], (int) p2[0], (int) p2[1]);
            }
        }

        g2d.dispose();

        // Serve as PNG
        response.setContentType("image/png");
        javax.imageio.ImageIO.write(image, "png", response.getOutputStream());
    }
}