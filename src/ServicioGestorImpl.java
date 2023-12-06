import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Este servicio se encarga de gestionar todas las operaciones de los jugadores. Crea
 * todas las estructuras de datos necesarias para jugar la partida, gestiona la
 * logica de juego, informa a los jugadores del fin de partida, puntuiaciones, etc.
 */
public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

    protected ServicioGestorImpl() throws RemoteException {
        super();
    }

    @Override
    public Tablero crearTablero() throws RemoteException {
        return new Tablero(10, 10);
    }

    @Override
    public void colocarBarcos(Jugador jugador, Tablero tablero) throws RemoteException {

    }

    @Override
    public void iniciarPartida(Jugador jugador1, Jugador jugador2, Tablero tablero1, Tablero tablero2) throws RemoteException {

    }

    @Override
    public void colocarFicha(Jugador jugador, Tablero tablero, char letra, char numero) throws RemoteException {

    }

    @Override
    public boolean partidaFinalizada(Tablero tablero1, Tablero tablero2) throws RemoteException {
        return false;
    }
}
