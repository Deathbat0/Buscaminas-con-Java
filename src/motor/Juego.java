package motor;

import java.util.Random;

import motor.Celda.EstadoCelda;

/**
 * Representa al juego Buscaminas en toda su esencia.
 * @author DeathbatO
 */
public class Juego {
    public static byte 
        ANCHO_FACIL= 5,
        LARGO_FACIL= 10,
        MINAS_FACIL= 5,
        ANCHO_MEDIO= 10,
        LARGO_MEDIO= 20,
        MINAS_MEDIO= 35,
        ANCHO_DIFICIL= 20,
        LARGO_DIFICIL= 30,
        MINAS_DIFICIL= 70;
    
    //El estado del juego
    public static enum EstadoJuego {JUGANDO,GANADO,PERDIDO}

    //El tipo Juego, que unifica todos los anteriores.
    private EstadoJuego estado;
    private final Tablero tablero;
    private final int bombas;          // cantidad de bombas en el tablero 
    private int marcadas;        // cantidad de celdas marcadas 
    private int descubiertas;    // cantidad de celdas descubiertas 
    private Random rd = new Random();
    
    /**
     * Crea un nuevo juego con estado JUGANDO, las dimensiones de tablero dadas
     * y la cantidad de bombas indicada. Las dimensiones de fila y columna 
     * no pueden ser menores a {@link Tablero.MIN_ANCHO} y {@link Tablero.MIN_LARGO}
     * respectivamente ni mayores a {@link Tablero.MAX_ANCHO} y {@link Tablero.MAX_LARGO}.
     * En caso de que los valores indicados no respeten estos largos se establecerán
     * en los valores mínimos. La cantidad de minas no puede superar la cantidad total
     * de celdas indicadas, lo cual es <b>cantidadFilas*cantidadColumnas</b>, y como
     * mínimo debe ser cero. Si no se respeta este rango se establecerá el valor en MINAS_FACIL. 
     * @param cantidadFilas La cantidad de filas, entre MIN_ANCHO y MAX_ANCHO.
     * @param cantidadColumnas La cantidad de columnas, entre MIN_LARGO y MAX_LARGO.
     * @param cantidadBombas La cantidad de minas, entre 0 y cantidadFilas*cantidadColumnas.
     */
    public Juego(byte cantidadFilas, byte cantidadColumnas, int cantidadBombas){
        this.estado = EstadoJuego.JUGANDO;
        this.tablero = new Tablero(cantidadFilas, cantidadColumnas);
        if(cantidadBombas <= 0 || cantidadBombas > cantidadColumnas*cantidadFilas){
        	this.bombas = MINAS_FACIL;
        }else{
        	this.bombas = cantidadBombas;
        }
        repartirMinas();
    }
    /**
     * Esta operación aumentará en 1 la cantidad de minas circundantes de todas las 
     * celdas alrededor de la posición dada
     * @param p - Posicion de la mina
     */
    private void aumentarMinasCircundantesAlRededor(Posicion p){
    	this.tablero.getCelda(p.getFila(), p.getColumna()).setBombasCircundantes(
    			(byte)(this.tablero.getCelda(p.getFila(), p.getColumna()).getBombasCircundantes()+1));
    }
    /**
     *  Se encargaría de repartir todas las minas al azar por el tablero. 
     *  Cada vez que una celda se marque con una mina, se invoca a la operación 
     *  {@link aumentarMinasCircundantesAlRededor} sobre la posición de la celda actua
     */
    private void repartirMinas(){
    	int bombtemp = this.bombas;
    	while(bombtemp > 0) {
    		int randomFila = rd.nextInt((int)this.tablero.getAncho());
    		int randomColumna = rd.nextInt((int)this.tablero.getLargo());
    		this.tablero.asignarBomba(this.tablero.getCelda((byte)randomFila, (byte)randomColumna).tieneBomba(), (byte)randomFila, (byte)randomColumna);
    		aumentarMinasCircundantesAlRededor(new Posicion((byte)randomFila,(byte)randomColumna));
    		bombtemp--;
    	}
    }
    
