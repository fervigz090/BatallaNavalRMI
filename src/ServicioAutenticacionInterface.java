import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {

    boolean login(String name, String password) throws RemoteException;

    void registrar(String name, String password) throws RemoteException;

}
