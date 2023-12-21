import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private HashMap<String, Jugador> RegistroJugadores = new HashMap<>();
    // private HashMap<String, Integer> Puntuaciones = new HashMap<>();
    private ArrayList<Partida> RegistroPartidas = new ArrayList<>();

    protected ServicioDatosImpl() throws RemoteException {
        super();
    }

    @Override
    public int size() throws RemoteException {
        return RegistroJugadores.size();
    }

    @Override
    public void setUser(Jugador jugador) throws RemoteException {
        RegistroJugadores.put(jugador.getName(), jugador);
    }

    @Override
    public Jugador getUser(String name) throws RemoteException{
        return RegistroJugadores.get(name);
    }

    @Override
    public boolean existe(String name) throws RemoteException {
        return RegistroJugadores.containsKey(name);
    }

    @Override
    public String getPass(String name) throws RemoteException {
        return RegistroJugadores.get(name).getPassword();
    }

    @Override
    public void deleteUser(String name) throws RemoteException {
        RegistroJugadores.remove(name);
    }

    public void setPartida(Partida p){
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

    public ArrayList<Partida> listaPartidas(){
        Partida[] lista = new Partida[RegistroPartidas.size()];
        for(int i=0; i<lista.length; i++){
            lista[i] = RegistroPartidas.get(i);
        }
        return RegistroPartidas;
    }

    @Override
    public ArrayList<Jugador> listaJugadores() throws RemoteException {
        ArrayList<Jugador> l = new ArrayList<>();
        for (HashMap.Entry<String, Jugador> entry : RegistroJugadores.entrySet()) {
            l.add(entry.getValue());
        }
        return l;
    }

    @Override
    public int obtenerPuntuacion(String name) throws RemoteException {
        int puntuacion = 0;
        puntuacion = RegistroJugadores.get(name).getPuntuacion();
        return 0;
    }
}
