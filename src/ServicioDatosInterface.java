import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioDatosInterface extends Remote {

    int size() throws RemoteException;

    void setUser(String name, String password) throws RemoteException;

    boolean existe(String name) throws RemoteException;

    String getPass(String name) throws RemoteException;

    void deleteUser(String name) throws RemoteException;

}
