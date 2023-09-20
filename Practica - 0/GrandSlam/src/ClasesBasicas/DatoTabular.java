package ClasesBasicas;
import java.util.ArrayList;
import java.util.List;

public abstract class DatoTabular{

	protected List<String> cabeceras;
    protected List<Object> valores;

    public DatoTabular(List<String> cabeceras) {
        this.cabeceras = cabeceras;
        this.valores = new ArrayList<>();
    }

    public Object getValor(String cabecera) {
        int index = cabeceras.indexOf(cabecera);
        if (index >= 0 && index < valores.size()) {
            return valores.get(index);
        }
        return null;
    }

    public void setValor(String cabecera, Object valor) {
        int index = cabeceras.indexOf(cabecera);
        if (index >= 0 && index < valores.size()) {
            valores.set(index, valor);
        }
    }

    public Object getValor(int numCampo) {
        if (numCampo >= 0 && numCampo < valores.size()) {
            return valores.get(numCampo);
        }
        return null;
    }

    public void setValor(int numCampo, Object valor) {
        if (numCampo >= 0 && numCampo < valores.size()) {
            valores.set(numCampo, valor);
        }
    }

    public static List<DatoTabular> cargaCsv(String csvFilePath) {
        // Aquí implementa la lógica para cargar datos desde un archivo CSV y devolver una lista de DatoTabular.
        // Puedes usar una librería de manejo de CSV o implementar tu propio código.
        // Retorna la lista de datos cargados.
        return new ArrayList<>();
    }

    public void guardarCsv(String csvFilePath) {
        // Aquí implementa la lógica para guardar los datos en un archivo CSV.
    }
}

