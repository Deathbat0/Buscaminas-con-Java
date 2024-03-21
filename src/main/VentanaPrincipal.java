package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import motor.*;

/**
 * Ventana principal del juego que se inicia con una dificultad en F
 * @author DeathbatO
 */

public class VentanaPrincipal extends javax.swing.JFrame implements ActionListener, MouseInputListener{
	
	/**
	 * 
	 */
	private static final int ANCHO_DEFECTO = 800;
	private static final int LARGO_DEFECTO = 600;
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiquetaStatus;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu menuAcciones;
    private javax.swing.JMenuItem menuAccionesDespejarSegura;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuJuegoNuevo;
    private javax.swing.JMenuItem menuJuegoSalir;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JCheckBoxMenuItem menuOpcionesDebug;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JPanel statusPane;
    // End of variables declaration//GEN-END:variables
	
	private Juego game;
	private CeldaVisual[][] vistaTablero;
	private final ImageIcon iconoBomba, iconoBombaExplota, iconoBandera, iconoOK,
		icono1, icono2, icono3, icono4, icono5, icono6, icono7, icono8;
	
	public VentanaPrincipal(){
		this.iconoBomba = new ImageIcon(getClass().getResource("/mina.png"));
		this.iconoBandera = new ImageIcon(getClass().getResource("/marca.png"));
		this.iconoBombaExplota = new ImageIcon(getClass().getResource("/minaExplota.png"));
		this.iconoOK = new ImageIcon(getClass().getResource("/ok.png"));
		this.icono1 = new ImageIcon(getClass().getResource("/1.png"));
		this.icono2 = new ImageIcon(getClass().getResource("/2.png"));
		this.icono3 = new ImageIcon(getClass().getResource("/3.png"));
		this.icono4 = new ImageIcon(getClass().getResource("/4.png"));
		this.icono5 = new ImageIcon(getClass().getResource("/5.png"));
		this.icono6 = new ImageIcon(getClass().getResource("/6.png"));
		this.icono7 = new ImageIcon(getClass().getResource("/7.png"));
		this.icono8 = new ImageIcon(getClass().getResource("/8.png"));
		
		this.setIconImage(iconoBombaExplota.getImage());
		this.comenzar((byte)10, (byte)10, 10);
	}
	
