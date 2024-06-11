import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Este servicio se encarga de gestionar todas las operaciones de los jugadores. Crea
 * todas las estructuras de datos necesarias para jugar la partida, gestiona la
 * logica de juego, informa a los jugadores del fin de partida, puntuiaciones, etc.
 */
public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

    private ServicioDatosInterface servicioDatos;
    private Map<String, CallbackJugadorInterface> ListaCallBack = new HashMap<>();

    protected ServicioGestorImpl() throws RemoteException {
        super();
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
        Jugador j1 = p.getJugador1();
        Jugador j2 = p.getJugador2();
        Tablero t1 = p.getTablero1();
        Tablero t2 = p.getTablero2();
        CallbackJugadorInterface cBack1 = ListaCallBack.get(j1.getName());
        CallbackJugadorInterface cBack2 = ListaCallBack.get(j2.getName());

        try {
            cBack1.turnoParaDisparar();
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