    public EstadoJuego getEstado(){
    	return this.estado;
    }
    /**
     * Retorna el estado de la celda en la posicón p dada.
     * <br><br><b>PRECONDICION:</b> La posicíon es válida.
     * @param p La posición de la celda cuyo estado se desea obtener.
     * @return El estado de la celda en la posicón p.
     */
    public Celda.EstadoCelda getEstadoCelda(Posicion p){
    	return this.getEstadoCelda(p);
    }
    
    /**
     * Indica si la celda en la posición dada tiene mina.<br><br>
     * <b>PRECONDICION</b>: La posición es válida.
     * @param p La posicíon de la celda a consultar.
     * @return TRUE si tiene mina, FALSE si no.
     */
    public boolean tieneBomba(Posicion p){
    	return this.tablero.getCelda(p.getFila(),p.getColumna()).tieneBomba();
    }
    
    /**
     * Indica la cantidad de minas que circundan a la posición dada.<br><br>
     * <b>PRECONDICIÓN</b>: La posición es válida.
     * @param p La posición de la celda cuya cantidad de circundantes se pretende conocer.
     * @return La cantidad de minas que circundan a la celda en cuestión.
     */
    public int getMinasCircundantes(Posicion p){
        return this.tablero.getCelda(p.getFila(), p.getColumna()).getBombasCircundantes();
    }
    
    /**
     * Retorna la cantidad de filas conque quedó configurado el tablero.
     * @return La cantidad de filas del tablero.
     */
    public byte getFilas(){
        return this.tablero.getAncho();
    }
    
    /**
     * Retorna la cantidad de columnas conque quedó configurado el tablero.
     * @return La cantidad de columnas.
     */
    public byte getColumnas(){
        return this.tablero.getLargo();
    }
    
    /**
     * Retorna la cantidad de minas en el tablero.
     * @return La cantidad de minas.
     */
    public int getMinas(){
        return this.bombas;
    }
    
    /**
     * Retorna la cantidad de celdas marcadas en el tablero.
     * @return La cantidad de celdas marcadas.
     */
    public int getCeldasMarcadas(){
        return this.marcadas;
    }
    
    /**
     * Retorna la cantidad de celdas descubiertas en el tablero.
     * @return La cantidad de celdas descubiertas.
     */
    public int getCeldasDescubiertas(){
        return this.descubiertas;
    }
    
