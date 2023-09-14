package ClasesBasicas;
public class Torneo {
    private int codigo;
    private String nombre;
    private String ciudad;

    public Torneo(int codigo, String nombre, String ciudad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCuidad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Torneo{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
