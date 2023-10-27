import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Clase para gestionar un conjunto de datos basado en ArrayList para ser visualizado en una JTable.
 * El modelo de esta tabla es ModeloJTable.
 */
public class DatasetParaJTable extends AbstractTableModel implements TableModel {

	// Lista para almacenar datos genéricos que implementen FilaParaJTable
	private List<FilaParaJTable> listaDatos = new ArrayList<FilaParaJTable>();

	// Instancia que sirve de ejemplo para obtener información, aunque la lista esté vacía
	private FilaParaJTable instanciaEjemplo;

	// Lista de observadores o listeners que escucharán cambios en el modelo de la tabla
	private ArrayList<TableModelListener> listaEsc = new ArrayList<>();

	// Constructor que inicializa el dataset con una instancia de ejemplo
	public DatasetParaJTable( FilaParaJTable instanciaEjemplo ) {
		this.instanciaEjemplo = instanciaEjemplo;
		this.listaDatos = new ArrayList<>();
	}

	// Devuelve la clase de la columna especificada
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return instanciaEjemplo.getColumnClass(columnIndex);
	}

	// Devuelve el número total de columnas
	@Override
	public int getColumnCount() {
		return instanciaEjemplo.getColumnCount();
	}

	// Cabeceras de las columnas
	private final String[] cabeceras = { "Código", "Nombre", "Habitantes", "Poblacion", "Provincia", "Autonomía", "Altitud", "Superficie" };

	// Devuelve el nombre de la columna especificada
	@Override
	public String getColumnName(int columnIndex) {
		return cabeceras[columnIndex];
	}

	// Devuelve el número total de filas
	@Override
	public int getRowCount() {
		return listaDatos.size();
	}

	// Define si una celda es editable o no (en este caso ninguna es editable)
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	// Añade un listener a la lista de escuchadores
	@Override
	public void addTableModelListener(TableModelListener l) {
		listaEsc.add( l );
	}

	// Elimina un listener de la lista de escuchadores
	@Override
	public void removeTableModelListener(TableModelListener l) {
		listaEsc.remove( l );
	}

	// Devuelve el valor de una celda específica
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return listaDatos.get(rowIndex).getValueAt( columnIndex );
	}

	// Establece el valor de una celda específica
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		listaDatos.get(rowIndex).setValueAt( aValue, columnIndex );
	}

	// Notifica a todos los escuchadores que hubo un cambio en el modelo de la tabla
	public void fireTableChanged( TableModelEvent e ) {
		for (TableModelListener l : listaEsc) {
			l.tableChanged( e );
		}
	}

	// Elimina una fila específica del dataset
	public void borraFila( int fila ) {
		listaDatos.remove( fila );
		fireTableChanged( new TableModelEvent( this, fila, listaDatos.size() ));
	}

	// Añade un nuevo dato en una posición específica del dataset
	public void anyadeFila( int fila, FilaParaJTable dato ) {
		listaDatos.add( fila, dato );
		fireTableChanged( new TableModelEvent( this, fila, listaDatos.size() ));
	}

	// Añade un nuevo dato al final del dataset
	public void add( FilaParaJTable dato ) {
		listaDatos.add( dato );
		fireTableChanged( new TableModelEvent( this, listaDatos.size()-1, listaDatos.size() ));
	}

	// Devuelve la lista completa de datos
	public List<? extends FilaParaJTable> getLista() {
		return listaDatos;
	}

	// Devuelve un dato específico del dataset
	public FilaParaJTable get( int fila ) {
		return listaDatos.get( fila );
	}

	// Devuelve el número total de elementos en el dataset
	public int size() {
		return listaDatos.size();
	}
}
