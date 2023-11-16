public class Probas {
    public static void main(String[] args) {
        Basededatos bd = new Basededatos();

        Tablero tb = new Tablero(10, 10);


        tb.establecerCasilla('B', 3, '*');
        tb.establecerCasilla('B', 4, '*');
        tb.establecerCasilla('B', 5, 'X');
        tb.establecerCasilla('B', 6, '*');

        tb.establecerCasilla('D', 4, '*');
        tb.establecerCasilla('E', 4, '*');
        tb.establecerCasilla('F', 4, '*');
        tb.establecerCasilla('G', 4, '*');



        tb.mostrarTablero();

        

    }
}
