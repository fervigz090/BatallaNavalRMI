import java.io.Serializable;

public class Partida implements Serializable {
    private int id;
    private Jugador jugador1;
    private Jugador jugador2;
    private  Tablero tablero1;
    private Tablero tablero2;
    private enum estadoPartida {EN_ESPERA, EN_CURSO, PAUSADA, TERMINADA}
    private estadoPartida estadoActual;

    public Partida(Jugador jugador1, Tablero t1, Tablero t2){
        this.id += 1;
        this.jugador1 = jugador1;
        this.tablero1 = t1;
        this.tablero2 = t2;
        this.estadoActual = estadoPartida.EN_ESPERA;
    }

    public int getId() {
        return id;
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

    public estadoPartida getEstadoActual() {
        return estadoActual;
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

    public void setEstadoActual(estadoPartida estadoActual) {
        this.estadoActual = estadoActual;
    }
}
