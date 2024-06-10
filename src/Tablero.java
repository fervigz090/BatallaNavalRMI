import java.io.Serializable;

public class Tablero implements Serializable {
    private int filas;
    private int columnas;
    private char[][] casillas;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new char[filas][columnas];
        inicializarTablero();
    }

    // Inicializa el tablero con espacios en blanco
    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = ' ';
            }
        }
    }

    // Devuelve un StringBuilder con el tablero
    public StringBuilder mostrarTablero() {
        StringBuilder st = new StringBuilder();
        st.append("  ");
        for (int i = 1; i <= columnas; i++) {
            st.append(i + " ");
        }
        st.append("\n");

        for (int i = 0; i < filas; i++) {
            st.append((char) ('A' + i) + " ");
            for (int j = 0; j < columnas; j++) {
                st.append(casillas[i][j] + " ");
            }
            st.append("\n");
        }
        return st;
    }

    // Obtener el contenido de una casilla específica
    public char obtenerCasilla(char fila, int columna) {
        int rowIndex = fila - 'A';
        int columnIndex = columna - 1;
        if (rowIndex >= 0 && rowIndex < filas && columnIndex >= 0 && columnIndex < columnas) {
            return casillas[rowIndex][columnIndex];
        } else {
            throw new IllegalArgumentException("Coordenadas fuera del tablero.");
        }
    }

    // Establecer el contenido de una casilla específica
    public Boolean establecerCasilla(int filaIndex, int columna, char contenido) {
        if (filaIndex >= 0 && filaIndex < filas && columna >= 0 && columna < columnas) {
            casillas[filaIndex][columna] = contenido;
            return true;
        } else {
            System.out.println("coordenadas: " + filaIndex + " - " + columna);
            throw new IllegalArgumentException("Coordenadas fuera del tablero.");
        }
    }
}

