FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Java files
COPY FibonacciCalculator.java .
COPY SimpleHTTPServer.java .
COPY index.html .

# Compile Java files
RUN javac FibonacciCalculator.java SimpleHTTPServer.java

# Expose port (will use $PORT from environment)
EXPOSE 10000

# Run the server using environment variable PORT
CMD ["sh", "-c", "java SimpleHTTPServer ${PORT:-10000}"]
