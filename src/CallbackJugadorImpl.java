import java.rmi.RemoteException;

public class CallbackJugadorImpl implements CallbackJugadorInterface{
    @Override
    public void partidaIniciada() throws RemoteException {

    }

    @Override
    public void turnoParaDisparar() throws RemoteException {

    }

    @Override
    public void resultadoDisparoOponente(boolean impacto, boolean hundido, char fila, int columna) throws RemoteException {

    }

    @Override
    public void hasGanado() throws RemoteException {

    }

    @Override
    public void hasPerdido() throws RemoteException {

    }
}
