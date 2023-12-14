import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioDatosInterface extends Remote {

    int size() throws RemoteException;

    void setUser(String name, String password) throws RemoteException;

    String getUser(String name) throws RemoteException;

    boolean existe(String name) throws RemoteException;

    String getPass(String name) throws RemoteException;

    void deleteUser(String name) throws RemoteException;

    void setPartida(Jugador j1, Jugador j2, Tablero t1, Tablero t2) throws RemoteException;

    Partida getPartida(int index) throws RemoteException;

    void borrarPartida(int index) throws RemoteException;

    int numeroPartidas() throws RemoteException;

    Partida[] listaPartidas() throws RemoteException;

    void listaJugadores() throws RemoteException;

}
