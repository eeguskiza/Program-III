import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Arbol {

    private JFrame frame;

    // Constructor de la clase
    public Arbol() {
        frame = new JFrame("Ventana personalizada");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Label de mensaje en la parte superior
        JLabel messageLabel = new JLabel(" ");
        frame.add(messageLabel, BorderLayout.NORTH);

        // JTree a la izquierda
        JTree tree = new JTree();
        JScrollPane treeScrollPane = new JScrollPane(tree);
        frame.add(treeScrollPane, BorderLayout.WEST);

        // Tabla en el centro
        String[] columnNames = {"Columna 1", "Columna 2"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // Panel de visualización a la derecha
        JPanel viewPanel = new JPanel();
        frame.add(viewPanel, BorderLayout.EAST);

        // Botonera en la parte inferior
        JPanel buttonPanel = new JPanel();
        JButton insertButton = new JButton("Inserción");
        JButton deleteButton = new JButton("Borrado");
        JButton orderButton = new JButton("Orden");

        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(orderButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
