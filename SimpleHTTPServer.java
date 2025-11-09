import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * SimpleHTTPServer - Lightweight HTTP server for local testing
 * Usage: java SimpleHTTPServer [port]
 * Default port: 8000
 */
public class SimpleHTTPServer {
    
    private static final String DEFAULT_FILE = "index.html";
    private static final int DEFAULT_PORT = 8000;
    
    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number. Using default: " + DEFAULT_PORT);
            }
        }
        
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new FileHandler());
            server.setExecutor(null);
            server.start();
            
            System.out.println("===========================================");
            System.out.println("🚀 Server started successfully!");
            System.out.println("===========================================");
            System.out.println("📍 Local:   http://localhost:" + port);
            System.out.println("📍 Network: http://" + getLocalIP() + ":" + port);
            System.out.println("===========================================");
            System.out.println("📂 Serving files from: " + System.getProperty("user.dir"));
            System.out.println("⏹  Press Ctrl+C to stop the server");
            System.out.println("===========================================\n");
            
        } catch (IOException e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
            
            // Handle API endpoint to generate curve
            if (requestPath.equals("/generate")) {
                handleGenerate(exchange, query);
                return;
            }
            
            // Default to index.html for root path
            if (requestPath.equals("/")) {
                requestPath = "/" + DEFAULT_FILE;
            }
            
            // Remove leading slash
            String filePath = requestPath.substring(1);
            
            // Security: prevent directory traversal
            Path path = Paths.get(filePath).normalize();
            if (path.toString().contains("..")) {
                sendResponse(exchange, 403, "Forbidden".getBytes());
                return;
            }
            
            File file = new File(filePath);
            
            if (!file.exists()) {
                String notFound = "404 - File Not Found: " + requestPath;
                sendResponse(exchange, 404, notFound.getBytes());
                logRequest(exchange.getRequestMethod(), requestPath, 404);
                return;
            }
            
            if (file.isDirectory()) {
                file = new File(file, DEFAULT_FILE);
                if (!file.exists()) {
                    String notFound = "404 - Directory index not found";
                    sendResponse(exchange, 404, notFound.getBytes());
                    logRequest(exchange.getRequestMethod(), requestPath, 404);
                    return;
                }
            }
            
            try {
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                String contentType = getContentType(file.getName());
                
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate");
                exchange.sendResponseHeaders(200, fileBytes.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(fileBytes);
                os.close();
                
                logRequest(exchange.getRequestMethod(), requestPath, 200);
                
            } catch (IOException e) {
                String error = "500 - Internal Server Error";
                sendResponse(exchange, 500, error.getBytes());
                logRequest(exchange.getRequestMethod(), requestPath, 500);
            }
        }
        
        private void sendResponse(HttpExchange exchange, int statusCode, byte[] response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
        
        private String getContentType(String fileName) {
            if (fileName.endsWith(".html") || fileName.endsWith(".htm")) return "text/html; charset=utf-8";
            if (fileName.endsWith(".css")) return "text/css";
            if (fileName.endsWith(".js")) return "application/javascript";
            if (fileName.endsWith(".json")) return "application/json";
            if (fileName.endsWith(".png")) return "image/png";
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
            if (fileName.endsWith(".gif")) return "image/gif";
            if (fileName.endsWith(".svg")) return "image/svg+xml";
            if (fileName.endsWith(".ico")) return "image/x-icon";
            if (fileName.endsWith(".pdf")) return "application/pdf";
            if (fileName.endsWith(".xml")) return "application/xml";
            if (fileName.endsWith(".txt")) return "text/plain";
            return "application/octet-stream";
        }
        
        private void logRequest(String method, String path, int status) {
            String statusIcon = status == 200 ? "✓" : "✗";
            System.out.printf("%s [%s] %s - %d\n", statusIcon, method, path, status);
        }
        
        private void handleGenerate(HttpExchange exchange, String query) throws IOException {
            if (query == null || !query.startsWith("n=")) {
                sendResponse(exchange, 400, "Missing parameter: n".getBytes());
                logRequest(exchange.getRequestMethod(), "/generate", 400);
                return;
            }
            
            try {
                int n = Integer.parseInt(query.substring(2));
                if (n < 1 || n > 30) {
                    sendResponse(exchange, 400, "N must be between 1 and 30".getBytes());
                    logRequest(exchange.getRequestMethod(), "/generate?n=" + n, 400);
                    return;
                }
                
                // Generate SVG directly without saving to file
                System.out.println("🔄 Generating curve for N=" + n + "...");
                
                String svg = generateSVGDirectly(n);
                
                if (svg == null) {
                    sendResponse(exchange, 500, "Failed to generate curve".getBytes());
                    logRequest(exchange.getRequestMethod(), "/generate?n=" + n, 500);
                    return;
                }
                
                byte[] svgBytes = svg.getBytes("UTF-8");
                exchange.getResponseHeaders().set("Content-Type", "image/svg+xml; charset=utf-8");
                exchange.getResponseHeaders().set("Cache-Control", "no-cache");
                exchange.sendResponseHeaders(200, svgBytes.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(svgBytes);
                os.close();
                
                System.out.println("✓ Generated and sent curve for N=" + n);
                logRequest(exchange.getRequestMethod(), "/generate?n=" + n, 200);
                
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "Invalid N parameter".getBytes());
                logRequest(exchange.getRequestMethod(), "/generate", 400);
            } catch (Exception e) {
                sendResponse(exchange, 500, ("Error: " + e.getMessage()).getBytes());
                logRequest(exchange.getRequestMethod(), "/generate", 500);
                e.printStackTrace();
            }
        }
        
        private String generateSVGDirectly(int N) {
            try {
                // Use FibonacciCalculator to get curve segments
                java.util.List<Object> segments = (java.util.List<Object>) Class.forName("FibonacciCalculator")
                    .getMethod("calculateIndividualCurves", int.class)
                    .invoke(null, N);
                
                // Build SVG dynamically
                return buildSVG(segments, N);
                
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        private String buildSVG(java.util.List<Object> segments, int N) throws Exception {
            final int WIDTH = 1000;
            final int HEIGHT = 800;
            final int MARGIN = 60;
            
            // Find bounds
            double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
            double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
            
            for (Object segObj : segments) {
                java.util.List<Double> X = (java.util.List<Double>) segObj.getClass().getField("X").get(segObj);
                java.util.List<Double> Y = (java.util.List<Double>) segObj.getClass().getField("Y").get(segObj);
                
                for (double x : X) {
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                }
                for (double y : Y) {
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
            int plotWidth = WIDTH - 2 * MARGIN;
            int plotHeight = HEIGHT - 2 * MARGIN;
            double scaleX = plotWidth / (maxX - minX);
            double scaleY = plotHeight / (maxY - minY);
            double scale = Math.min(scaleX, scaleY);
            
            // Build SVG string
            StringBuilder svg = new StringBuilder();
            svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(WIDTH)
               .append("\" height=\"").append(HEIGHT).append("\" viewBox=\"0 0 ").append(WIDTH)
               .append(" ").append(HEIGHT).append("\">\n");
            
            // White background
            svg.append("<rect width=\"").append(WIDTH).append("\" height=\"").append(HEIGHT)
               .append("\" fill=\"white\"/>\n");
            
            // Grid
            svg.append("<!-- Grid -->\n");
            for (int i = 0; i <= 10; i++) {
                int x = MARGIN + (int)(i * plotWidth / 10.0);
                int y = MARGIN + (int)(i * plotHeight / 10.0);
                svg.append("<line x1=\"").append(x).append("\" y1=\"").append(MARGIN)
                   .append("\" x2=\"").append(x).append("\" y2=\"").append(MARGIN + plotHeight)
                   .append("\" stroke=\"#e0e0e0\" stroke-width=\"1\"/>\n");
                svg.append("<line x1=\"").append(MARGIN).append("\" y1=\"").append(y)
                   .append("\" x2=\"").append(MARGIN + plotWidth).append("\" y2=\"").append(y)
                   .append("\" stroke=\"#e0e0e0\" stroke-width=\"1\"/>\n");
            }
            
            // Axes
            svg.append("<!-- Axes -->\n");
            int zeroX = MARGIN + (int)((-minX) * scale);
            int zeroY = MARGIN + plotHeight - (int)((-minY) * scale);
            svg.append("<line x1=\"").append(MARGIN).append("\" y1=\"").append(zeroY)
               .append("\" x2=\"").append(MARGIN + plotWidth).append("\" y2=\"").append(zeroY)
               .append("\" stroke=\"black\" stroke-width=\"2\"/>\n");
            svg.append("<line x1=\"").append(zeroX).append("\" y1=\"").append(MARGIN)
               .append("\" x2=\"").append(zeroX).append("\" y2=\"").append(MARGIN + plotHeight)
               .append("\" stroke=\"black\" stroke-width=\"2\"/>\n");
            
            // Colors
            String[] colors = {"#000080", "#DC143C", "#008000", "#FF8C00", "#8A2BE2", 
                              "#FF1493", "#00BFFF", "#FFD700", "#FF6347", "#20B2AA"};
            
            // Draw curves
            svg.append("<!-- Curves -->\n");
            int idx = 0;
            for (Object segObj : segments) {
                java.util.List<Double> X = (java.util.List<Double>) segObj.getClass().getField("X").get(segObj);
                java.util.List<Double> Y = (java.util.List<Double>) segObj.getClass().getField("Y").get(segObj);
                
                svg.append("<polyline points=\"");
                for (int i = 0; i < X.size(); i++) {
                    int x = MARGIN + (int)((X.get(i) - minX) * scale);
                    int y = MARGIN + plotHeight - (int)((Y.get(i) - minY) * scale);
                    svg.append(x).append(",").append(y);
                    if (i < X.size() - 1) svg.append(" ");
                }
                svg.append("\" fill=\"none\" stroke=\"").append(colors[idx % colors.length])
                   .append("\" stroke-width=\"2.5\" stroke-linecap=\"round\"/>\n");
                idx++;
            }
            
            // Title
            svg.append("<text x=\"").append(WIDTH / 2).append("\" y=\"25\" font-size=\"18\" ")
               .append("font-weight=\"bold\" text-anchor=\"middle\">Fibonacci Curve (N=")
               .append(N).append(")</text>\n");
            
            svg.append("</svg>\n");
            
            return svg.toString();
        }
    }
    
    private static String getLocalIP() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }
}
