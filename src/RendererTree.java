import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;




public class RendererTree extends DefaultTreeCellRenderer {
    private Map<String, Integer> poblacionPorProvincia;

    public RendererTree(Map<String, Integer> poblacionPorProvincia) {
        this.poblacionPorProvincia = poblacionPorProvincia;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.isLeaf()) {
                String provincia = value.toString();
                int poblacion = poblacionPorProvincia.getOrDefault(provincia, 0);
                label.setText(provincia + " (" + poblacion + ")");
            }
        }
        return c;
    }
}