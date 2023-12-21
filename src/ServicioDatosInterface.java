import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ServicioDatosInterface extends Remote {

    int size() throws RemoteException;

    void setUser(Jugador jugador) throws RemoteException;

    Jugador getUser(String name) throws RemoteException;

    boolean existe(String name) throws RemoteException;

    String getPass(String name) throws RemoteException;

    void deleteUser(String name) throws RemoteException;

    void setPartida(Partida p) throws RemoteException;

    Partida getPartida(int index) throws RemoteException;

    void borrarPartida(int index) throws RemoteException;

    int numeroPartidas() throws RemoteException;

    ArrayList<Partida> listaPartidas() throws RemoteException;

    ArrayList<Jugador> listaJugadores() throws RemoteException;

    int obtenerPuntuacion(String name) throws RemoteException;

}
