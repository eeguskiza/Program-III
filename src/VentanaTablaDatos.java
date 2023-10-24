import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.util.List;
import java.util.stream.Collectors;



@SuppressWarnings("serial")
public class VentanaTablaDatos extends JFrame {

	private static final int COL_AUTONOMIA = 6;
	
	private JTable tablaDatos;
	private DataSetMunicipios datosMunis;
	// private DatasetParaJTable modeloDatos;  El propio dataset es también modelo de datos, no hace falta diferenciarlos

	private String autonomiaSeleccionada = "";

	private JLabel lblMensaje = new JLabel(""); // Parte superior
	private JTree arbol = new JTree(); // Parte izquierda
	private DefaultTreeModel modeloArbol; // Modelo del árbol
	private JPanel pnlVisualizacion = new JPanel(); // Parte derecha
	private JButton btnInsertar = new JButton("Insertar");
	private JButton btnBorrar = new JButton("Borrar");
	private JButton btnOrden = new JButton("Orden"); // Parte inferior

	private static final int COLUMN_INDEX_OF_POBLACION = 2; // Donde X es el índice correcto de la columna de población


	public VentanaTablaDatos(JFrame ventOrigen) {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 600 );
		setLocationRelativeTo( null );
		
		tablaDatos = new JTable();
		add( new JScrollPane( tablaDatos ), BorderLayout.CENTER );
		
		JPanel pInferior = new JPanel();
		JButton bAnyadir = new JButton( "Añadir" );
		JButton bBorrar = new JButton( "Borrar" );
		pInferior.add( bAnyadir );
		pInferior.add( bBorrar );
		add( pInferior, BorderLayout.SOUTH );
		
