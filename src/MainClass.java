import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class MainClass extends JFrame implements Runnable, MouseListener{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private BufferedImage offscreen;
	private Graphics bg;
	
	private LockableList<Particle> allParticles;
	
	public MainClass(){
		allParticles = new LockableList<Particle>();
		offscreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		this.addMouseListener(this);
		new Thread(this).start();
	}
	
	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
	}
	
	public void paint(Graphics g){
		bg.setColor(new Color(0.1f,0.1f,0.1f,0.1f));
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		allParticles.acquire();
		for(Particle p : allParticles)p.draw(bg);
		allParticles.release();
		g.drawImage(offscreen, 0, 0, null);
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(30);
				for(int i=0 ; i<allParticles.size() ; i++){
					Particle p = allParticles.get(i);
					p.update();
					allParticles.acquire();
					if(p.isOffScreen())allParticles.remove(p);
					allParticles.release();
					if(p instanceof ExplodingParticle){
						if(((ExplodingParticle) p).timeToExplode()){
							allParticles.acquire();
							allParticles.remove(p);
							allParticles.release();
							this.explode((ExplodingParticle)p);
						}
					}
				}
				repaint();
			} catch (InterruptedException e) {
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	private MouseEvent press;
	@Override
	public void mousePressed(MouseEvent e) {
		press = e;		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		double vX = (x - press.getX())/10.0;
		double vY = (y - press.getY())/10.0;
		allParticles.acquire();
		allParticles.add(new ExplodingParticle(x,y,vX,vY));
		allParticles.release();
		
	}
	
	public void explode(ExplodingParticle p){
		allParticles.acquire();
		for(int i=0 ; i<1000 ; i++){
			allParticles.add(new Particle(p));
		}
		allParticles.release();
	}

}
