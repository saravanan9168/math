@echo off
REM Compile and Run Script for Fibonacci GUI Application
REM Windows Batch File

echo ================================================
echo  Fibonacci Curve Generator - Compilation Script
echo ================================================
echo.

REM Check if Java is installed
echo [1/4] Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)
echo Java found!
echo.

REM Compile FibonacciCalculator.java
echo [2/4] Compiling FibonacciCalculator.java...
javac FibonacciCalculator.java
if errorlevel 1 (
    echo ERROR: Compilation failed for FibonacciCalculator.java
    pause
    exit /b 1
)
echo FibonacciCalculator.java compiled successfully!
echo.

REM Compile FibonacciGUI.java
echo [3/4] Compiling FibonacciGUI.java...
javac FibonacciGUI.java
if errorlevel 1 (
    echo ERROR: Compilation failed for FibonacciGUI.java
    pause
    exit /b 1
)
echo FibonacciGUI.java compiled successfully!
echo.

REM Run the application
echo [4/4] Launching Fibonacci GUI Application...
echo.
echo ================================================
echo  Application Starting...
echo ================================================
echo.
java FibonacciGUI

pause
