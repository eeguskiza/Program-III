import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;

public class RendererTree extends DefaultTreeCellRenderer {
    private Map<String, Integer> poblacionPorProvincia;
    private JProgressBar progressBar = new JProgressBar();

    public RendererTree(Map<String, Integer> poblacionPorProvincia) {
        this.poblacionPorProvincia = poblacionPorProvincia;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.isLeaf()) { // Para provincias
                String provincia = node.getUserObject().toString();
                int poblacion = poblacionPorProvincia.getOrDefault(provincia, 0);
                progressBar.setString(provincia + " (" + poblacion + ")");
                progressBar.setStringPainted(true);
                progressBar.setValue(poblacion); // Aquí puedes ajustar el valor según tu rango
                progressBar.setMaximum(1000000); // Asumiendo que este es el máximo, ajusta según tus datos
                return progressBar;
            }
        }
        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }
}
