import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class RendererTree extends DefaultTreeCellRenderer {
    private JPanel panel;
    private JLabel label;
    private JProgressBar progressBar;

    public RendererTree() {
        // Inicializamos los componentes
        panel = new JPanel(new BorderLayout());
        label = new JLabel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Para que muestre texto (porcentaje)

        panel.add(label, BorderLayout.WEST);
        panel.add(progressBar, BorderLayout.EAST);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component renderer = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        String text = value.toString();
        String[] parts = text.split(" ");
        if (parts.length > 1 && parts[1].contains("/")) {
            String[] populations = parts[1].replace("(", "").replace(")", "").split("/");
            int currentPopulation = Integer.parseInt(populations[0]);
            int totalPopulation = Integer.parseInt(populations[1]);

            int percentage = (int) (((double) currentPopulation / totalPopulation) * 100);
            progressBar.setValue(percentage);
            progressBar.setString(percentage + "%");

            label.setText(parts[0]);
            return panel;
        }

        return renderer;
    }
}

