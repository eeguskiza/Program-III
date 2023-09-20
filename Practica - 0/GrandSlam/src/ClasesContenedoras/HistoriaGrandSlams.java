package ClasesContenedoras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import ClasesBasicas.*;

public class HistoriaGrandSlams {
    private List<Resultado> resultados;
    private Map<String, Torneo> torneosPorNombre;
    private Map<String, Torneo> torneosPorCodigo;
    private Map<String, Tenista> tenistasPorNombre;

    public HistoriaGrandSlams(List<Tenista> tenistas, List<Torneo> torneos, List<Resultado> resultados) {
        super();
		this.resultados = resultados;
		this.torneosPorNombre = new HashMap<>();
		this.torneosPorCodigo = new HashMap<>();
		this.tenistasPorNombre = new HashMap<>();
		
		//Logica para rellenar los hashmaps con la info de las listas
		for(Torneo t : torneos) {
			this.torneosPorNombre.put(t.getNombre(), t);
			this.torneosPorCodigo.put(t.getCodigo(), t);
		}
		for(Tenista t: tenistas) {
			this.tenistasPorNombre.put(t.getNombre(), t);
		}
    }

    // Método para cargar los datos desde ficheros CSV, por ejemplo.
    public void cargarDatosDesdeCSV(String resultadosCSV, String torneosCSV) {
        // Implementa la lógica para cargar los datos desde los archivos CSV aquí.
    }

    // Método para calcular la clasificación entre los años dados.
    public Map<String, Integer> calculaClasificacion(int anyoInicial, int anyoFinal) {
        Map<String, Integer> clasificacion = new HashMap<>();
        
        for (Resultado resultado : resultados) {
            int anyoTorneo = resultado.getAño();
            if (anyoTorneo >= anyoInicial && anyoTorneo <= anyoFinal) {
                String nombreTenista = resultado.getGanador().toUpperCase();
                clasificacion.put(nombreTenista, clasificacion.getOrDefault(nombreTenista, 0) + 1);
            }
        }
        
        return clasificacion;
    }

    
}




