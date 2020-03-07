import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.applet.Applet;
import java.applet.AudioClip;
import javax.imageio.ImageIO;

import java.net.URL;
import java.io.File;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.net.MalformedURLException;

/**
 * 
 * Klasa rozszerzająca JPanel mająca na celu wyrysowanie prostokątów na panelu.
 *
 */
 public class Shape extends JPanel implements MouseListener {
	/**
	 * Określa szerokość obiektu
	 */
	public int width = 50;
	/**
	 * Określa wysokość obiektu
	 */
	public int height = 50;
	/**
	 * Określa położenie na osi x w panelu
	 */
	public int axiX = 0;
	/**
	 * Określa położenie na osi y w panelu
	 */
	public int axiY = 0;
	/**
	 * Przechowuje wartość początkowej szerokości planszy
	 */
	public int x = ProjectProperties.poczatkowaSzerokoscPlanszy;
	/**
	 * Przechowuje wartość początkowej wyosokości planszy
	 */
	public int y = ProjectProperties.poczatkowaWysokoscPlanszy;
	/**
	 * Decyduje o tym, czy panel został naciśnięty
	 */
	private boolean clicked = false;
	/**
	 * Przechowuje obraz poruszającego się obiekty
	 */
	private Image image;
	/**
	 * Zmienna losowa potrzebna do ustalenia koloru obiektu
	 */
	public int rand = (int)(Math.random()*7);
	
	/**
	 * Konstuktor klasy Rect
	 */
	public Shape(){
		this.setLayout(new GridLayout(1, 1));
		this.setBackground(new Color(0, 0, 0, 0));
		if(GamePanel.level == 1) {
			image = Toolkit.getDefaultToolkit().createImage("resources\\bottle.png");	
		}
		else if(GamePanel.level == 2) {
			image = Toolkit.getDefaultToolkit().createImage("resources\\indian.png");	
		}
		else if (GamePanel.level == 3) {
			image = Toolkit.getDefaultToolkit().createImage("resources\\buffalo.png");	
		}
		else if(GamePanel.level == 4) {
			image = Toolkit.getDefaultToolkit().createImage("resources\\mannequin.png");	 
		}
		this.addMouseListener(this);
	}
	
	/**
	 * 
	 * @return Zwraca wartość zmiennej clicked
	 */
	public boolean isClicked() {
        return clicked;
    }
	
	/**
	 * Funkcja losuje kolor
	 * @param i Zmienna lokalna wykorzystywana aby wybrać kolor
	 * @return Zwraca wylosowany kolor
	 */
	private Color randomColor(int i) {
		if(i == 0) {
			return Color.RED;
		}
		else if(i == 1) {
			return Color.GREEN;
		}
		else if(i == 2) {
			return Color.YELLOW;
		}
		else if(i == 3) {
			return Color.BLUE;
		}
		else if(i == 4) {
			return Color.ORANGE;
		}
		else if(i == 5) {
			return Color.MAGENTA;
		}
		else {
			return Color.BLUE;
		}
	}
	
	/**
	 * 
	 * @return Funkcja zwraca szerokość panelu
	 */
    public int getwidth() {
        return width;
    }
    
	/**
	 * 
	 * @return Funkcja zwraca wysokość panelu
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
	        GamePanel.hit++;
	        GamePanel.shots--;
    	}
    }

    @Override
    /**
	 * Metoda interfejsu MouseListener, decyduje o tym co się stanie gdy wciśniemy obiekt
	 */
    public void mousePressed(MouseEvent e) {
    	if(GamePanel.paused == false) {
    		clicked = true;
    		GamePanel.hit++;
    		GamePanel.shots--;
    		sound(new File("resources\\strzal.WAV"));
    		if(GamePanel.shots == 0) {
    			GamePanel.end = 1;
    		}
    	}
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
    /**
     * Funkcja rysująca obiekt w panelu - w zależności od danych w pliku parametrycznym będzie to jeden z kształtów lub plik graficzny
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); 
        if(ProjectProperties.obiektyGry.equals("figuryGeometryczne") == true) {
        	g.setColor(randomColor(rand)); 
        	switch(ProjectProperties.figuraObiektuGry) {
        	case "prostokąty":
                g.fillRect(axiX ,axiY,width,height);
                g.setColor(Color.BLACK);
                g.drawRect(axiX ,axiY,width,height);
                break;
        	case "kółka":
        		g.fillOval(axiX ,axiY, width,height);
        		g.setColor(Color.BLACK);
        		g.drawOval(axiX ,axiY, width,height);
        		break;
        	case "trójkąty":
        		g.fillPolygon(new int[] {(int)axiX, (int)axiX+(width/2), (int)axiX+width},
                        new int[] {(int)axiY+width, (int)axiY, (int)axiY+width}, 3);
                g.setColor(Color.BLACK);
                g.drawPolygon(new int[] {(int)axiX, (int)axiX+(width/2), (int)axiX+width},
                        new int[] {(int)axiY+width, (int)axiY, (int)axiY+width}, 3);
        		break;
        	case "kwadraty":
        		 g.fillRect(axiX , axiY, width, width);
        		 g.setColor(Color.BLACK);
                 g.drawRect(axiX , axiY, width, width);
                 break;
        	}
        }
        else {
        	g.drawImage(image, axiX , axiY, width, height, this);
        }
    }  
}