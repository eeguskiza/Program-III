public class CocheJuego extends Coche{
	private LabelCoche coche;
	
	public CocheJuego() {
		coche = new LabelCoche("coche.png");
	}

	public LabelCoche getCoche() {
		return coche;
	}

	public void setCoche(LabelCoche coche) {
		this.coche = coche;
	}

	@Override
	public void setpX(double pX) {
		this.pX = pX;
		coche.setBounds((int) pX, coche.getY(), coche.getWidth(), coche.getHeight());
	}
	
	@Override
	public void setpY(double pY) {
		this.pY = pY;
		coche.setBounds(coche.getX(), (int) pY , coche.getWidth(), coche.getHeight());
	}
	
	@Override
	public void mueve( double tiempoDeMovimiento ) {
		super.mueve(tiempoDeMovimiento);
		coche.setBounds((int) pX, coche.getY(), coche.getWidth(), coche.getHeight());
		coche.setBounds(coche.getX(), (int) pY , coche.getWidth(), coche.getHeight());
	}
}
