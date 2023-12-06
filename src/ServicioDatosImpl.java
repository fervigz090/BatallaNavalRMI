import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private HashMap<String, String> RegistroJugadores = new HashMap<>();
    // private HashMap<String, Integer> Puntuaciones = new HashMap<>();
    private List<Partida> RegistroPartidas = new ArrayList<>();

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
    public String getUser(String name) throws RemoteException{
        return RegistroJugadores.get(name);
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

    public void setPartida(Jugador j1, Jugador j2, Tablero t1, Tablero t2){
        Partida p = new Partida(j1, j2, t1, t2);
        RegistroPartidas.add(p);
    }

    public Partida getPartida(int index){
        return RegistroPartidas.get(index);
    }

    public void borrarPartida(int index){
        RegistroPartidas.remove(index);
    }

    public int numeroPartidas(){
        return RegistroPartidas.size();
    }

    public Partida[] listaPartidas(){
        Partida[] lista = new Partida[RegistroPartidas.size()];
        for(int i=0; i<lista.length; i++){
            lista[i] = RegistroPartidas.get(i);
        }
        return lista;
    }
}
