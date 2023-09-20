package GUI;
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

public class VentanaGrandSlam extends JFrame {
    protected JPanel panelTablaResultados;
    protected JButton bResultados, bJugadores, bTorneos, bAjustes;
    protected JPanel panelBotonAbajo;
    private DefaultTableModel m1; // Para la tabla de resultados
    private DefaultTableModel m2; // Para la tabla de tenistas
    protected List<Resultado> resultados;
    protected List<Torneo> torneos;
    protected List<Tenista> tenistas;
    protected JPanel principal;
    protected String rutaTorneoCSV = "torneos.csv";
    protected String rutaResultadoCSV = "US Open Championships, Champion vs Runner-up, Men's Singles,1968-2021";

    public VentanaGrandSlam() {
        super();
        this.setTitle("Grand Slam de Tenis");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        principal = new JPanel(new BorderLayout());
        this.add(principal);
        tenistas = new ArrayList<>();

        // ----PANEL SUPERIOR DE BOTONES---
        JPanel panelSuperiorBotones = new JPanel(new FlowLayout());
        principal.add(panelSuperiorBotones, BorderLayout.NORTH);

        bJugadores = new JButton("Jugadores");
        bTorneos = new JButton("Torneos");
        bResultados = new JButton("Resultados");
        bAjustes = new JButton("Ajustes");

        //bAjustes.setBackground(Color.GREEN); --> En MacOs no funciona

        panelSuperiorBotones.add(bJugadores);
        panelSuperiorBotones.add(bTorneos);
        panelSuperiorBotones.add(bResultados);
        panelSuperiorBotones.add(bAjustes);

       // ------ PANEL DE RESULTADOS ------
    JPanel panelResultados = new JPanel();
    panelResultados.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
    panelResultados.setLayout(new BorderLayout());

    String[] columnasTitulos = {"Año", "Torneo", "Campeón", "Ranking Campeón", "Nacionalidad Camp.", "Subcampeón", "Ranking SUB.", "Nacionalidad SUB.", "Resultado"};
    DefaultTableModel modelo = new DefaultTableModel(columnasTitulos, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    JTable tablaResultados = new JTable(modelo);
    tablaResultados.getTableHeader().setBackground(Color.LIGHT_GRAY); // Encabezados de columna en gris claro

    JScrollPane scrollPane = new JScrollPane(tablaResultados);
    panelResultados.add(scrollPane, BorderLayout.CENTER);

        

    }
}
