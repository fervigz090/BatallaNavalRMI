import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

    private ServicioDatosInterface sD;

    protected ServicioAutenticacionImpl() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        try {
            sD  = (ServicioDatosInterface) Naming.lookup("rmi://localhost/sD");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println(e.toString());
        }
    }

        @Override
        public boolean login (String name, String password) throws RemoteException {
            try {
                if (sD.existe(name)) {
                    return sD.getPass(name).equals(password);
                } else {
                    return false;
                }
            } catch (RemoteException e){
                System.err.println(e.toString());
            }
                return false;
        }

        @Override
        public void registrar (String name, String password) throws RemoteException {
        sD.setUser(name, password);
    }

}