    /**
     * También llamado Despejar. Esta operación ejecuta la acción de hacer
     * clic sobre una celda que está oculta y sin marcar. Si la celda en la
     * posición dada ya está DESCUBIERTA o está MARCADA no se hará nada. En
     * caso contrario se hará lo siguiente:<br><br>
     * <ul>
       <li>Si la celda tiene mina se marcará como DESCUBIERTA y el estado del
        juego cambiará a PERDIDO. Se aumentará en 1 el contador de celdas
        descubiertas.</li>
       <li>Si la celda no tiene mina pero tiene minas alrededor
        (bombasCircundantes>0) se marcará la celda como DESCUBIERTA. También
        se aumenta en 1 el contador de celdas descubiertas.</li>
       <li>Si la celda no tiene minas ni tampoco hay minas circundantes
        entonces se marcará la celda como DESCUBIERTA, se aumenta en 1 el
        contador de celdas descubiertas y se descubren todas las celdas
        alrededor de esta cuyo estado es OCULTA, repitiendo el proceso. Esto
        es complejo. Se sugiere implementar el algoritmo descrito en la letra
        del proyecto para llevar adelante esta tarea.</li>
     * </ul>
        Si al despejar celdas ya no quedan celdas sin minas entonces se
        cambiará el estado del juego a GANADO, ya que el jugador ganó.
     * @param p 
     */
	public void descubrir(Posicion p){
		byte fila, columna;
		fila = p.getFila();
		columna = p.getColumna();
        //Estado oculta
		if (this.tablero.getCelda(fila, columna).getEstado() == EstadoCelda.OCULTA){
        	//Tiene bomba
        	if(this.tablero.getCelda(fila, columna).tieneBomba()){
        		this.tablero.getCelda(fila, columna).setEstado(EstadoCelda.DESCUBIERTA);
        		this.estado = EstadoJuego.PERDIDO;
        		this.descubiertas++;
        	}
        	//tiene bombas circundantes
        	else if(this.tablero.getCelda(fila, columna).getBombasCircundantes() > 0){
        		this.tablero.getCelda(fila, columna).setEstado(EstadoCelda.DESCUBIERTA);
        		this.descubiertas++;
        	}
        	//No tiene minas circundantes
        	else if(this.tablero.getCelda(fila, columna).getBombasCircundantes() == 0){
        		this.tablero.getCelda(fila, columna).setEstado(EstadoCelda.DESCUBIERTA);
        		this.descubiertas++;
        		for(int i = fila-1; i <= fila+1; i++){
        			for(int j = columna-1; j <= columna+1; j++){
        				if(!this.tablero.getCelda((byte)i, (byte)j).tieneBomba()){
        					descubrir(new Posicion((byte)i, (byte)j));
        				}
        			}
        		}
        	}
        }
		if((this.tablero.getCantidadCeldas()-this.tablero.getCantidadMinas()) == this.descubiertas){
			this.estado = EstadoJuego.GANADO;
		}
		return;
    }

    /**
     * Aplica la acción de marcar sobre la celda en la posición dada. Si la
    posición no es correcta no se hace nada. Si la celda esta OCULTA se
    cambia su estado a MARCADA, en cualquier otro caso no se hace nada. Se
    aumenta la cuenta de celdas marcadas en 1.
     * @param p La posición de la celda que se pretende marcar.
     */
    public void marcar(Posicion p){
       	if(this.tablero.esPosicionValida(p.getFila(), p.getColumna())){
	    	if(this.tablero.getCelda(p.getFila(), p.getColumna()).getEstado() == EstadoCelda.OCULTA){
	    		this.tablero.getCelda(p.getFila(), p.getColumna()).setEstado(EstadoCelda.MARCADA);
	    		this.marcadas++;
	    	}
       	}
       	return;
    }
    
    /**
     * Aplica la acción de desmarcar sobre la celda en la posición dada. Si
    la posición no es correcta no se hace nada. Si la celda esta MARCADA
    se cambia su estado a OCULTA, en cualquier otro caso no se hace nada.
    Se disminuye la cuenta de celdas marcadas en 1.
     * @param p La posición de la celda a desmarcar.
     */
    public void desmarcar(Posicion p){
    	if(this.tablero.esPosicionValida(p.getFila(), p.getColumna())){
	    	if(this.tablero.getCelda(p.getFila(), p.getColumna()).getEstado() == EstadoCelda.MARCADA){
	    		this.tablero.getCelda(p.getFila(), p.getColumna()).setEstado(EstadoCelda.OCULTA);
	    		this.marcadas--;
	    	}
    	}
    	return;
    }
 
    /**
     * Retorna la cantidad de celdas que están alrededor de la celda en
    cuestión cuyo estado es MARCADA. Si no hay ninguna retorna 0.<br><br>
    * <b>PRECONDICIÓN</b>: La posición es válida para el tablero.
     * @param p La posición de la celda cuyas cicundantes marcadas se desea saber.
     * @return La cantidad de circundantes marcadas para la celda en la posición p.
     */
    public int getCircundantesMarcadas(Posicion p){
    	int marcadas = 0;
    	if(this.tablero.esPosicionValida(p.getFila(), p.getColumna())){
    		for(int i = p.getFila()-1; i <= p.getFila()+1; i++){
    			for(int j = p.getColumna()-1; j <= p.getColumna()+1; j++){
    				if(this.getEstadoCelda(new Posicion((byte)i, (byte)j)) == EstadoCelda.MARCADA){
    					marcadas++;
    				}
    			}
    		}
    	}
    	return marcadas;
    }
    
