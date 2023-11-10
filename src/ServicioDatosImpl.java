import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private HashMap<String, String> RegistroJugadores = new HashMap<>();

    protected ServicioDatosImpl() throws RemoteException {
        super();
    }

    @Override
    public int size() throws RemoteException {
        return RegistroJugadores.size();
    }

    @Override
    public void setUser(String name, String password) throws RemoteException {
        RegistroJugadores.put(name, password);
    }

    @Override
    public boolean existe(String name) throws RemoteException {
        return RegistroJugadores.containsKey(name);
    }

    @Override
    public String getPass(String name) throws RemoteException {
        return RegistroJugadores.get(name);
    }

    @Override
    public void deleteUser(String name) throws RemoteException {
        RegistroJugadores.remove(name);
    }
}