		this.addWindowListener( new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ventOrigen.setVisible( false );
			}
			@Override
			public void windowClosed(WindowEvent e) {
				ventOrigen.setVisible( true );
			}
		});
	
		bBorrar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					datosMunis.borraFila( filaSel );
				}
			}
		});
		
		bAnyadir.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel>=0) {
					datosMunis.anyadeFila( filaSel, new Municipio( datosMunis.getListaMunicipios().size()+1, "Nombre", 0, "Provincia", "Autonomía" , 0, 0) );
				}
			}
		});

		// Configuración de la nueva interfaz:
		// Parte superior
		add(lblMensaje, BorderLayout.NORTH);

		// Parte izquierda
		JScrollPane spArbol = new JScrollPane(arbol);
		add(spArbol, BorderLayout.WEST);

		// Parte central (donde estaba tu tabla antes)
		JScrollPane spTabla = new JScrollPane(tablaDatos);
		add(spTabla, BorderLayout.CENTER);

		// Parte derecha
		add(pnlVisualizacion, BorderLayout.EAST);

		// Parte inferior
		JPanel pnlInferior = new JPanel(); // Este panel ya estaba definido en tu código, solo añado los nuevos botones
		pnlInferior.add(btnInsertar);
		pnlInferior.add(btnBorrar);
		pnlInferior.add(btnOrden);
		add(pnlInferior, BorderLayout.SOUTH);

		arbol.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();

				if (selectedNode != null && selectedNode.isLeaf()) {  // Suponiendo que solo las hojas del árbol son provincias
					String provinciaSeleccionada = selectedNode.toString();

					// Cargar los datos de los municipios para esa provincia.
					// Parece que necesitas implementar este método
					List<Municipio> municipios = cargarMunicipios(provinciaSeleccionada);

					// Limpiar datos anteriores en el modelo de la tabla
					for (int i = datosMunis.getListaMunicipios().size() - 1; i >= 0; i--) {
						datosMunis.borraFila(i);
					}

					// Añadir los nuevos municipios al modelo de la tabla
					for (FilaParaJTable municipio : municipios) {
						datosMunis.add(municipio);  // Suponiendo que tienes un método add en tu DataSetMunicipios
					}
				}
			}
		});

		tablaDatos.setDefaultRenderer(JProgressBar.class, new DefaultTableCellRenderer() {
			private JProgressBar pbHabs = new JProgressBar(0, 5000000) {
				protected void paintComponent(java.awt.Graphics g) {
					super.paintComponent(g);
					g.setColor(Color.BLACK);
					g.drawString(getValue() + "", 50, 10);
				}
			};

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (column == COLUMN_INDEX_OF_POBLACION) {  // Asegúrate de definir COLUMN_INDEX_OF_POBLACION con el índice correcto de la columna
					pbHabs.setValue((Integer) value);
					return pbHabs;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		});

	}

	private List<Municipio> cargarMunicipios(String provinciaSeleccionada) {
		List<Municipio> municipiosFiltrados = datosMunis.getListaMunicipios().stream()
				.filter(muni -> muni.getProvincia().equals(provinciaSeleccionada))
				.collect(Collectors.toList());

		return getMunicipiosOrdenadosPorNombre(municipiosFiltrados);
	}


	private List<Municipio> getMunicipiosOrdenadosPorNombre(List<Municipio> municipios) {
		municipios.sort((m1, m2) -> m1.getNombre().compareTo(m2.getNombre()));
		return municipios;
	}

	public void setDatos( DataSetMunicipios datosMunis ) {
		this.datosMunis = datosMunis;
		tablaDatos.setModel( datosMunis );
		
		TableColumn col = tablaDatos.getColumnModel().getColumn( 0 );
		col.setMaxWidth( 50 );
		col = tablaDatos.getColumnModel().getColumn(2);
		col.setMinWidth( 150 );
		col.setMaxWidth( 150 );

		tablaDatos.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {
			private JProgressBar pbHabs = new JProgressBar( 0, 5000000 ) {
				protected void paintComponent(java.awt.Graphics g) {
					super.paintComponent(g);
					g.setColor( Color.BLACK );
					g.drawString( getValue()+"", 50, 10 );
				}
			};
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// System.out.println( "getTCR " + row + "," + column );
				if (column==2) {
					// Si el dato es un Object o String sería esto
					// int valorCelda = Integer.parseInt( value.toString() );
					// pbHabs.setValue( valorCelda );
					// return pbHabs;
					// Pero si el dato está asegurado ser un Integer se puede castear:
					pbHabs.setValue( (Integer)value );
					return pbHabs;
				}
				JLabel rendPorDefecto = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				return rendPorDefecto;
			}
			
		});
		
		tablaDatos.setDefaultRenderer( String.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column );
				c.setBackground( Color.WHITE );
				if (isSelected) {
					c.setBackground( Color.LIGHT_GRAY );
				}
				if (column==COL_AUTONOMIA) {
					if (autonomiaSeleccionada.equals( (String)value )) {
						c.setBackground( Color.CYAN );
					}
				}
				return c;
			}
		} );
		
		tablaDatos.addMouseMotionListener( new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int filaEnTabla = tablaDatos.rowAtPoint( e.getPoint() );
				int colEnTabla = tablaDatos.columnAtPoint( e.getPoint() );
				if (colEnTabla == 2) {
					int numHabs = datosMunis.getListaMunicipios().get(filaEnTabla).getHabitantes();
					tablaDatos.setToolTipText( String.format( "Población: %,d", numHabs ) );
				} else {
					tablaDatos.setToolTipText( null );  // Desactiva
				}
			}
		});

		tablaDatos.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaEnTabla = tablaDatos.rowAtPoint( e.getPoint() );
				int colEnTabla = tablaDatos.columnAtPoint( e.getPoint() );
				if (colEnTabla == COL_AUTONOMIA && filaEnTabla>=0) {
					autonomiaSeleccionada = datosMunis.getListaMunicipios().get(filaEnTabla).getAutonomia();
				} else {
					autonomiaSeleccionada = "";
				}
				tablaDatos.repaint();
			}
		});

		tablaDatos.setDefaultEditor( Integer.class, new DefaultCellEditor( new JTextField() ) {
			SpinnerNumberModel mSpinner = new SpinnerNumberModel( 200000, 200000, 5000000, 1000 );
			JSpinner spinner = new JSpinner( mSpinner );
			boolean lanzadoSpinner;
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { // Componente que se pone en la tabla al editar una celda
				if (column != 2) {
					lanzadoSpinner = false;
					return super.getTableCellEditorComponent(table, value, isSelected, row, column);
				}
				mSpinner.setValue( (Integer) value );
				lanzadoSpinner = true;
				return spinner;
			}
			@Override
			public Object getCellEditorValue() { // Valor que se retorna al acabar la edición
				if (lanzadoSpinner) {
					return spinner.getValue();
				} else {
					return Integer.parseInt( super.getCellEditorValue().toString() );
				}
			}
		});
		cargarArbol();
	}

	private void cargarArbol() {
		// Nodo raíz
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Municipios");

		// Hashmaps para gestionar nodos de comunidades y provincias
		HashMap<String, DefaultMutableTreeNode> comunidades = new HashMap<>();
		HashMap<String, DefaultMutableTreeNode> provincias = new HashMap<>();

		for (Municipio muni : datosMunis.getListaMunicipios()) {
			// Añadir nodo de comunidad si no existe
			if (!comunidades.containsKey(muni.getAutonomia())) {
				DefaultMutableTreeNode nodoComunidad = new DefaultMutableTreeNode(muni.getAutonomia());
				comunidades.put(muni.getAutonomia(), nodoComunidad);
				raiz.add(nodoComunidad);
			}

			// Añadir nodo de provincia si no existe
			String claveProvincia = muni.getAutonomia() + "-" + muni.getProvincia();
			if (!provincias.containsKey(claveProvincia)) {
				DefaultMutableTreeNode nodoProvincia = new DefaultMutableTreeNode(muni.getProvincia());
				provincias.put(claveProvincia, nodoProvincia);
				comunidades.get(muni.getAutonomia()).add(nodoProvincia);
			}
		}

		// Asignar el modelo al JTree
		modeloArbol = new DefaultTreeModel(raiz);
		arbol.setModel(modeloArbol);
		arbol.setEditable(false); // El JTree no es editable
	}




}