	public void comenzar(byte cantidadFilas, byte cantidadColumnas, int cantidadBombas){
		this.game = new Juego(cantidadFilas, cantidadColumnas, cantidadBombas);
		
		this.panelTablero.removeAll();
		((GridLayout)this.panelTablero.getLayout()).setRows(this.game.getFilas());
		((GridLayout)this.panelTablero.getLayout()).setColumns(this.game.getColumnas());
		
		this.vistaTablero = new CeldaVisual[this.game.getFilas()][this.game.getColumnas()];
		for(int i = 0; i < this.game.getFilas(); i++){
			for(int j = 0; j < this.game.getColumnas(); j++){
				this.vistaTablero[i][j] = new CeldaVisual(new Posicion((byte)i, (byte)j));
				((JButton)this.vistaTablero[i][j]).setPreferredSize(new Dimension(CeldaVisual.LARGO, CeldaVisual.ANCHO));
				this.panelTablero.add(this.vistaTablero[i][j]);
				((JButton)this.vistaTablero[i][j]).addMouseListener(this);
			}
		}
		this.setSize(ANCHO_DEFECTO, LARGO_DEFECTO);
		this.etiquetaStatus.setText(this.game.getMinas()+"Minas | "+this.game.getCeldasMarcadas()+" Marcadas |"+this.game.getCeldasDescubiertas()+" Descubiertas");
	}
    public void actualizar(){
        for(int i=0; i<this.game.getFilas(); i++){
            for(int j=0; j<this.game.getColumnas(); j++){ 
                if(!menuOpcionesDebug.isSelected()){
                    switch (this.game.getEstadoCelda(new Posicion((byte)i,(byte)j))){
                        case OCULTA: ((JButton)this.vistaTablero[i][j]).setIcon(null); break;
                        case MARCADA:
                            ((JButton)this.vistaTablero[i][j]).setIcon(iconoBandera);
                            break;
                        case DESCUBIERTA: 
                            if (this.game.tieneBomba(new Posicion((byte)i,(byte)j))) {
                                ((JButton)this.vistaTablero[i][j]).setIcon(iconoBombaExplota);
                                ((JButton)this.vistaTablero[i][j]).setBackground(Color.red);
                            } else {
                                if (this.game.getMinasCircundantes(new Posicion((byte)i,(byte)j))==0){
                                    ((JButton)this.vistaTablero[i][j]).setIcon(null);
                                    ((JButton)this.vistaTablero[i][j]).setEnabled(false);
                                } else {
                                    ((JButton)this.vistaTablero[i][j]).setEnabled(true);
                                    switch(this.game.getMinasCircundantes(new Posicion((byte)i,(byte)j))){
                                        case 1: ((JButton)this.vistaTablero[i][j]).setIcon(icono1); break;
                                        case 2: ((JButton)this.vistaTablero[i][j]).setIcon(icono2); break;
                                        case 3: ((JButton)this.vistaTablero[i][j]).setIcon(icono3); break;
                                        case 4: ((JButton)this.vistaTablero[i][j]).setIcon(icono4); break;
                                        case 5: ((JButton)this.vistaTablero[i][j]).setIcon(icono5); break;
                                        case 6: ((JButton)this.vistaTablero[i][j]).setIcon(icono6); break;
                                        case 7: ((JButton)this.vistaTablero[i][j]).setIcon(icono7); break;
                                        case 8: ((JButton)this.vistaTablero[i][j]).setIcon(icono8); break;
                                    }
                                }
                            }
                            break;
                    }
                }//Fin MODO NORMAL
                else {
                    if (this.game.tieneBomba(new Posicion((byte)i,(byte)j))){
                        ((JButton)this.vistaTablero[i][j]).setIcon(iconoBomba);
                    }else if (this.game.getMinasCircundantes(new Posicion((byte)i,(byte)j))==0){
                        ((JButton)this.vistaTablero[i][j]).setIcon(iconoOK);
                    } else {
                        ((JButton)this.vistaTablero[i][j]).setEnabled(true);
                        switch(this.game.getMinasCircundantes(new Posicion((byte)i,(byte)j))){
                            case 1: ((JButton)this.vistaTablero[i][j]).setIcon(icono1); break;
                            case 2: ((JButton)this.vistaTablero[i][j]).setIcon(icono2); break;
                            case 3: ((JButton)this.vistaTablero[i][j]).setIcon(icono3); break;
                            case 4: ((JButton)this.vistaTablero[i][j]).setIcon(icono4); break;
                            case 5: ((JButton)this.vistaTablero[i][j]).setIcon(icono5); break;
                            case 6: ((JButton)this.vistaTablero[i][j]).setIcon(icono6); break;
                            case 7: ((JButton)this.vistaTablero[i][j]).setIcon(icono7); break;
                            case 8: ((JButton)this.vistaTablero[i][j]).setIcon(icono8); break;
                        }
                    }

                    switch(this.game.getEstadoCelda(new Posicion((byte)i,(byte)j))){
                        case MARCADA: ((JButton)this.vistaTablero[i][j]).setIcon(iconoBandera); break;
                        case DESCUBIERTA: ((JButton)this.vistaTablero[i][j]).setEnabled(false); break;
                    }
                }
            }
        }
        
        switch(this.game.getEstado()){
            case GANADO:
                JOptionPane.showMessageDialog(this,"¡¡¡GANASTE!!!");
                this.etiquetaStatus.setText("FELICITACIONES ¡¡¡GANASTE!!!");
                break;
            case PERDIDO:
                JOptionPane.showMessageDialog(this,"Lamentablemente has perdido");
                this.etiquetaStatus.setText("LAMENTABLEMENTE PERDISTE");
                break;
            case JUGANDO:
                this.etiquetaStatus.setText(this.game.getMinas()+ "minas | "+this.game.getCeldasMarcadas()+" Marcadas | "+this.game.getCeldasDescubiertas()+" Descubiertas");
                break;
        }
    }

	
	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(){
    	
    	statusPane = new javax.swing.JPanel();
    	etiquetaStatus = new javax.swing.JLabel();
    	jScrollPane3 = new javax.swing.JScrollPane();
        panelTablero = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuJuegoNuevo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuJuegoSalir = new javax.swing.JMenuItem();
        menuAcciones = new javax.swing.JMenu();
        menuAccionesDespejarSegura = new javax.swing.JMenuItem();
        menuOpciones = new javax.swing.JMenu();
        menuOpcionesDebug = new javax.swing.JCheckBoxMenuItem();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscaminas");
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        
        statusPane.setBackground(new java.awt.Color(255, 255, 255));
        statusPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusPane.setMaximumSize(new java.awt.Dimension(32767, 50));
        statusPane.setMinimumSize(new java.awt.Dimension(100, 50));
        
        etiquetaStatus.setText("jLabel1");

        javax.swing.GroupLayout statusPaneLayout = new javax.swing.GroupLayout(statusPane);
        statusPane.setLayout(statusPaneLayout);
        
        statusPaneLayout.setHorizontalGroup(
            statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        
        statusPaneLayout.setVerticalGroup(
                statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(statusPaneLayout.createSequentialGroup()
                    .addComponent(etiquetaStatus)
                    .addGap(0, 0, Short.MAX_VALUE)));
        
        panelTablero.setLayout(new java.awt.GridLayout(10, 10));
        jScrollPane3.setViewportView(panelTablero);

        menuArchivo.setText("Juego");

        menuJuegoNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuJuegoNuevo.setText("Nuevo...");
        menuJuegoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuJuegoNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuJuegoNuevo);
        menuArchivo.add(jSeparator1);
        
        menuJuegoSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuJuegoSalir.setText("Salir");
        menuJuegoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuJuegoSalirActionPerformed(evt);
            }
        });
        
        menuArchivo.add(menuJuegoSalir);

        jMenuBar1.add(menuArchivo);

        menuAcciones.setText("Acciones");

        menuAccionesDespejarSegura.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuAccionesDespejarSegura.setText("Despejar segura");
        menuAccionesDespejarSegura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAccionesDespejarSeguraActionPerformed(evt);
            }
        });
        
        menuAcciones.add(menuAccionesDespejarSegura);

        jMenuBar1.add(menuAcciones);

        menuOpciones.setText("Opciones");
        menuOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpcionesActionPerformed(evt);
            }
        });

        menuOpcionesDebug.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuOpcionesDebug.setText("Modo debug");
        menuOpcionesDebug.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                menuOpcionesDebugItemStateChanged(evt);
            }
        });
        
        menuOpciones.add(menuOpcionesDebug);

        jMenuBar1.add(menuOpciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPane, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
    
    private void menuJuegoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJuegoNuevoActionPerformed
        DialogoJuegoNuevo dialogoNew= new DialogoJuegoNuevo(this,true);
        dialogoNew.setVisible(true);
        dialogoNew.setLocationRelativeTo(this);
        if(dialogoNew.aceptado()){
            int f, c;
            int m;
            f= dialogoNew.getCantidadFilas();
            c= dialogoNew.getCantidadColumnas();
            m= dialogoNew.getCantidadMinas();
            dialogoNew.dispose();
            this.menuOpcionesDebug.setSelected(false);
            this.comenzar((byte)f,(byte)c,m);
        }else dialogoNew.dispose();
        
    }//GEN-LAST:event_menuJuegoNuevoActionPerformed
    
    private void menuOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpcionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuOpcionesActionPerformed

    private void menuOpcionesDebugItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_menuOpcionesDebugItemStateChanged
        this.actualizar();
    }//GEN-LAST:event_menuOpcionesDebugItemStateChanged

    private void menuAccionesDespejarSeguraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAccionesDespejarSeguraActionPerformed
        if (this.game.getEstado() == Juego.EstadoJuego.JUGANDO){
            this.game.descubrirSegura();
            this.actualizar();
        }
    }//GEN-LAST:event_menuAccionesDespejarSeguraActionPerformed

    private void menuJuegoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJuegoSalirActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_menuJuegoSalirActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(this, "Lala");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.game.getEstado()!=Juego.EstadoJuego.JUGANDO){
            JOptionPane.showMessageDialog(this,"El juego ha terminado: inicia un nuevo juego");
            return;
        }
                
        CeldaVisual cv = (CeldaVisual)e.getSource();
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                this.game.descubrir(cv.getPosicion());
                break;
            case MouseEvent.BUTTON3:
                switch(this.game.getEstadoCelda(cv.getPosicion())){
                    case OCULTA: this.game.marcar(cv.getPosicion()); break;
                    case MARCADA: this.game.desmarcar(cv.getPosicion()); break;
                }
                break;
            case MouseEvent.BUTTON2:
                this.game.marcarCircundantes(cv.getPosicion());
                break;
            default:
                break;
        }
        
        this.actualizar();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
