import java.awt.Button;
import java.awt.TextArea;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class CallbackJugadorImpl extends UnicastRemoteObject implements CallbackJugadorInterface{

    Jugador jugador;
    ServicioGestorInterface servicioGestor;

    protected CallbackJugadorImpl() throws RemoteException {
        super();
        try {
            Registry registry = LocateRegistry.getRegistry();
            servicioGestor = (ServicioGestorInterface) registry.lookup("servicioGestor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setJugador(Jugador jugador) throws RemoteException {
        this.jugador = jugador;
    }

    @Override
    public Jugador getJugador() throws RemoteException {
        return jugador;
    }

    @Override
    public void partidaIniciada() throws RemoteException {
        System.out.println("La partida ha comenzado!");

        //Añadir logica del juego para manejar inicio de partida en el cliente.

    }

    @Override
    public String turnoParaDisparar() throws RemoteException {
        System.out.println("Es tu turno para disparar!");
        final String[] coordenadas = new String[1];

        // final Runnable disparar = new Runnable() {
        //     public String getCoordenadas() {
        //         return jugador.getCoordenadas();
        //     }
        //     @Override
        //     public void run() {
        //         coordenadas[0] = JOptionPane.showInputDialog(null, jugador.getName() + ": Introduce las coordenadas para disparar (ejemplo: A1) ");
        //         jugador.setCoordenadas(coordenadas[0]);
        //         System.out.println("Coordenadas introducidas (1): " + coordenadas[0]);
        //     }
        // };

        // // Crear un hilo para pedir las coordenadas
        // Thread pedirCoordenadas = new Thread(){
        //     public void run(){
        //         try {
        //             SwingUtilities.invokeAndWait(disparar);
        //         } catch (InvocationTargetException e) {
        //             e.printStackTrace();
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // };
        // pedirCoordenadas.start();

        // // // Volver a unir los hilos
        // // try {
        // //     pedirCoordenadas.join(1000);
        // // } catch (InterruptedException e) {
        // //     e.printStackTrace();
        // // }

        // while (jugador.getCoordenadas() == null) {
        //     try {
        //         Thread.sleep(1000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
        // coordenadas[0] = jugador.getCoordenadas();

        // System.out.println("Coordenadas introducidas (2): " + coordenadas[0]);

        return coordenadas[0];

    }

    @Override
    public void resultadoDisparoOponente(boolean impacto, boolean hundido, char fila, int columna) throws RemoteException {
        if(impacto){
            System.out.println("Has dado en el blanco! " + fila + columna);
            if(hundido){
                System.out.println("Has hundido un barco del oponente!");
            }
        } else {
            System.out.println("Agua!" + fila + columna);
        }

        //logica del resultado del disparo del oponente en el cliente

    }

    @Override
    public void hasGanado() throws RemoteException {
        System.out.println("Has ganado la partida!");

        //Logica para cuando el jugador gana la partida

    }

    @Override
    public void hasPerdido() throws RemoteException {
        System.out.println("Has perdido la partida!");

        //Logica para cuando el jugador pierde la partida.

    }
}
