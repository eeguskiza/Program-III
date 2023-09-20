public class Coche {
    private double velocidad;; 
	protected double direccion; 
	protected double pX; 
	protected double pY; 
	private String piloto; 

    public Coche() {
		super();
		velocidad = 0;
		direccion = 0;
		pX = 0;
		pY = 0;
		piloto = "";
	}

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public double getDireccion() {
        return direccion;
    }

    public void setDireccion(double direccion) {
        this.direccion = direccion;
    }

    public double getpX() {
        return pX;
    }

    public void setpX(double x) {
        pX = x;
    }

    public double getpY() {
        return pY;
    }

    public void setpY(double y) {
        pY = y;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    @Override
	public String toString() {
        return "Coche [velocidad=" + velocidad + ", direccion=" + direccion + ", X=" + pX + ", Y=" + pY + ", piloto="
                + piloto + "]";
    }

    /** Cambia la velocidad actual del coche
	* @param aceleracion 
	*/
	public void acelera( double aceleracion ) {
		velocidad += aceleracion;
	}

	/** Cambia la dirección actual del coche
	* @param giro 
	*/
	public void gira( double giro ) {
		direccion += giro;
	}

	/** Cambia la posición del coche 
	* @param tMov 
	*/
	public void mueve( double tMov ) {
		pX += velocidad * tMov * Math.cos(direccion);
		pY += velocidad * tMov * Math.sin(direccion);
	}

    
    
}
