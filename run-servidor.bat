@echo off
setlocal
cd /d "%~dp0"

set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.2"
set "PATH=%JAVA_HOME%\bin;%PATH%"

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo ERROR: No se encontro JDK 25 en %JAVA_HOME%
    pause
    exit /b 1
)

echo Usando JDK en: %JAVA_HOME%
echo.

echo ============================================
echo Compilando proyecto completo...
echo ============================================
call mvn clean install -q -DskipTests
if errorlevel 1 (
    echo.
    echo ERROR: Fallo la compilacion.
    pause
    exit /b 1
)

echo.
echo ============================================
echo Iniciando SERVIDOR
echo ============================================
echo.

java -jar servidor-assembler\target\servidor.jar

if errorlevel 1 (
    echo.
    echo El servidor termino con error.
    pause
)

endlocal