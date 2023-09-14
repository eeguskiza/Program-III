import java.util.Random;
import javax.swing.*;
/** Ejercicio de hilos con ventanas. Programa esta clase para que se cree una ventana
 * que pida un dato de texto al usuario y un botón de confirmar para que se confirme.
 * Haz que al pulsar el botón de confirmación se llame al procesoConfirmar()
 * que simula un proceso de almacenamiento externo que tarda unos segundos.
 * Observa que hasta que ocurre esta confirmación la ventana no responde.
 * 
 * 1. Arréglalo para que la ventana no se quede "frita" hasta que se acabe de confirmar.
 * 2. Haz que el botón de "confirmar" no se pueda pulsar dos veces mientras el proceso
 * de confirmación se esté realizando
 * 
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */

class VentanaConfirmacionLenta extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfTexto;
    private JButton bConfirmar;
    private JLabel lMensaje;
    private boolean confirmado = false;

    public VentanaConfirmacionLenta() {
        // 1. Crea los componentes (campos y botón)
        tfTexto = new JTextField( 20 );
        bConfirmar = new JButton( "Confirmar" );
        lMensaje = new JLabel( " " );
        // 2. Crea contenedor y añade componentes
        JPanel pSuperior = new JPanel();
        pSuperior.add( new JLabel( "Texto:" ) );
        pSuperior.add( tfTexto );
        pSuperior.add( bConfirmar );
        JPanel pInferior = new JPanel();
        pInferior.add( lMensaje );
        JPanel pTotal = new JPanel();
        pTotal.add( pSuperior );
        pTotal.add( pInferior );
        this.add( pTotal );
        // 3. Asigna escuchadores a componentes
        bConfirmar.addActionListener( e -> {
            if (confirmado) return;
            confirmado = true;
            lMensaje.setText( "Confirmando..." );
            new Thread( () -> {
                procesoConfirmar();
                lMensaje.setText( "Confirmado" );
                confirmado = false;
            }).start();
        });
        // 4. Hacer visible la ventana
        this.pack();
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setLocationRelativeTo( null );
        this.setVisible( true );
    }

		private static Random r = new Random();
	// Este método simula un proceso que tarda un tiempo en hacerse (entre 5 y 10 segundos)
	private static void procesoConfirmar() {
		try {
			Thread.sleep( 5000 + 1000*r.nextInt(6) );
		} catch (InterruptedException e) {}
	}
}