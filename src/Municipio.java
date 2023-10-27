import javax.swing.*;

/** Permite crear objetos municipio con información de población, provincia y comunidad autónoma
 */
public class Municipio implements FilaParaJTable {  // Especializa un comportamiento de cualquier clase que podamos querer como fila en una JTable
	private int codigo;
	private String nombre;
	private int habitantes;
	private String provincia;
	private String autonomia;
	private int altitud;
	private int superficie;

	/**
	 * Crea un municipio
	 *
	 * @param codigo     Código único del municipio (1-n)
	 * @param nombre     Nombre oficial
	 * @param habitantes Número de habitantes
	 * @param provincia  Nombre de su provincia
	 * @param autonomia  Nombre de su comunidad autónoma
	 */
	public Municipio(int codigo, String nombre, int habitantes, String provincia, String autonomia, int altitud, int superficie) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.provincia = provincia;
		this.autonomia = autonomia;
		this.altitud = altitud;
		this.superficie = superficie;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHabitantes() {
		return habitantes;
	}

	public int getAltitud() {
		return altitud;
	}

	public int getSuperficie() {
		return superficie;
	}

	public void setHabitantes(int habitantes) {
		this.habitantes = habitantes;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	public void setAltitud(int altitud) {
		this.altitud = altitud;
	}

	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}

	@Override
	public String toString() {
		return "[" + codigo + "] " + nombre + ", " + habitantes + " en " + provincia + " (" + autonomia + ")" + " Altitud: " + altitud + " Superficie: " + superficie;
	}

	// Implementación de FilaParaJTable

	// Arrays que definen las clases y cabeceras de las columnas para la tabla.
	private static final Class<?>[] CLASES_COLS = {Integer.class, String.class, Integer.class, JProgressBar.class, String.class, String.class, Integer.class, Integer.class};
	private static final String[] CABECERAS_COLS = {"Código", "Nombre", "Habitantes", "Poblacion", "Provincia", "Autonomía", "Altitud", "Superficie"};

	// Devuelve la clase de dato de la columna especificada.
// Esto ayuda a la tabla a saber cómo manejar y presentar los datos en cada columna.
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return CLASES_COLS[columnIndex];
	}

	// Devuelve el número de columnas de la tabla.
// Es equivalente al tamaño del array de clases.
	@Override
	public int getColumnCount() {
		return CLASES_COLS.length;
	}

	// Proporciona el nombre de una columna específica.
// Se utiliza para mostrar las cabeceras de las columnas en la tabla.
	@Override
	public String getColumnName(int columnIndex) {
		return CABECERAS_COLS[columnIndex];
	}

	// Devuelve el valor del objeto Municipio en una columna específica.
// Utiliza una estructura switch para determinar qué atributo del objeto
// corresponde a la columna dada.
	@Override
	public Object getValueAt(int columnIndex) throws IndexOutOfBoundsException {
		switch (columnIndex) {
			case 0:
				return getCodigo();
			case 1:
				return getNombre();
			case 2:
				return getHabitantes();
			case 3:
				return getHabitantes();
			case 4:
				return getProvincia();
			case 5:
				return getAutonomia();
			case 6:
				return getAltitud();
			case 7:
				return getSuperficie();
			default:
				throw new IndexOutOfBoundsException("Columna incorrecta: " + columnIndex);
		}
	}


	// Establece el valor de un atributo del objeto Municipio basándose en la columna proporcionada.
// Utiliza una estructura switch para determinar a qué atributo se debe asignar el valor.
// Puede lanzar excepciones si se intenta configurar un valor inapropiado o en una columna que no existe.
	@Override
	public void setValueAt(Object aValue, int columnIndex) throws ClassCastException, IndexOutOfBoundsException {
		switch (columnIndex) {
			case 0:
				setCodigo((Integer) aValue);
				break;
			case 1:
				setNombre((String) aValue);
				break;
			case 2:
				setHabitantes((Integer) aValue);
				break;
			// No necesitas un setter para la JProgressBar.
			case 4:
				setProvincia((String) aValue);
				break;
			case 5:
				setAutonomia((String) aValue);
				break;
			case 6:
				setAltitud((Integer) aValue);
				break;
			case 7:
				setSuperficie((Integer) aValue);
				break;
			default:
				throw new IndexOutOfBoundsException("Columna incorrecta: " + columnIndex);
		}
	}

}

