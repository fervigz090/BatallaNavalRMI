import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackJugadorInterface extends Remote {

    void partidaIniciada() throws RemoteException;

    void turnoParaDisparar() throws RemoteException;

    void resultadoDisparoOponente(boolean impacto, boolean hundido, char fila, int columna) throws RemoteException;

    void hasGanado() throws RemoteException;

    void hasPerdido() throws RemoteException;

}
