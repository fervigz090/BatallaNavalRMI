@echo off
REM Lanzador de la aplicación
REM Autor: Iván Fernández Rodríguez

cd src || (echo No se pudo cambiar al directorio src && exit /b 1)

REM Eliminar los logs anteriores
del /q *.log

REM Compilar todos los archivos .java
javac *.java
if %errorlevel% neq 0 (
    echo Error de compilación
    exit /b 1
)

REM Lanzar la base de datos
start /b java Basededatos > basededatos.log 2>&1
set "basededatos_pid=%!"

REM Dar tiempo a que la base de datos se inicie
timeout /t 2 /nobreak > nul

REM Lanzar el servidor
start /b java Servidor > servidor.log 2>&1
set "servidor_pid=%!"

REM Dar tiempo a que el servidor se inicie
timeout /t 2 /nobreak > nul

REM Lanzar dos instancias del jugador
start /b java Jugador > jugador1.log 2>&1
set "jugador1_pid=%!"

start /b java Jugador > jugador2.log 2>&1
set "jugador2_pid=%!"

REM Dar tiempo a que los jugadores se conecten
timeout /t 2 /nobreak > nul

REM Mostrar los pids de los procesos lanzados
echo PID de Basededatos: %basededatos_pid%
echo PID de Servidor: %servidor_pid%
echo PID de Jugador 1: %jugador1_pid%
echo PID de Jugador 2: %jugador2_pid%

REM Esperar a que el proceso de la base de datos termine
wait %basededatos_pid%

REM Eliminar todos los archivos .class después de que terminen los procesos
del /q *.class

REM Matar los otros procesos si es necesario
taskkill /PID %servidor_pid% /F
taskkill /PID %jugador1_pid% /F
taskkill /PID %jugador2_pid% /F

echo Todos los archivos .class han sido eliminados.
