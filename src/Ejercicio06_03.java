import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;


public class Ejercicio06_03 {

	private static JFrame ventana;
	private static DataSetMunicipios dataset;

	private static VentanaTablaDatos ventanaDatos;

	public static void main(String[] args) {
		ventana = new JFrame( "Ejercicio 6.3" );
		ventana.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		ventana.setSize( 200, 200 );
		ventana.setLocationRelativeTo( null );

		JButton bCargaMunicipios = new JButton( "" );

		// Establecer el ícono del botón
		ImageIcon iconOriginal = new ImageIcon("upload.png");
		Image imgEscalada = iconOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		bCargaMunicipios.setIcon(new ImageIcon(imgEscalada));
		ventana.add( bCargaMunicipios );

		bCargaMunicipios.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargaMunicipios();
			}
		});

		ventana.setVisible( true );
	}

	private static void cargaMunicipios() {
		try {
			dataset = new DataSetMunicipios( "municipios200k.txt" );
			System.out.println( "Cargados municipios:" );
			for (Municipio m : dataset.getListaMunicipios() ) {
				System.out.println( "\t" + m );
			}
			// TODO Resolver el ejercicio 6.3
			ventanaDatos = new VentanaTablaDatos( ventana );
			ventanaDatos.setDatos( dataset );
			ventanaDatos.setVisible( true );
		} catch (IOException e) {
			System.err.println( "Error en carga de municipios" );
		}
	}

}
