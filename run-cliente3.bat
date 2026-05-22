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

if not exist "cliente-assembler\target\cliente.jar" (
    echo ERROR: cliente.jar no existe.
    echo Ejecuta primero run-cliente1.bat o run-servidor.bat para compilar.
    pause
    exit /b 1
)

echo ============================================
echo Iniciando CLIENTE 3 (puerto 6002)
echo ============================================
echo.

java -jar cliente-assembler\target\cliente.jar 6002

if errorlevel 1 (
    echo.
    echo El cliente termino con error.
    pause
)

endlocal