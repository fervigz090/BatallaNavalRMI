import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServicioGestorInterface extends Remote {

    void conectarCback (String nombre) throws RemoteException;

    Tablero crearTablero() throws RemoteException;

    void colocarBarcos(Jugador jugador, Tablero tablero) throws RemoteException;

    Partida iniciarPartida(Jugador jugador1, Tablero tablero1, Tablero tablero2) throws RemoteException;

    ArrayList<Partida> listarPartidas() throws RemoteException;

    Partida obtenerPartida(int idPartida) throws RemoteException;

    void Rondas(Partida p) throws RemoteException;

    String realizarDisparo(Jugador jugador, Tablero tablero, String coordenadas) throws RemoteException;

    boolean partidaFinalizada(Tablero tablero1, Tablero tablero2) throws RemoteException;

    StringBuilder obtenerPuntuacion(String name) throws RemoteException;

    StringBuilder obtenerTablero(Partida p, String name) throws RemoteException;

    void actualizarTablero(Partida p, Tablero t, int numJugador) throws RemoteException;

    void actualizarPartida(int id, Partida p) throws RemoteException;

    void comprobarPartidas() throws RemoteException;

}
