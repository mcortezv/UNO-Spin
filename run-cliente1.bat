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

echo ============================================
echo Compilando solo cliente y sus dependencias...
echo ============================================
call mvn clean install -q -DskipTests -pl cliente-assembler -am
if errorlevel 1 (
    echo.
    echo ERROR: Fallo la compilacion.
    pause
    exit /b 1
)

echo.
echo ============================================
echo Iniciando CLIENTE 1
echo ============================================
echo.

java -jar cliente-assembler\target\cliente.jar

if errorlevel 1 (
    echo.
    echo El cliente termino con error.
    pause
)

endlocal