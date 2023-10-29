import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class VentanaTablaDatos extends JFrame {

	private static final int COL_AUTONOMIA = 4;

	private JTable tablaDatos;
	private DataSetMunicipios datosMunis;
	private JLabel lblMensaje;
	private JTree arbolDatos;
	private JPanel pnlVisualizacion;
	private ArrayList<Municipio> todosLosMunicipios = new ArrayList<>();
	private String autonomiaSeleccionada = "";
	private boolean resaltado = false;


	public VentanaTablaDatos( JFrame ventOrigen ) {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 1400, 600 );
		setLocationRelativeTo( null );

		// Inicializamos el mensaje en la parte superior
		lblMensaje = new JLabel("");
		add(lblMensaje, BorderLayout.NORTH);

		// Inicializamos el JTree a la izquierda con barras de desplazamiento
		arbolDatos = new JTree();
		JScrollPane scrollArbol = new JScrollPane(arbolDatos);
		add(scrollArbol, BorderLayout.WEST);

		tablaDatos = new JTable();
		JScrollPane scrollTabla = new JScrollPane( tablaDatos );
		add(scrollTabla, BorderLayout.CENTER);

		// Panel de visualización a la derecha
		pnlVisualizacion = new JPanel();
		add(pnlVisualizacion, BorderLayout.EAST);

		JPanel pInferior = new JPanel();
		JButton bAnyadir = new JButton( "Añadir" );
		JButton bBorrar = new JButton( "Borrar" );
		JButton bOrden = new JButton( "Orden" );  // Nuevo botón
		pInferior.add(bAnyadir);
		pInferior.add(bBorrar);
		pInferior.add(bOrden);  // Agregamos el nuevo botón
		add(pInferior, BorderLayout.SOUTH);

		arbolDatos.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arbolDatos.getLastSelectedPathComponent();


				// Comprobamos que el nodo seleccionado sea una hoja pero no la raíz
				if (selectedNode != null && selectedNode.isLeaf() && !selectedNode.isRoot()) {
					SwingUtilities.invokeLater(() -> {
						String provinciaSeleccionada = selectedNode.toString();
						System.out.println("Provincia seleccionada: " + provinciaSeleccionada);


						// Cargar los datos de los municipios para esa provincia.
						cargarMunicipios(provinciaSeleccionada);
					});
				}
			}
		});

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

		bBorrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					int confirmacion = JOptionPane.showConfirmDialog(VentanaTablaDatos.this, "¿Estás seguro de que deseas borrar este municipio?", "Confirmación de borrado", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
						datosMunis.borraFila(filaSel);
					}
				}
			}
		});


		bAnyadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
				if (filaSel >= 0) {
					Municipio municipioSeleccionado = datosMunis.getListaMunicipios().get(filaSel);
					String provinciaSeleccionada = municipioSeleccionado.getProvincia();
					String autonomiaSeleccionada = municipioSeleccionado.getAutonomia();

					// Crea un nuevo municipio con los valores deseados
					Municipio nuevoMunicipio = new Municipio(
							datosMunis.getListaMunicipios().size() + 1,
							"",
							50000,
							provinciaSeleccionada,
							autonomiaSeleccionada,
							0,
							0
					);

					// Agrega el nuevo municipio al final del dataset
					datosMunis.anyadir(nuevoMunicipio);

					// Actualiza el árbol para reflejar los cambios
					cargarArbol();
				}
			}
		});

		bOrden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Botón de Orden presionado");
				System.out.println("Provincia seleccionada: " + autonomiaSeleccionada); // Change this to provinciaSeleccionada or similar if needed
				if (autonomiaSeleccionada.isEmpty()) { // This should also be changed if the variable name is changed
					lblMensaje.setText("Selecciona una provincia en el árbol antes de ordenar.");
				} else {
					String columnName = tablaDatos.getColumnName(tablaDatos.getSelectedColumn());
					List<Municipio> municipiosProvinciaSeleccionada = datosMunis.getListaMunicipios()
							.stream()
							.filter(municipio -> municipio.getProvincia().equals(autonomiaSeleccionada))
							.collect(Collectors.toList());

					if (columnName.equals("Nombre")) {
						ordenarMunicipiosPorNombre(municipiosProvinciaSeleccionada);
					} else if (columnName.equals("Habitantes")) {
						ordenarMunicipiosPorHabitantesDescendente(municipiosProvinciaSeleccionada);
					}

					// Refresh the table data after sorting
					DataSetMunicipios datasetFiltrado = new DataSetMunicipios();
					for (Municipio municipio : municipiosProvinciaSeleccionada) {
						datasetFiltrado.anyadir(municipio);
					}
					tablaDatos.setModel(datasetFiltrado);
				}
			}
		});




	}

	private void ordenarMunicipiosPorNombre(List<Municipio> municipios) {
		municipios.sort(Comparator.comparing(Municipio::getNombre));
	}

	private void ordenarMunicipiosPorHabitantesDescendente(List<Municipio> municipios) {
		municipios.sort(Comparator.comparingInt(Municipio::getHabitantes).reversed());
	}

	//Haz que al seleccionar una provincia en el árbol se cargue el modelo de la tabla central con los datos de todos los municipios de esa provincia, en orden alfabético de nombre.
	private void cargarMunicipios(String provinciaSeleccionada) {
		List<Municipio> municipiosDeUnaProvincia = datosMunis.getListaMunicipios().stream()
				.filter(municipio -> provinciaSeleccionada.equals(municipio.getProvincia()))
				.sorted(Comparator.comparing(Municipio::getNombre)) // Ordena alfabéticamente por nombre
				.collect(Collectors.toList());
		System.out.println("Municipios en " + provinciaSeleccionada + ": " + municipiosDeUnaProvincia.size());

		// Crear un nuevo DataSetMunicipios para la tabla
		DataSetMunicipios datasetFiltrado = new DataSetMunicipios(); // Suponiendo que existe un constructor vacío o crea uno
		for (Municipio municipio : municipiosDeUnaProvincia) {
			datasetFiltrado.anyadir(municipio);
		}

		// Configurar el JTable para usar el nuevo dataset
		tablaDatos.setModel(datasetFiltrado); // Asumiendo que tablaDatos es tu JTable
	}

	//Dato habietantes de la progress bar y onerla en una columna extra


	public void setDatos( DataSetMunicipios datosMunis ) throws IOException {
		this.datosMunis = datosMunis;
		tablaDatos.setModel( datosMunis );

		TableColumn col = tablaDatos.getColumnModel().getColumn( 0 );
		col.setMaxWidth( 50 );
		col = tablaDatos.getColumnModel().getColumn(2);
		col.setMinWidth( 150 );
		col.setMaxWidth( 150 );

			tablaDatos.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					if (column == 2) { // Si es la columna de habitantes
						return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					}
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
			});

		// Renderizador para la columna de la barra de progreso
		tablaDatos.setDefaultRenderer(JProgressBar.class, new DefaultTableCellRenderer() {
			private JProgressBar pbHabs = new JProgressBar(50000, 5000000) {
				@Override
				protected void paintComponent(Graphics g) {
					int currentValue = getValue();
					float percentage = (float) (currentValue - 50000) / (5000000 - 50000);

					// Interpola entre verde y rojo basado en el porcentaje
					Color interpolatedColor = new Color((int) (255 * percentage), 255 - (int) (255 * percentage), 0);
					setForeground(interpolatedColor);

					super.paintComponent(g);
				}
			};

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (column == 3 && value instanceof Integer) { // Si es la columna de la barra de progreso y el valor es Integer
					pbHabs.setValue((Integer) value);
					pbHabs.setStringPainted(false);  // Asegúrate de que no se pinte la cadena en la barra
					return pbHabs;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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

		tablaDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					if (!resaltado) {
						int filaSeleccionada = tablaDatos.rowAtPoint(e.getPoint());
						if (filaSeleccionada >= 0) {
							Municipio municipioSeleccionado = datosMunis.getListaMunicipios().get(filaSeleccionada);
							int poblacionSeleccionada = municipioSeleccionado.getHabitantes();

							for (int fila = 0; fila < tablaDatos.getRowCount(); fila++) {
								int poblacion = (int) tablaDatos.getValueAt(fila, 2);

								if (poblacion > poblacionSeleccionada) {
									tablaDatos.getCellRenderer(fila, 0).getTableCellRendererComponent(tablaDatos, null, false, false, fila, 0).setBackground(Color.RED);
								} else if (poblacion < poblacionSeleccionada) {
									tablaDatos.getCellRenderer(fila, 0).getTableCellRendererComponent(tablaDatos, null, false, false, fila, 0).setBackground(Color.GREEN);
								}
							}
						}
						resaltado = true;
					} else {
						// Si ya está resaltado, quitamos el resaltado
						for (int fila = 0; fila < tablaDatos.getRowCount(); fila++) {
							tablaDatos.getCellRenderer(fila, 0).getTableCellRendererComponent(tablaDatos, null, false, false, fila, 0).setBackground(Color.WHITE);
							tablaDatos.getCellRenderer(fila, 1).getTableCellRendererComponent(tablaDatos, null, false, false, fila, 1).setBackground(Color.WHITE);
						}
						resaltado = false;
					}
					tablaDatos.repaint();  // Refrescar la tabla para ver los cambios
				}
			}
		});
	}

	private void cargarArbol() {
		// Nodo raíz
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("España");

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
		DefaultTreeModel modeloArbol = new DefaultTreeModel(raiz);
		arbolDatos.setModel(modeloArbol);
		arbolDatos.setEditable(false); // El JTree no es editable
		arbolDatos.setCellRenderer(new RendererTree());
	}

}
