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

    // Imprime el tablero en la consola
    public void mostrarTablero() {
        System.out.print("  ");
        for (int i = 1; i <= columnas; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < filas; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < columnas; j++) {
                System.out.print(casillas[i][j] + " ");
            }
            System.out.println();
        }
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
    public void establecerCasilla(char fila, int columna, char contenido) {
        int rowIndex = fila - 'A';
        int columnIndex = columna - 1;
        if (rowIndex >= 0 && rowIndex < filas && columnIndex >= 0 && columnIndex < columnas) {
            casillas[rowIndex][columnIndex] = contenido;
        } else {
            throw new IllegalArgumentException("Coordenadas fuera del tablero.");
        }
    }

    public static void main(String[] args) {
        Tablero tablero = new Tablero(10, 10);
        tablero.mostrarTablero();

        // Establecer el contenido de una casilla (por ejemplo, A1) como 'X'
        tablero.establecerCasilla('A', 1, 'X');

        System.out.println("\nTablero después de establecer una casilla:");
        tablero.mostrarTablero();
    }
}

