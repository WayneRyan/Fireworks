import java.awt.Color;
import java.awt.Graphics;


public class Particle {
	public static final double GRAVITY = 0.1;
	private double vX,vY,x,y;
	private Color myColor;
	
	public Particle(double x, double y,double vX, double vY){
		this.vX = vX;
		this.vY = vY;
		this.x = x;
		this.y = y;
		myColor = randomColor();
	}
	public Particle(double x, double y){
		this.x = x;
		this.y = y;
		myColor = randomColor();
	}
	
	public Particle(Particle other) {
		this.x = other.x;
		this.y = other.y;
		this.vX = Math.random()*10-5;
		this.vY = Math.random()*10-5;
		double distance = Math.sqrt(vX*vX+vY*vY);
		while(distance>5){
			this.vX = Math.random()*10-5;
			this.vY = Math.random()*10-5;
			distance = Math.sqrt(vX*vX+vY*vY);
		}
		myColor = randomColor();
	}
	
	public boolean isOffScreen(){
		return this.y > MainClass.HEIGHT;
	}
	
	private Color randomColor(){
		float r = (float)Math.random();
		float g = (float)Math.random();
		float b = (float)Math.random();
		return new Color(r,g,b);
	}
	
	public void draw(Graphics g){
		g.setColor(myColor);
		g.fillOval((int)x, (int)y, 7, 7);
	}
	
	public void update(){
		x +=vX;
		y +=vY;
		vY+= GRAVITY;
	}
	
}
