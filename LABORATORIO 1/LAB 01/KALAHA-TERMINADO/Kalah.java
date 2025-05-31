
/**
 * La clase Kalah intenta hacer el juego de kalah americano con un par de
 * particularidades, donde destaco que se inicia con 3 semillas en cada hoyo,
 * Para almacenar los hoyos usamos una lista para facilitar la 
 * ejecucion y logica del juego.
 * 
 * @Anderson Fabian Garcia Nieto y Christian Alfonso Romero Martinez
 * @version (00.00.00.01)
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Kalah {
    private List<Pit> pits;
    private Pit almacenNorth;
    private Pit almacenSouth;
    private int turno;

    public Kalah() {
        iniciarJuego();
    }

    /**
     * Constructor del tablero con 6 hoyos para cada jugador, divididos
     * en norte y sur, cada uno cuenta con un almacén.
     */
    public void iniciarJuego() {
        pits = new ArrayList<>();

        // Jugador North
        for (int i = 5; i >= 0; i--) {
            Pit pit = new Pit(false);
            pit.putSeeds(3);
            pit.moveTo(150 + i * 120, 100);
            pit.changeColors("blue", "black");
            pit.makeVisible();
            pits.add(pit);
        }

        // Almacén Norte
        almacenNorth = new Pit(false);
        almacenNorth.moveTo(20, 170);
        almacenNorth.changeColors("red", "black");
        almacenNorth.makeVisible();
        pits.add(almacenNorth);

        // Jugador Sur
        for (int i = 0; i < 6; i++) {
            Pit pit = new Pit(false);
            pit.putSeeds(3);
            pit.moveTo(150 + i * 120, 250);
            pit.changeColors("green", "black");
            pit.makeVisible();
            pits.add(pit);
        }

        // Almacén Sur
        almacenSouth = new Pit(false);
        almacenSouth.moveTo(870, 170);
        almacenSouth.changeColors("yellow", "black");
        almacenSouth.makeVisible();
        pits.add(almacenSouth);

        turno = 1;
        JOptionPane.showMessageDialog(null, "Turno del jugador: " + getTurno());
    }

    /**
     * Regresa el turno actual.
     */
    public int getTurno() {
        return turno;
    }

    /**
     * Hace el cambio de turno entre jugadores.
     */
    public void cambio() {
        turno = (turno == 1) ? 2 : 1;
    }

    /**
     * Ejecuta los movimientos, turnos y distribución de las semillas.
     */
    public void makeMove(int pitI) {
        if (pitI < 1 || pitI > 6) {
            JOptionPane.showMessageDialog(null, "El hoyo no existe. Elija entre 1 y 6.");
            return;
        }

        int index = (turno == 1) ? pitI - 1 : pitI + 6;
        Pit selectedPit = pits.get(index);
        int seedsToDistribute = selectedPit.seeds();

        if (seedsToDistribute == 0) {
            JOptionPane.showMessageDialog(null, "No hay semillas. Elija otro hoyo.");
            return;
        }

        selectedPit.removeSeeds(seedsToDistribute);
        int pos = index;

        while (seedsToDistribute > 0) {
            pos = (pos + 1) % pits.size();

            // Evita el almacén del oponente
            if ((turno == 1 && pos == 13) || (turno == 2 && pos == 6)) {
                continue;
            }

            pits.get(pos).putSeeds(1);
            pits.get(pos).moveTo(0 , 0);
            seedsToDistribute--;
        }
        

        if (pits.get(pos).seeds() == 1 && ((turno == 1 && pos < 6) || (turno == 2 && pos > 6 && pos < 13))) {
            int oppositeIndex = 12 - pos; 
            Pit oppositePit = pits.get(oppositeIndex);
        
            if (oppositePit.seeds() > 0) {
                int stolenSeeds = oppositePit.seeds();
                oppositePit.removeSeeds(stolenSeeds);
                pits.get(pos).removeSeeds(1);
        

                if (turno == 1) {
                    almacenNorth.putSeeds(stolenSeeds + 1);
                } else {
                    almacenSouth.putSeeds(stolenSeeds + 1);
                }
        
                JOptionPane.showMessageDialog(null, "¡Captura! El jugador " + turno + " roba " + stolenSeeds + " semillas del oponente.");
            }
        }

        

        if ((turno == 1 && pos == 6) || (turno == 2 && pos == 13)) {
            JOptionPane.showMessageDialog(null, "El jugador "+ turno + " tiene un turno extra" + getTurno());
            return;
        }

        mostrarEstadoTablero();
        verificarVictoria();
        cambio();
        JOptionPane.showMessageDialog(null, "Turno del jugador: " + getTurno());
    }

    /**
     * Verifica si hay un ganador.
     */
    public void verificarVictoria() {
        boolean norteVacio = true;
        boolean surVacio = true;

        for (int i = 0; i < 6; i++) {
            if (pits.get(i).seeds() > 0) {
                norteVacio = false;
            }
            if (pits.get(i + 7).seeds() > 0) {
                surVacio = false;
            }
        }

        if (norteVacio || surVacio) {
            for (int i = 0; i < 6; i++) {
                almacenNorth.putSeeds(pits.get(i).seeds());
                pits.get(i).removeSeeds(pits.get(i).seeds());
            }
            for (int i = 7; i < 13; i++) {
                almacenSouth.putSeeds(pits.get(i).seeds());
                pits.get(i).removeSeeds(pits.get(i).seeds());
            }

            String ganador = (almacenNorth.seeds() > almacenSouth.seeds()) ? "North" : "South";
            JOptionPane.showMessageDialog(null, "El jugador " + ganador + " gana con " + 
            Math.max(almacenNorth.seeds(), almacenSouth.seeds()) + " semillas.");
        }
    }

    /**
     * Reinicia el juego.
     */
    public void reiniciarJuego() {
        int respuesta = JOptionPane.showConfirmDialog(null, "Confirme una nueva partida", "Reiniciar Juego", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            for (Pit pit : pits) {
                pit.removeSeeds(pit.seeds());
            }
            iniciarJuego();
        }
    }

    /**
     * Muestra el estado actual del tablero en la consola.
     */
    public void mostrarEstadoTablero() {
        String estado = "Estado del tablero:\n";
        estado += "North:  ";
        for (int i = 5; i >= 0; i--) {
            estado += pits.get(i).seeds() + " ";
        }
        estado += " | Almacén North: " + almacenNorth.seeds() + "\n";

        estado += "Sur:    ";
        for (int i = 7; i < 13; i++) {
            estado += pits.get(i).seeds() + " ";
        }
        estado += " | Almacén South: " + almacenSouth.seeds() + "\n";

        estado += "Turno del jugador: " + getTurno();
        JOptionPane.showMessageDialog(null, estado);
    }
}