    /**
     * Retorna la cantidad de celdas que están alrededor de la celda en
    cuestión cuyo estado es MARCADA u OCULTA (distinto de DESCUBIERTA). Si
    no hay ninguna retorna 0. Si la posición no es válida no hace nada.<br><br>
    * <b>PRECONDICIÓN</b>: La posición es válida para el tablero.
     * @param p La posición de la celda en cuestión.
     * @return La cantidad de celdas no descubiertas.
     */
    public int getCircundantesNoDescubiertas(Posicion p){
    	int noDescubiertas = 0;
    	if(this.tablero.esPosicionValida(p.getFila(), p.getColumna())){
    		for(int i = p.getFila()-1; i <= p.getFila()+1; i++){
    			for(int j = p.getColumna()-1; j <= p.getColumna()+1; j++){
    				if(this.getEstadoCelda(new Posicion((byte)i, (byte)j)) != EstadoCelda.DESCUBIERTA){
    					noDescubiertas++;
    				}
    			}
    		}
    	}
    	return noDescubiertas;
    }
    
    /**
     * Esta acción se lleva a cabo solo si la posición es correcta y si la
    celda en cuestión ya está descubierta. La cantidad de celdas
    circundantes OCULTAS o MARCADAS sebe ser igual a la cantidad de minas
    alrededor de esta celda. Todas las celdas circundantes quedan
    marcadas.
     * @param p La posición de la celda cuyas circundantes se desea marcar.
     */
    public void marcarCircundantes(Posicion p){
    	int cantidadCirc = 0;
    	if(this.tablero.esPosicionValida(p.getFila(), p.getColumna()) && 
    			(this.tablero.getCelda(p.getFila(), p.getColumna()).getEstado() == EstadoCelda.DESCUBIERTA)){
    		for(int i = p.getFila()-1; i <= p.getFila()+1; i++){
    			for(int j = p.getColumna()-1; j <= p.getColumna()+1; j++){
    				if(this.tablero.getCelda((byte)i, (byte)j).getEstado() != EstadoCelda.DESCUBIERTA){
    					cantidadCirc++;
    				}
    			}
    		}
    		if(this.tablero.getCelda(p.getFila(), p.getColumna()).getBombasCircundantes() == cantidadCirc){
    			for(int i = p.getFila()-1; i <= p.getFila()+1; i++){
        			for(int j = p.getColumna()-1; j <= p.getColumna()+1; j++){
        				if((byte)i == p.getFila() && (byte)j == p.getColumna()){
        					return;
        				}else{
        					this.tablero.getCelda((byte)i, (byte)j).setEstado(EstadoCelda.MARCADA);
        				}
        			}
        		}
    		}
    	}
    	return;
    }
    
    /**
     * Aplica la acción Descubrir sobre una celda al azar del tablero, que
        aún esté OCULTA y que además no tenga mina (bomba).
     */
    public void descubrirSegura(){
    	int randomFila, randomColumna;
    	boolean nodescubierta = true;
    	while(nodescubierta){
    		randomFila = rd.nextInt(this.tablero.getAncho());
    		randomColumna = rd.nextInt(this.tablero.getLargo());
	    	if((!this.tablero.getCelda((byte)randomFila, (byte)randomColumna).tieneBomba())&&
	    			(this.tablero.getCelda((byte) randomFila, (byte)randomColumna).getEstado() == EstadoCelda.OCULTA)){
	    		descubrir(new Posicion((byte)randomFila, (byte)randomColumna));
	    		nodescubierta = false;
	    	}
    	}
    	return;
    }
}