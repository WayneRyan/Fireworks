
public class ExplodingParticle extends Particle {
	
	int age;

	public ExplodingParticle(double x, double y, double vX, double vY) {
		super(x, y, vX, vY);
		age = 0;
	}
	
	public void update(){
		super.update();
		age++;
	}

	public boolean timeToExplode(){
		return age>30;
	}

}
