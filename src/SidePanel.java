import javax.management.relation.RelationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 * Klasa odpowiadająca za cały boczny panel
 * Dziedziczy ona po JPanel oraz rozwija interfejsy Runnable i ActionListener
 * @author płcz
 *
 */
public class SidePanel extends JPanel implements Runnable, ActionListener {
	/**
	 * Zmienna przechowująca wartość początkowej szerkości planszy
	 */
	private int x = ProjectProperties.poczatkowaSzerokoscPlanszy;
	/**
	 * Zmienna przechowująca wartość początkowej wysokości planszy
	 */
	private int y = ProjectProperties.poczatkowaWysokoscPlanszy;
	/**
	 * Instancje klasy ScorePanel wyświetlające odpowiednie liczby
	 * sp - Pokazuje wynik
	 * sp1 - Pokazuje ilość pozostałych strzałów
	 * poziomy - Pokazuje aktualny poziom
	 */
	public ScorePanel sp,sp1,poziomy;
	/**
	 * Instancja klasy JPanel przechowującej przycisk
	 */
	public JPanel pb;
	/**
	 * Instancja klasy Thread, odpowiedzialna za odświeżanie panelu bocznego
	 */
	public Thread thread;
	/**
	 * Instancje klasy ImageIcon
	 * pauseb - przechowuje obraz, który będzie tłem przycisku, gdy gra jest niezapauzowana
	 * play - przechowuje obraz, który będzie tłem przycisku, gdy gra jest zapauzowana
	 */
	private ImageIcon pauseb, playb;
	/**
	 * Instancje klasy JButton
	 * pause_button - przycisk, gdy gra jest niezapauzowana
	 * play_button - przycisk, gdy gra jest zapauzowana
	 */
	private JButton pause_button, play_button;  
	/**
	 * Określa czy odświeżanie jest dalej aktywne
	 */
	private boolean running = false;
	
	/**
	 * Konstuktor klasy SidePanel
	 * Klasa wykorzystuje GridLayout do rozmieszczenia 4 paneli w wierszach
	 * Pierwszy panel - poziomy
	 * Drugi panel - wynik
	 * Trzeci panel - strzały
	 * Czwarty Panel - pauza/stop pauzy
	 */
	public SidePanel() {
		setLayout(new GridLayout(4,1));
		setMaximumSize(new Dimension(100,y));
		setPreferredSize(new Dimension(100,y));
		pauseb = new ImageIcon("resources\\pause.png");
		playb = new ImageIcon("resources\\play.png");
		pause_button = new JButton(pauseb);
		play_button = new JButton((playb));
		poziomy = new ScorePanel("Poziom: ");
		add(poziomy);
		sp = new ScorePanel("Wynik: ");
		add(sp);
		sp1 = new ScorePanel("Strzały: ");
		add(sp1);
		pb = new JPanel();
		pb.setSize(100, 100);
		add(pb);
	}
	
	/**
	 * Metoda klasy ActionListener
	 * Określa co się ma zadziać, gdy przycisk zostanie wciśnięty - czyli pauza gry i załadowanie nowego przycisku.
	 */
	public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == pause_button) {
        	GamePanel.paused = true;
            pb.remove(pause_button);
            play_button.setPreferredSize(new Dimension(playb.getIconWidth(), playb.getIconHeight()));
            play_button.setMaximumSize(new Dimension(playb.getIconWidth(), playb.getIconHeight()));
            play_button.setBounds(0, 0, 100, 100);
            play_button.addActionListener(this);
            pb.add(play_button);
        }
        else if(src == play_button) {
            GamePanel.paused = false;
            pb.remove(play_button);
            pause_button.setPreferredSize(new Dimension(playb.getIconWidth(), playb.getIconHeight()));
            pause_button.setMaximumSize(new Dimension(playb.getIconWidth(), playb.getIconHeight()));
            pause_button.setBounds(0, 0, 100, 100);
            pause_button.addActionListener(this);
            pb.add(pause_button);
        }
    }

	/**
	 * Metoda pozwalająca na rysowanie odpowiednich komponentów na panelu bocznym
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        sp.setScore(GamePanel.hit);
        sp1.setScore(GamePanel.shots);
        if(GamePanel.level == 1) {
        	poziomy.setScore(ProjectProperties.numeracjaPoziomowZaczynaSieOd);
        }
        if(GamePanel.level == 2) {
        	poziomy.setScore(ProjectProperties.numeracjaPoziomowZaczynaSieOd+1);
        }
        if(GamePanel.level == 3) {
        	poziomy.setScore(ProjectProperties.numeracjaPoziomowZaczynaSieOd+2);
        }
        if(GamePanel.level == 4) {
        	poziomy.setScore(ProjectProperties.numeracjaPoziomowZaczynaSieOd+3);
        }
        if(GamePanel.paused == false) {
            pause_button.setPreferredSize(new Dimension(pauseb.getIconWidth(), pauseb.getIconHeight()));
            pause_button.setBounds(0, 0, 120, 120);
            pause_button.addActionListener(this);
            pb.add(pause_button);
        }
        else {
            play_button.setPreferredSize(new Dimension(playb.getIconWidth(), playb.getIconHeight()));
            play_button.setBounds(0, 0, 120, 120);
            play_button.addActionListener(this);
            pb.add(play_button);
        }
    }
    
	/**
	 * Metoda interfejsu Runnable 
	 * Metoda ta określa, co ma robic wątek tej klasy (czyli aktualizować stan panelu bocznego)
	 */
	public void run() {
        int fps = 60;
        double timePerTick = 1000000000/fps; 
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        while(running) {
            now = System.nanoTime();
            delta+=(now-lastTime)/timePerTick;
            lastTime=now;
            if(delta >= 1) {
                repaint();
                delta--;
            }
            if(GamePanel.end == 1) {
            	running = false;
            	 try {
                     thread.join();
                 } 
            	 catch (InterruptedException e) {
                     e.printStackTrace();
                 }
            } 	
        }
    }
	
	/**
	 * Metoda startująca wątek klasy
	 */
	public synchronized void start() {
        if(running){
        	return;
        }
        running=true;
        thread=new Thread(this);
        thread.start();
    }

    /**
     * Metoda zatrzymuje wątek
     */
    public synchronized void stop() {
        if(!running) {
        	return;
        }
        running=false;
        try {
            thread.join();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}