package ClasesBasicas;
import java.util.Map;

public class Tenista {
    private String nombre;
    private String nacionalidad;
    private int victorias;
    private Map<String, Integer> victoriasPorTorneo;
    
    public Tenista(String nombre, String nacionalidad, int victorias, Map<String, Integer> victoriasPorTorneo) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.victorias = victorias;
        this.victoriasPorTorneo = victoriasPorTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public int getVictorias() {
        return victorias;
    }

    public Map<String, Integer> getVictoriasPorTorneo() {
        return victoriasPorTorneo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }   

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }   

    public void setVictoriasPorTorneo(Map<String, Integer> victoriasPorTorneo) {
        this.victoriasPorTorneo = victoriasPorTorneo;
    }   

    @Override
    public String toString() {
        return "Tenista{" +
                "nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", victorias=" + victorias +
                ", victoriasPorTorneo=" + victoriasPorTorneo +
                '}';
    }
}


