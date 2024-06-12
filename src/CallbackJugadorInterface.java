import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackJugadorInterface extends Remote {

    void setJugador(Jugador jugador) throws RemoteException;

    Jugador getJugador() throws RemoteException;

    void partidaIniciada() throws RemoteException;

    String turnoParaDisparar() throws RemoteException;

    void resultadoDisparoOponente(boolean impacto, boolean hundido, char fila, int columna) throws RemoteException;

    void hasGanado() throws RemoteException;

    void hasPerdido() throws RemoteException;

}
