import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaJuego extends JFrame {
    private JPanel panelPrincipal, panelBotonera;
    private JButton bAcelerar, bFrenar, bGirarIzd, bGirarDcha;

    private CocheJuego coche;
    private JFrame ventana;

    public VentanaJuego() {
        super();
        ventana = this;
        setBounds(100, 100, 1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(Color.WHITE);
        panelBotonera = new JPanel();
        bAcelerar = new JButton("Acelerar");
        bFrenar = new JButton("Frenar");
        bGirarIzd = new JButton("Girar Izd.");
        bGirarDcha = new JButton("Girar Dcha");
        panelBotonera.add(bAcelerar);
        panelBotonera.add(bFrenar);
        panelBotonera.add(bGirarIzd);
        panelBotonera.add(bGirarDcha);
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        getContentPane().add(panelBotonera, BorderLayout.SOUTH);

        // Crea un coche con LabelCoche
        coche = new CocheJuego();
        panelPrincipal.add(coche.getCoche());

        bAcelerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coche.acelera(5);
                System.out.println(coche.getVelocidad());
            }
        });

        bFrenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coche.acelera(-5);
                System.out.println(coche.getVelocidad());
            }
        });

        bGirarIzd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coche.gira(10);
                System.out.println(coche.getDireccion());
            }
        });

        bGirarDcha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coche.gira(-10);
                System.out.println(coche.getDireccion());
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int iniciox = ventana.getX();
                int finx = iniciox + ventana.getWidth();
                int inicioy = ventana.getY();
                int finy = inicioy + ventana.getHeight();

                while (true) {
                    coche.mueve(0.04); // Tiempo de movimiento en segundos (40 ms)
                    ventana.repaint();
                    if (coche.getpX() >= finx) {
                        coche.setpX(iniciox);
                    }
                    if (coche.getpY() >= finy) {
                        coche.setpY(inicioy);
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {

                    }
                }
            }
        });
        t.start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                t.interrupt();
            }
        });
        setVisible(true);
    }


}
