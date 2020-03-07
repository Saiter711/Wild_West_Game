import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
/**
 * 
 * Klasa odpowiedzialna rozszerzająca JPanel mająca na celu wyrysowanie paczki z amunicją na panelu.
 *Klasa implementuje MouseListener
 */
public class ShootingPacket extends JPanel implements  MouseListener {
	/**
	 * Określa szerokość obiektu
	 */
	int width=50;
	/**
	 * Określa wysokość obiektu.
	 */
	int height = 50;
	/**
	 * Określa położenie na osi X w panelu.
	 */
	int axiX = 0;
	/**
	 * Określa położenie na osi Y w panelu.
	 */
	int axiY = 0;
	/**
	 * Zmienna decydująca o tym czy panel zostął naciśnięty.
	 */
	private boolean clicked = false;
	/**
	 * Zmienna przechowująca obraz poruszającego się obiektu.
	 */
	private Image image;
	int rand = (int)(Math.random()*5); 
	
	/**
	 * Konstruktor klasy ShootingPacket
	 */
	public ShootingPacket() {
		this.setLayout(new GridLayout(1, 1));
		this.setBackground(new Color(0, 0, 0, 0));
		image = Toolkit.getDefaultToolkit().createImage("resources\\packet.jpg");	
		this.addMouseListener(this);
	}
	
	/**
	 * @return Funkcja zwraca wartość zmiennej clicked
	 */
	public boolean isClicked() {
        return clicked;
    }	
	
	/**
	 * @return Funkcja zwraca szerokość panelu
	 */
    public int getwidth() {
        return width;
    }
    
	/**
	 * @return Funkcja zwraca wysokość panelu.
	 */
    public int getheight() {
        return height;
    }
    
    /**
     * Funkcja ustanawia wysokość i szerokość panelu
     * @param x Nowa szerokość panelu
     * @param y Nowa wysokość panelu
     */
	public void setSizze(int x, int y) {
		width = x;
		height = y;
	}
	
	/**
	 * Funkcja pozwala na wprowadzenie dzwięku w programie
	 * @param Sound Zmienna dzwiękowa
	 */
	public void sound(File Sound) {
        try {
        	Clip clip = AudioSystem.getClip();
        	clip.open(AudioSystem.getAudioInputStream(Sound));
        	clip.start();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
	 * Metoda interfejsu MouseListener, decyduje o tym co się stanie gdy klikniemy obiekt
	 */
    public void mouseClicked(MouseEvent e) {
    	if(GamePanel.paused == false) {
	        clicked = true;
	        GamePanel.shots+=10;
    	}
    }
    
	/**
	 * Metoda interfejsu MouseListener, decyduje o tym co się stanie gdy wciśniemy obiekt.
	 */
    @Override
    public void mousePressed(MouseEvent e) {
    	if(GamePanel.paused == false) {
    		clicked = true;
    		GamePanel.shots += 10;
    		sound(new File("resources\\Collect.WAV"));
    		GamePanel.ilosc_pakietow--;
    	}
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    /**
	 * Funkcja rysująca obiekt w panelu
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);   
        g.drawImage(image, axiX , axiY, width, height, this);  
    }  
}