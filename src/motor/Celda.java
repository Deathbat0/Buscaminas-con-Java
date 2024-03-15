package motor;

/**
 * Representa una celda del juego Buscaminas, la cual puede contener una bomba,
 * estar OCULTA, MARCADA o DESCUBIERTA, y además conocer la cantidad de minas
 * (bombas) circundantes.
 * @author DeathbatO
 */
public class Celda {
    /**
     * Tipo que establece los posibles estados de una celda.
     */
    public static enum EstadoCelda {OCULTA,MARCADA,DESCUBIERTA}

    private boolean tieneBomba;
    private EstadoCelda estado;
    private byte bombasCircundantes;

    /**
     * Crea una nueva celda según los datos pasados como argumento.
     * @param tieneBomba Si es TRUE se interpreta que la celda tiene mina, si no, pues que no la tiene.
     * @param estado El estado actual de la celda.
     * @param bombasCircundantes La cantidad de bombas que hay alrededor de la celda.
     */
    public Celda(boolean tieneBomba, EstadoCelda estado, byte bombasCircundantes) {

    }

    /**
     * Indica si la celda tiene bomba (mina).
     * @return TRUE si la celda tiene bomba, FALSE si no.
     */
    public boolean tieneBomba() {

    }

    /**
     * La celda se marcará como celda con bomba si se pasa TRUE, o sin bomba si se pasa FALSE.
     * @param tieneBomba TRUE para indicar si tiene bomba, FALSE si no.
     */
    public void setTieneBomba(boolean tieneBomba) {

    }

    /**
     * Indica el estado de la celda.
     * @return El estado actual de la celda.
     */
    public EstadoCelda getEstado() {

    }

    /**
     * Asigna un nuevo estado a la celda.
     * @param estado El nuevo estado de la celda.
     */
    public void setEstado(EstadoCelda estado) {

    }

    /**
     * Retorna la cantdiad de bombas circundantes de la celda.
     * @return La cantidad de minas que hay alrededor de la celda.
     */
    public byte getBombasCircundantes() {

    }

    /**
     * Asigna la cantidad de bombas circundantes a la celda.
     * @param bombasCircundantes La cantidad de bombas circundantes.
     */
    public void setBombasCircundantes(byte bombasCircundantes) {

    }
}