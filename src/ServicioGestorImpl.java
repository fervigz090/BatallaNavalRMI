import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;


public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

    private ServicioDatosInterface servicioDatos;
    private Map<String, CallbackJugadorInterface> ListaCallBack;
    Thread thread;

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
    public void comprobarPartidas() {
        ArrayList<Partida> partidas = new ArrayList<>();
        
        while (true) {
            try {
                partidas = servicioDatos.listaPartidas();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            
            for (Partida p : partidas) {
                if (p.getEstadoActual().equals("EN_CURSO")) {
                    System.out.println("Ambos tableros listos, iniciando partida");
                    try {
                        Rondas(p);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        Jugador atacante = pAux.getJugador1();
        Jugador defensor = pAux.getJugador2();
        Jugador aux;
        Tablero tAux;
        Tablero t1 = p.getTablero1();
        Tablero t2 = p.getTablero2();
        CallbackJugadorInterface cBack1 = ListaCallBack.get(atacante.getName());

        System.out.println("Ronda (datos inicio): " + atacante.getName() + t1.mostrarTablero().toString() + " - " + defensor.getName() + t2.mostrarTablero().toString());

        if (defensor == null) {
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

        while (!partidaFinalizada(atacante)) {
            // Realizar disparo
            coordenadas = JOptionPane.showInputDialog(null, atacante.getName() + ": Introduce las coordenadas para disparar (ejemplo: A1) ");
            System.out.println("Coordenadas: " + coordenadas);

            // Disparar
            String resultado = realizarDisparo(atacante, t2, coordenadas);
            System.out.println(t1.mostrarTablero().toString());
            System.out.println(t2.mostrarTablero().toString());
            System.out.println("Resultado: " + resultado);
            JOptionPane.showMessageDialog(null, resultado);

            // Cambiar turno
            aux = atacante;
            atacante = defensor;
            defensor = aux;
            // Cambiar tablero
            tAux = t1;
            t1 = t2;
            t2 = tAux;
            // Actualizar partida
            p.setTablero1(t1);
            p.setTablero2(t2);
            // Actualizar partida en BD
            actualizarPartida(p.getId(), p);
        }
        p.set_terminada();
        JOptionPane.showMessageDialog(null, "Partida finalizada! el ganador es: " + atacante.getName());
        
    }

    @Override
    public String realizarDisparo(Jugador jugador, Tablero tablero, String coordenadas) throws RemoteException {
        String resultado = "";
        char fila = coordenadas.charAt(0); // Obtiene fila
        int filaIndex = fila - 'A' + 1; // Convierte fila a entero
        String columna = coordenadas.substring(1); // Obtiene columna
        int columnaIndex = Integer.parseInt(columna); // Convierte columna a entero

        if (tablero.obtenerCasilla(fila, columnaIndex) == 'O') {
            tablero.establecerCasilla(filaIndex - 1, columnaIndex - 1, 'X');
            jugador.setPuntuacion(jugador.getPuntuacion() + 1);
            resultado = "Impacto!";
        } else {
            tablero.establecerCasilla(filaIndex - 1, columnaIndex - 1, ' ');
            resultado = "Agua!";
        }

        return resultado;
    }

    @Override
    public boolean partidaFinalizada(Jugador j) throws RemoteException {
        if (j.getPuntuacion() == 6) {
            return true;
        }
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
        Partida pAux = servicioDatos.getPartida(p.getId());
        switch (numJugador) {
            case 1:
                p.setTablero1(t);
                p.setTablero2(pAux.getTablero2());
                break;
            case 2:
                p.setTablero2(t);
                p.setTablero1(pAux.getTablero1());
                break;
        }

        actualizarPartida(p.getId(), pAux);
    }

    @Override
    public ArrayList<Partida> listarPartidas () throws RemoteException {
        return servicioDatos.listaPartidas();
    }

    @Override
    public Partida obtenerPartida(int idPartida) throws RemoteException {
        return servicioDatos.getPartida(idPartida);
    }

    public void actualizarPartida(int idPartida, Partida p) throws RemoteException {
        servicioDatos.borrarPartida(idPartida);
        servicioDatos.setPartida(p);
        p = servicioDatos.getPartida(idPartida);
        try {
            System.out.println("Partida actualizada: " + p.getJugador1().getName() + p.getTablero1().toString() + " vs " + p.getJugador2().getName() + p.getTablero2().toString());
        } catch (NullPointerException e) {
            System.err.println("Error actualizando partida: " + e.toString());
        }
    }

}
