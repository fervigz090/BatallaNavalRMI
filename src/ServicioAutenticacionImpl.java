import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

    private ServicioDatosInterface servicioDatos;

    protected ServicioAutenticacionImpl() throws RemoteException {
        super();
        try {
            servicioDatos  = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println(e.toString());
        }
    }

        @Override
        public boolean login (String name, String password) throws RemoteException {
            try {
                if (servicioDatos.existe(name)) {
                    return servicioDatos.getPass(name).equals(password);
                } else {
                    return false;
                }
            } catch (RemoteException e){
                System.err.println(e.toString());
            }
                return false;
        }

        @Override
        public void registrar (Jugador jugador) throws RemoteException {
        servicioDatos.setUser(jugador);
    }

}
