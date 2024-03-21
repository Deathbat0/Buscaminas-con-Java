package main;

import javax.swing.JButton;
import motor.Posicion;


/**
 * Crea una celda individual
 * @author DeathabtO
 */

public class CeldaVisual extends JButton {
	public static int LARGO = 25, ANCHO = 25;
	private final Posicion posicionCelda;
	
	public CeldaVisual(Posicion p){
		super();
		this.posicionCelda = p;
	}
	
	public byte getFila(){
        return this.posicionCelda.getFila();
    }
    
    public byte getColumna(){
        return this.posicionCelda.getColumna();
    }
    
    public Posicion getPosicion(){
        return this.posicionCelda;
    }
}
