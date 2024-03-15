package motor;

/**
 * Representa un tablero del juego Buscaminas como una grilla de objetos de tipo
 * {@link Celda}. Se definen además las constantes que se usarán para definir los
 * distintos estados del juego.
 * @author DeathbatO
 */
public class Tablero {
        //Valores predefinidos para las dimensiones maximas y minimas del tablero
    public static byte
        MIN_ANCHO=5,
        MIN_LARGO=5,
        MAX_ANCHO=100,
        MAX_LARGO=100; 

    //El tablero: una grilla con topes (tal como hicimos en el Juego de la Vida).
    private final Celda[][] celdas;
    private int cantidadMinas;
    
    /**
     * Crea un nuevo tablero con las dimensiones indicadas. Las celdas del mismo
     * estarán todas OCULTAS y sin minas. Las cantidades de filas y columnas deben
     * ser mayores que MIN_ANCHO y MIN_LARGO respectivamente, de lo contrario se
     * establecerán con dichos valores.
     * @param cantidadFilas La cantidad de filas que contendrá el tablero.
     * @param cantidadColumnas La cantidad de columnas que contendrá el tablero.
     */
    public Tablero(byte cantidadFilas, byte cantidadColumnas){
        
    }
    
    /**
     * Indica el largo (cantidad de columnas) del tablero.
     * @return La cantidad de columnas en el tablero.
     */
    public byte getLargo(){
        
    }
    
    /**
     * Indica el ancho (cantidad de filas) del tablero.
     * @return La cantidad de filas del tablero.
     */
    public byte getAncho(){
        
    }
    
    /**
     * Indica si una posición es válida para el tablero.
     * @param fila La posición de la fila (la primera fila es 0).
     * @param columna La posición de la columna (la primera columna es 0).
     * @return TRUE si la posición es adecuada en el tablero, FALSE si no lo es.
     */
    public boolean esPosicionValida(byte fila, byte columna){
         
    }
    
    /**
     * Si se pasa TRUE en tieneBomba se establecerá la celda actual como una celda
     * con mina (salvo que ésta ya estuviera marcada así); en tal caso se sumará
     * 1 bomba circundante a todas las celdas alrededor de la posición dada. Si se
     * pasa FALSE en tieneBomba se establecerá la celda actual como una celda sin
     * mina (salvo que ya estuviera marcada así); en tal caso se restará 1 bomba
     * circundante a todas las celdas alrededor de la posición dada.<br><br>
     * <b>PRECONDICIÓN:</b> La posicíon dada {@link esPosicionValida}.
     * @param tieneBomba TRUE para indicar que la celda contiene bomba, FALSE si no.
     * @param fila La fila en que está la celda.
     * @param columna La columna en que está la celda.
     */
    public void asignarBomba(boolean tieneBomba, byte fila, byte columna){
        
    }
    
    /**
     * Retorna la celda en la posición dada.<br><br>
     * <b>PRECONDICIÓN</b>: La posición {@link esPosicionValida}.
     * @param fila La fila de la celda buscada.
     * @param columna La columna de la celda buscada.
     * @return La celda en la posición dada.
     */
    public Celda getCelda(byte fila, byte columna){
        
    }
    
    /**
     * Indica la cantidad total de celdas existentes en el tablero.
     * @return La cantidad de celdas en el tablero.
     */
    public int getCantidadCeldas(){
        
    }
    
    /**
     * Indica la cantidad de minas en el tablero.
     * @return La cantidad de minas en el tablero.
     */
    public int getCantidadMinas(){
        
    }
}