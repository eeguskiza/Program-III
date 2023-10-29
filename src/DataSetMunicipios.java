

import java.io.*;
import java.util.*;

/** Permite gestionar datasets de municipios. Cada objeto contiene un dataset de 'n' municipios
 */
public class DataSetMunicipios extends DatasetParaJTable {
	private ArrayList<Municipio> originalMunicipios;

	/** Crea un nuevo dataset de municipios, cargando los datos desde el fichero indicado
	 * @param nombreFichero	Nombre de fichero o recurso en formato de texto. En cada línea debe incluir los datos de un municipio <br>
	 * separados por tabulador: código nombre habitantes provincia autonomía
	 * @throws IOException	Si hay error en la lectura del fichero
	 */

	public DataSetMunicipios (){
		super( new Municipio( 0, "", 0, "", "", 0, 0 ) );
		originalMunicipios = new ArrayList<>(getListaMunicipios());
	}
	public DataSetMunicipios( String nombreFichero ) throws IOException {
		super( new Municipio( 0, "", 0, "", "", 0, 0 ) );
		originalMunicipios = new ArrayList<>(getListaMunicipios());
		File ficMunicipios = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficMunicipios.exists()) {
			lecturaFic = new Scanner( ficMunicipios );
		} else {
			lecturaFic = new Scanner( DataSetMunicipios.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "\t" );
			try {
				int codigo = Integer.parseInt( partes[0] );
				String nombre = partes[1];
				int habitantes = Integer.parseInt( partes[2] );
				String provincia = partes[3];
				String comunidad = partes[4];
				int altitud = Integer.parseInt( partes[5] );
				int superficie = Integer.parseInt( partes[6] );
				Municipio muni = new Municipio( codigo, nombre, habitantes, provincia, comunidad , altitud, superficie);
				add( muni );
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}

	/** Devuelve la lista de municipios
	 * @return	Lista de municipios
	 */
	@SuppressWarnings("unchecked")
	public List<Municipio> getListaMunicipios() {
		return (List<Municipio>) getLista();
	}


	/** Añade un municipio al final
	 * @param muni	Municipio a añadir
	 */
	public void anyadir( Municipio muni ) {
		add( muni );
	}

	/** Añade un municipio en un punto dado
	 * @param muni	Municipio a añadir
	 * @param posicion	Posición relativa del municipio a añadir (de 0 a n)
	 */
	public void anyadir( Municipio muni, int posicion ) {
		anyadeFila( posicion, muni );
	}

	// Método añadido para recuperar la lista original
	public List<Municipio> getOriginalMunicipios() {
		return new ArrayList<>(originalMunicipios);
	}

	/** Quita un municipio
	 * @param codigoMuni	Código del municipio a eliminar
	 */
	public void quitar( int codigoMuni ) {
		for (int i=0; i<size(); i++) {
			if (((Municipio)get(i)).getCodigo() == codigoMuni) {
				borraFila( i );
				return;
			}
		}
	}

	// Queremos que las celdas sean editables excepto el código
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1 || columnIndex == 2;
	}

}
