import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioGestorInterface extends Remote {

    Tablero crearTablero() throws RemoteException;

    void colocarBarcos(Jugador jugador, Tablero tablero) throws RemoteException;

    void iniciarPartida(Jugador jugador1, Jugador jugador2,
                        Tablero tablero1, Tablero tablero2) throws RemoteException;

    void colocarFicha(Jugador jugador, Tablero tablero, char letra, char numero) throws RemoteException;

    boolean partidaFinalizada(Tablero tablero1, Tablero tablero2) throws RemoteException;



}
