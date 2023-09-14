package ClasesContenedoras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import ClasesBasicas.*;

public class HistoriaGrandSlams {
    private List<Resultado> resultados;
    private Map<String, Torneo> torneosPorNombre;
    private Map<Integer, Torneo> torneosPorCodigo;
    private Map<String, Tenista> tenistas;

    public HistoriaGrandSlams() {
        resultados = new ArrayList<>();
        torneosPorNombre = new HashMap<>();
        torneosPorCodigo = new HashMap<>();
        tenistas = new HashMap<>();
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




