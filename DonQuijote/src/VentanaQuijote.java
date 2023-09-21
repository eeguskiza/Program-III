import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

public class VentanaQuijote extends JFrame implements ActionListener {
    private JTextArea areaTexto;
    private JButton btnPaginaArriba, btnPaginaAbajo;
    private JScrollPane scrollPane;
    private JPanel panelBotones;

    public VentanaQuijote() {
        super("Don Quijote de la Mancha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);

        scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        btnPaginaArriba = new JButton("^");
        btnPaginaArriba.addActionListener(this);

        btnPaginaAbajo = new JButton("v");
        btnPaginaAbajo.addActionListener(this);

        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2));
        panelBotones.add(btnPaginaArriba);
        panelBotones.add(btnPaginaAbajo);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setSize(600, 400);
        setVisible(true);

        cargarTexto();
    }

    private void cargarTexto() {
    String texto = "";
    try {
        File file = new File("/Users/erikeguskiza/Documents/Program III/DonQuijote/src/DonQuijote.txt");
        Scanner scanner = new Scanner(file, "UTF-8");
        texto = scanner.useDelimiter("\\A").next();
        scanner.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    areaTexto.setText(texto);
}

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPaginaArriba) {
            int currentPos = scrollPane.getVerticalScrollBar().getValue();
            final int newPos = currentPos - scrollPane.getViewport().getHeight();
            int maxPos = scrollPane.getVerticalScrollBar().getMaximum();
            final Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scrollPane.getVerticalScrollBar().setValue(newPos);
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else if (e.getSource() == btnPaginaAbajo) {
            int currentPos = scrollPane.getVerticalScrollBar().getValue();
            final int newPos = currentPos + scrollPane.getViewport().getHeight();
            int maxPos = scrollPane.getVerticalScrollBar().getMaximum();
            final Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scrollPane.getVerticalScrollBar().setValue(newPos);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
	public static void main(String[] args) {
		VentanaQuijote v = new VentanaQuijote();
		v.setVisible( true );
		v.cargarTexto();
	}
}