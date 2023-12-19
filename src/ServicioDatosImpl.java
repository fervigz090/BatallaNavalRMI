import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private HashMap<String, Jugador> RegistroJugadores = new HashMap<>();
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
    public void setUser(Jugador jugador) throws RemoteException {
        RegistroJugadores.put(jugador.getName(), jugador);
    }

    @Override
    public Jugador getUser(Jugador jugador) throws RemoteException{
        return RegistroJugadores.get(jugador.getName());
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

    @Override
    public void listaJugadores() throws RemoteException {
        for (HashMap.Entry<String, Jugador> entry : RegistroJugadores.entrySet()) {
            System.out.printf("%s -> %d puntos\n", entry.getKey(), 3546);
        }
    }

    @Override
    public int obtenerPuntuacion(String name) throws RemoteException {
        int puntuacion = 0;
        puntuacion = RegistroJugadores.get(name).getPuntuacion();
        return 0;
    }
}
