public class Partida {
    private Jugador jugador1;
    private Jugador jugador2;
    private  Tablero tablero1;
    private Tablero tablero2;

    public Partida(Jugador jugador1, Jugador jugador2){
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero1 = new Tablero(10, 10);
        this.tablero2 = new Tablero(10, 10);
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Tablero getTablero1() {
        return tablero1;
    }

    public Tablero getTablero2() {
        return tablero2;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public void setTablero1(Tablero tablero) {
        this.tablero1 = tablero;
    }

    public void setTablero2(Tablero tablero2) {
        this.tablero2 = tablero2;
    }
}
