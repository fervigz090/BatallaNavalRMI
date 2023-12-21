import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioGestorInterface extends Remote {

    Tablero crearTablero() throws RemoteException;

    void colocarBarcos(Jugador jugador, Tablero tablero) throws RemoteException;

    Partida iniciarPartida(Jugador jugador1, Tablero tablero1, Tablero tablero2) throws RemoteException;

    void colocarFicha(Jugador jugador, Tablero tablero, char letra, char numero) throws RemoteException;

    boolean partidaFinalizada(Tablero tablero1, Tablero tablero2) throws RemoteException;

    StringBuilder obtenerPuntuacion(String name) throws RemoteException;

    StringBuilder obtenerTablero(Partida p, String name) throws RemoteException;

    void actualizarTablero(Partida p, Tablero t, int numJugador) throws RemoteException;

}
