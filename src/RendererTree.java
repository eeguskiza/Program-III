import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;
public class RendererTree extends DefaultTreeCellRenderer {
    private Map<String, Integer> poblacionPorProvincia;
    private JProgressBar progressBar = new JProgressBar(0, 1000000);
    private JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel label = new JLabel();

    public RendererTree(Map<String, Integer> poblacionPorProvincia) {
        this.poblacionPorProvincia = poblacionPorProvincia;
        panel.add(label);
        panel.add(progressBar);
        progressBar.setPreferredSize(new Dimension(100, 15));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.isLeaf()) { // Asumimos que solo las provincias son hojas
                String provincia = node.getUserObject().toString();
                int poblacion = poblacionPorProvincia.getOrDefault(provincia, 0);
                label.setText(provincia);
                progressBar.setValue(poblacion);
                return panel;
            }
        }
        return this;
    }
}

