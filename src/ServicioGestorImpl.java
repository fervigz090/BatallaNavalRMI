import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;


/**
 * Este servicio se encarga de gestionar todas las operaciones de los jugadores. Crea
 * todas las estructuras de datos necesarias para jugar la partida, gestiona la
 * logica de juego, informa a los jugadores del fin de partida, puntuiaciones, etc.
 */
public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

    private ServicioDatosInterface servicioDatos;
    private Map<String, CallbackJugadorInterface> ListaCallBack;

    protected ServicioGestorImpl() throws RemoteException {
        super();
        ListaCallBack = new HashMap<>();
        try {
            servicioDatos  = (ServicioDatosInterface) Naming.lookup("rmi://localhost/servicioDatos");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            System.err.println(e.toString());
        }
    }

    public void conectarCback (String nombre) throws RemoteException {
        try {
            CallbackJugadorInterface cBack = (CallbackJugadorInterface) Naming.lookup("rmi://localhost/cBackJugador/" + nombre);
            ListaCallBack.put(nombre, cBack);
        } catch (Exception e) {
            System.err.println("Error conectando a cBack de " + nombre);
            e.printStackTrace();
        }
    }

    @Override
    public Tablero crearTablero() throws RemoteException {
        return new Tablero(10, 10);
    }

    @Override
    public void colocarBarcos(Jugador jugador, Tablero tablero) throws RemoteException {

    }

    @Override
    public Partida iniciarPartida(Jugador jugador1, Tablero tablero1, Tablero tablero2) throws RemoteException {
        Partida p = new Partida(jugador1, tablero1, tablero2);
        servicioDatos.setPartida(p);
        return p;
    }

    @Override
    public void Rondas(Partida p) throws RemoteException {
        Partida pAux = servicioDatos.getPartida(p.getId());
        conectarCback(pAux.getJugador1().getName());
        conectarCback(pAux.getJugador2().getName());
        String coordenadas = " ";
        Jugador j1 = pAux.getJugador1();
        Jugador j2 = pAux.getJugador2();
        Tablero t1 = pAux.getTablero1();
        Tablero t2 = pAux.getTablero2();
        CallbackJugadorInterface cBack1 = ListaCallBack.get(j1.getName());

        if (j2 == null) {
            throw new NullPointerException("Jugador 2 es null");
        }
        if (t1 == null) {
            throw new NullPointerException("Tablero de Jugador 1 es null");
        }
        if (t2 == null) {
            throw new NullPointerException("Tablero de Jugador 2 es null");
        }
        if (cBack1 == null) {
            throw new NullPointerException("Callback de Jugador 1 es null");
        }

        try {
            coordenadas = cBack1.turnoParaDisparar();
            System.out.println("Coordenadas: " + coordenadas);
        } catch (RemoteException e) {
            System.err.println("Error en inicioRondas: 116: ServicioGestorImpl");
            e.printStackTrace();
        }
    }

    @Override
    public void realizarDisparo(Jugador jugador, Tablero tablero, char letra, char numero) throws RemoteException {

    }

    @Override
    public boolean partidaFinalizada(Tablero tablero1, Tablero tablero2) throws RemoteException {
        return false;
    }

    @Override
    public StringBuilder obtenerPuntuacion(String name) throws RemoteException {
        StringBuilder p = new StringBuilder();
        p.append("Jugador -> " + name + "\n");
        p.append("Puntuación maxima -> " + String.valueOf(servicioDatos.obtenerPuntuacion(name)) + "\n");
        return p;
    }

    @Override
    public StringBuilder obtenerTablero(Partida p, String name) throws RemoteException {
        StringBuilder t = new StringBuilder();
        if (p.getJugador1().getName().equals(name)) {
            t = p.getTablero1().mostrarTablero();
        } else if (p.getJugador2().getName().equals(name)) {
            t = p.getTablero2().mostrarTablero();
        } else {
            t.append("Error localizando el tablero: 57: ServicioGestorImpl");
        }
        return t;
    }

    @Override
    public void actualizarTablero(Partida p, Tablero t, int numJugador) throws RemoteException {
        switch (numJugador) {
            case 1: p.setTablero1(t); break;
            case 2: p.setTablero2(t); break;
        }
    }

    @Override
    public ArrayList<Partida> listarPartidas () throws RemoteException {
        return servicioDatos.listaPartidas();
    }

    @Override
    public Partida obtenerPartida(int idPartida) throws RemoteException {
        return servicioDatos.getPartida(idPartida);
    }

    @Override
    public Partida asignarJugador2(int idPartida, Jugador jugador) throws RemoteException {
        Partida p = servicioDatos.getPartida(idPartida);
        if (p == null){
            System.err.println("Error: id " + idPartida + " Partida incorrecto");
        } else {
            p.setJugador2(jugador);
            actualizarPartida(idPartida, p);
        }
        return servicioDatos.getPartida(idPartida);
    }

    public void actualizarPartida(int idPartida, Partida p) throws RemoteException {
        servicioDatos.borrarPartida(idPartida);
        servicioDatos.setPartida(p);
    }


}
