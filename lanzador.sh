#!/bin/bash
# Lanzador de la aplicación
# Autor: Iván Fernández Rodríguez

cd src || { echo "No se pudo cambiar al directorio src"; exit 1; }

# Eliminar los logs anteriores
rm -f *.log

# Compilar todos los archivos .java
javac *.java || { echo "Error de compilación"; exit 1; }

# Lanzar la base de datos
java Basededatos > basededatos.log 2>&1 &
basededatos_pid=$!

# Dar tiempo a que la base de datos se inicie
sleep 2

# Lanzar el servidor
java Servidor > servidor.log 2>&1 &
servidor_pid=$!

# Dar tiempo a que el servidor se inicie
sleep 2

# Lanzar dos instancias del jugador
java Jugador > jugador1.log 2>&1 &
jugador1_pid=$!

java Jugador > jugador2.log 2>&1 &
jugador2_pid=$!

# Dar tiempo a que los jugadores se conecten
sleep 2

# Mostrar los pids de los procesos lanzados
echo "PID de Basededatos: $basededatos_pid"
echo "PID de Servidor: $servidor_pid"
echo "PID de Jugador 1: $jugador1_pid"
echo "PID de Jugador 2: $jugador2_pid"

# Esperar a que el proceso de la base de datos termine
wait $basededatos_pid

# Eliminar todos los archivos .class después de que terminen los procesos
rm -f *.class

# Matar los otros procesos si es necesario
kill $servidor_pid
kill $jugador1_pid
kill $jugador2_pid

echo "Todos los archivos .class han sido eliminados."
