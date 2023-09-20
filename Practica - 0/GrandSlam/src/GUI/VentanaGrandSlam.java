package GUI;
import ClasesBasicas.*;
import ClasesContenedoras.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaGrandSlam extends JFrame{

	protected JPanel panelTablaResultados;
	protected JButton bResultados;
	protected JPanel panelBotonAbajo;
	private DefaultTableModel modelo;
	private DefaultTableModel modelo2; //el de la tabla de torneos
	protected List<Resultado> resultados;
	protected List<Torneo> torneos;
	protected List<Tenista> tenistas;
	protected JPanel main;
	protected String rutaTorneoCSV = "torneos.csv";
	protected String rutaResultadoCSV = "resultados.csv";
	
	public VentanaGrandSlam() {
		this.setTitle("Sistema de gestión del Super Slam");
		this.setSize(1100, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		main = new JPanel();
        main.setLayout(new BorderLayout());
		this.add(main);
		tenistas = new ArrayList<>();
					
		
		//----PANEL BOTONES ARRIBA---
		JPanel panelBotonesArriba = new JPanel(new FlowLayout());
		main.add(panelBotonesArriba, BorderLayout.NORTH);
		JButton bJugadores = new JButton("Jugadores");
		JButton bTorneos = new JButton("Torneos");
		bResultados = new JButton("Resultados"); bResultados.setEnabled(true);
		JButton bAjustes = new JButton("Ajustes"); bAjustes.setBackground(Color.GREEN);
		panelBotonesArriba.add(bJugadores); panelBotonesArriba.add(bTorneos); panelBotonesArriba.add(bResultados); panelBotonesArriba.add(bAjustes);
		
		//---------------------PANEL RESULTADOS-----------------------
		panelTablaResultados = new JPanel();
		panelTablaResultados.setBorder(new EmptyBorder(25, 10, 10, 10));
        panelTablaResultados.setLayout(new BorderLayout());
		
		String[] columnasTitulos = {"Año", "Torneo", "Campeón", "Ranking Campeón", "Nacionalidad Camp.", "Subcampeón", "Ranking SUB.", "Nacionalidad SUB.", "Resultado"};
		modelo = new DefaultTableModel(columnasTitulos, 0) {
    		@Override // Esto hace que todas las celdas sean no editables y asi pueda hacer el pop-up
    	    public boolean isCellEditable(int row, int column) {
    	        return false; 
    	    }
    	};
    	
        JTable tablaResultados = new JTable(modelo);
        
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        panelTablaResultados.add(scrollPane, BorderLayout.CENTER);
        
        //----PANEL BOTON AÑADIR Y GUARDAR----
        panelBotonAbajo = new JPanel(new FlowLayout());
        JButton botonAñadirResultado = new JButton("Añadir Resultado");
        panelBotonAbajo.add(botonAñadirResultado);
        //main.add(panelBotonAbajo, BorderLayout.SOUTH); 
        
        botonAñadirResultado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaAñadirResultadoNuevo();
			}
        	});
        
        tablaResultados.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()) {
    				ventanaAñadirResultadoNuevo();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        
        JButton botonGuardar = new JButton("Guardar Datos");
        panelBotonAbajo.add(botonGuardar);
        
        botonGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guardarResultadosCSV(rutaResultadoCSV);
			}     	
        });
        
        tablaResultados.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()) {
                    ventanaAñadirResultadoNuevo();
                } else if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
                	guardarResultadosCSV(rutaResultadoCSV);
                	
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        //main.add(panelTablaResultados, BorderLayout.CENTER);
        
       
        //----------------------------TABLA TORNEOS----------------------------------
    	JPanel panelTablaTorneos = new JPanel(new BorderLayout());
    	panelTablaTorneos.setBorder(new EmptyBorder(25, 200, 10, 200));
    	
    	String[] columnasTitulosTorneo = {"Código", "Nombre", "Ciudad"};
		modelo2 = new DefaultTableModel(columnasTitulosTorneo, 0) {
    		@Override // Esto hace que todas las celdas sean no editables y asi pueda hacer el pop-up
    	    public boolean isCellEditable(int row, int column) {
    	        return false; 
    	    }
    	};
    	
        JTable tablaTorneo = new JTable(modelo2);
        
        JScrollPane scrollPane2 = new JScrollPane(tablaTorneo);
        panelTablaTorneos.add(scrollPane2, BorderLayout.CENTER);
        
        //main.add(panelTablaTorneos, BorderLayout.CENTER);
        
  
        
        //-----CONTROL INICIAL----
        /*panelTablaResultados.setVisible(true);
        panelBotonAbajo.setVisible(true);
        panelTablaTorneos.setVisible(false);*/
        
        
        
      //damos la posibilidad de observar diferentes datos (jugadores, torneos), botones
        bTorneos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*panelTablaResultados.setVisible(false);
                panelTablaTorneos.setVisible(true);
                panelBotonAbajo.setVisible(false);*/
                
                main.add(panelTablaTorneos, BorderLayout.CENTER);
                main.remove(panelTablaResultados);
                main.remove(panelBotonAbajo);
                main.revalidate();
				main.repaint();

                bResultados.setEnabled(true);
                bTorneos.setEnabled(false);
                bJugadores.setEnabled(true);
            }
        });

        bResultados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*panelTablaResultados.setVisible(true);
                panelTablaTorneos.setVisible(false);
                panelBotonAbajo.setVisible(true); */
                
                main.remove(panelTablaTorneos);
                main.add(panelBotonAbajo, BorderLayout.SOUTH);
                main.add(panelTablaResultados, BorderLayout.CENTER);
                main.revalidate();
				main.repaint();

                bResultados.setEnabled(false);
                bTorneos.setEnabled(true);
                bJugadores.setEnabled(true);
            }
        });
        
        bJugadores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//enlazar con ventanaClasificacion.java
				VentanaClasificacion tablaTenistas = new VentanaClasificacion(VentanaGrandSlam.this);
				tablaTenistas.setVisible(true);
				main.setVisible(false);
				
			}
        });
        
        bAjustes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ajustes();
			}
        	
        });
        
        
      //-------CARGAR DATOS AL ENTRAR (y que aparezca la tabla resultados)------
      		resultados = new ArrayList<>();
      		torneos = new ArrayList<>();
      				addWindowListener(new WindowAdapter() {
      					@Override
      					public void windowOpened(WindowEvent e) {
      						cargarResultadosCSV(rutaResultadoCSV);
      						cargarTorneosCSV(rutaTorneoCSV);
      						main.remove(panelTablaTorneos);
      		                main.add(panelBotonAbajo, BorderLayout.SOUTH);
      		                main.add(panelTablaResultados, BorderLayout.CENTER);
      		                main.revalidate();
      		                

      		                bResultados.setEnabled(false);
      		                bTorneos.setEnabled(true);
      		                bJugadores.setEnabled(true);
      					}
      				});
      				

		//si no hay ruta de archivo se tendrá que abrir ajustes para poner una ruta
			if(rutaResultadoCSV.isEmpty() || rutaTorneoCSV.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No se encuentra ninguna ruta para los archivos necesarios. Abre ajustes y configúrala. \nEl resto es automático.");
			}
        
	}
	
	private void ventanaAñadirResultadoNuevo() {
		JFrame frame = new JFrame("Gestión de nuevos registros");
		frame.setSize(270,400);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		JPanel panelDatos = new JPanel(new GridLayout(9,2));
		JTextField tAño = new JTextField();
		JTextField tTorneo = new JTextField();
		JTextField tCampeon = new JTextField();
		JTextField tRankingC = new JTextField();
		JTextField tNacionalidadC = new JTextField();
		JTextField tSubcampeon = new JTextField();
		JTextField tRankingS = new JTextField();
		JTextField tNacionalidadS = new JTextField();
		JTextField tResultado = new JTextField();

		panelDatos.add(new JLabel("Año")); panelDatos.add(tAño);
		panelDatos.add(new JLabel("Torneo")); panelDatos.add(tTorneo);
		panelDatos.add(new JLabel("Campeon")); panelDatos.add(tCampeon);
		panelDatos.add(new JLabel("Ranking C."));panelDatos.add(tRankingC);
		panelDatos.add(new JLabel("Nacionalidad C."));panelDatos.add(tNacionalidadC);
		panelDatos.add(new JLabel("Subcampeon"));panelDatos.add(tSubcampeon);
		panelDatos.add(new JLabel("Ranking S."));panelDatos.add(tRankingS);
		panelDatos.add(new JLabel("Nacionalidad S."));panelDatos.add(tNacionalidadS);
		panelDatos.add(new JLabel("Resultado"));panelDatos.add(tResultado);
		
		frame.add(panelDatos);
		
		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				guardarResultado(Integer.parseInt(tAño.getText()), tTorneo.getText(), tCampeon.getText(), Integer.parseInt(tRankingC.getText()), tNacionalidadC.getText(), tSubcampeon.getText(), Integer.parseInt(tRankingS.getText()), tNacionalidadS.getText(), tResultado.getText()); 
				Tenista t1 = new Tenista(tCampeon.getText(), tNacionalidadC.getText(), null);
				Tenista t2 = new Tenista(tSubcampeon.getText(), tNacionalidadS.getText(), null);

				boolean repetido1 = false;
				boolean repetido2 = false;
				for(Tenista tenista : tenistas) {
					if (tenista.getNombre().equals(t1.getNombre())) {
						repetido1 = true;
					}
					if(tenista.getNombre().equals(t2.getNombre())) {
						repetido2 = true;
					}
				}
				
				if (!repetido1) {
					tenistas.add(t1);
				}
				if(!repetido2) {
					tenistas.add(t2);
				}
							
				frame.dispose();
				System.out.println(tenistas);
			}
		});
		frame.add(guardar, BorderLayout.SOUTH);
		
	}
	
	//QUE EL NUEVO RESULTADO APAREZCA EN LA TABLA VISUAL
	private void guardarResultado(int año, String torneo, String campeon, int rankingC, String nacionalidadC, String subcampeon, int rankingS, String nacionalidadS, String resultado) {
		Resultado nuevo;
		nuevo = new Resultado(año, torneo, campeon, rankingC, nacionalidadC, subcampeon, rankingS, nacionalidadS, resultado, null); //lo de las listas de cabeceras no he terminado de entender y no afectan en nada al funcionamineto de la aplicacion, por eso null.
		resultados.add(nuevo);
		modelo.addRow(new Object[] {año, torneo, campeon, rankingC, nacionalidadC, subcampeon, rankingS, nacionalidadS, resultado});	
	}

	//DESDE UN FICHERO CSV TANTO A LA LISTA EN MEMORIA DE RESULTADOS COMO A LA TABLA
	private void cargarResultadosCSV(String fichero) {
		try {
			Scanner sc = new Scanner(new File(fichero));
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(",");
				// Procesar los campos para eliminar las comillas si están presentes
	            for (int i = 0; i < campos.length; i++) {
	                campos[i] = campos[i].replaceAll("^\"|\"$", ""); // Elimina comillas al principio y al final
	            }
				
				guardarResultado(Integer.parseInt(campos[0]), campos[1], campos[2], Integer.parseInt(campos[3]), campos[4], campos[5], Integer.parseInt(campos[6]), campos[7], campos[8]);
			
				//creo un tenista con los pocos valores que quiero para mostrar luego en el pop-up
				Tenista t1 = new Tenista(campos[2], campos[4], null); //campeon
				Tenista t2 = new Tenista(campos[5], campos[7], null); //subcampeon
				
				boolean repetido1 = false;
				boolean repetido2 = false;
				for(Tenista tenista : tenistas) {
					if (tenista.getNombre().equals(t1.getNombre())) {
						repetido1 = true;
					}
					if(tenista.getNombre().equals(t2.getNombre())) {
						repetido2 = true;
					}
				}
				
				if (!repetido1) {
					tenistas.add(t1);
				}
				if(!repetido2) {
					tenistas.add(t2);
				}
		
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Error al cargar resultados");
		}
	}

	//se me siguien duplicando los datos al guardar
	private void guardarResultadosCSV(String fichero) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(fichero, false))) {
	        for (Resultado r : resultados) {
	            StringBuilder linea = new StringBuilder();

	            linea.append(r.getAño()).append(",");
	            linea.append(r.getTorneo()).append(",");
	            linea.append(r.getCampeon()).append(",");
	            linea.append(r.getRankingCampeon()).append(",");
	            linea.append(r.getNacionalidadCampeon()).append(",");
	            linea.append(r.getSubCampeon()).append(",");
	            linea.append(r.getRankingSubcampeon()).append(",");
	            linea.append(r.getNacionalidadSubcampeon()).append(",");
	            linea.append(r.getResultado());

	            writer.println(linea.toString());
	        }
	    } catch (IOException e) {
	        System.err.println("Error al guardar los resultados.");
	    }
	}

	private void cargarTorneosCSV(String fichero) {
		try {
			Scanner sc = new Scanner(new File(fichero));
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				// Procesar los campos para eliminar las comillas si están presentes
	            for (int i = 0; i < campos.length; i++) {
	                campos[i] = campos[i].replaceAll("^\"|\"$", ""); // Elimina comillas al principio y al final
	            }
				Torneo t = new Torneo(campos[0], campos[1], campos[2], null);						
				torneos.add(t);
				//ahora añado a la tabla
				modelo2.addRow(new Object[] {campos[0], campos[1], campos[2]});	

				
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error al cargar resultados");
		}
	}
	
	private void ajustes() {
		JFrame frame = new JFrame();
		frame.setSize(550,200);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("AJUSTES");
		frame.setVisible(true);
		JPanel main2 = new JPanel(new GridLayout(2,2));
		frame.add(main2);
		JTextField rTorneo = new JTextField();
		JTextField rResultados = new JTextField();
		
		main2.add(new JLabel("Ruta Torneos (CSV)")); main2.add(rTorneo);
		main2.add(new JLabel("Ruta Resultados (CSV)")); main2.add(rResultados);
		
		JButton guardar = new JButton("Actualizar Cambios");
		frame.add(guardar, BorderLayout.SOUTH);
		
		guardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rutaTorneoCSV = rTorneo.getText();
				rutaResultadoCSV = rResultados.getText();
				
				
				//cargamos nuevos y borramos antiguos
				if(!rutaResultadoCSV.isEmpty()) {
					modelo.setRowCount(0);
					resultados.clear();
					tenistas.clear();
					cargarResultadosCSV(rutaResultadoCSV);				
				}
				if(!rutaTorneoCSV.isEmpty()) {
					modelo2.setRowCount(0);					
					torneos.clear();
					cargarTorneosCSV(rutaTorneoCSV);
				}
					
				main.add(panelTablaResultados, BorderLayout.CENTER);
				main.add(panelBotonAbajo, BorderLayout.SOUTH);
				bResultados.setEnabled(false);
				main.revalidate();
				main.repaint();
				frame.dispose();	
			}
		});
		
		
	}
}
