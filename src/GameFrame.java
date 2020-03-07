import javax.management.relation.RelationException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Klasa rozszerzająca JFrame na potrzeby naszego programu.
 * Jest w niej realizowana cała gra - pojawianie się i poruszanie obiektów oraz interaktywny panel boczny.
 */
public class GameFrame extends JFrame {
	/**
	 * Obiekt klasy JMenuBar, dzięki któremu tworzymy menu.
	 */
	private JMenuBar menu;
	
	/**
	 * Instancja klasy BackImage (odpowiedzialna za rysowanie obszaru, gdzie strzelamy do obiektów)
	 */
	public GamePanel backim;
	
	public static boolean nowy = false;
	
	/**
	 * Instancja klasy SidePanel, czyli panelu bocznego
	 */
	public SidePanel sp;
	
	/**
	 * Elementy menu (rozwijalnego).
	 */
	private String[] elementy = {
		"Nowa gra", "Lista wyników", "Start / Pauza", "Wybór celownika", "Zakończ"	
	};
	
	/**
	 * Metoda (konstruktor) który odpowiada za ustawienie początkowego wyglądu interface'u wyświetlanego na ekranie.
	 * Konstruktor dodaje panel gry, dodaje menu oraz wyświetla obrazy i celność gracza.
	 * Ustawia on również inny kursor po najechaniu na ramkę.
	 * Menu jest klikalne poprzez wykorzystanie MouseListenerów do każdej pozycji z menu.
	 */
	public GameFrame() {
		super(ProjectProperties.nazwaGry);
		
		/**
		 *  Jest to zmienna przechowująca wartość początkowej szerokości okna z pliku "par.txt" (dla zmniejszenia pisanego tekstu)
		 */
		int x = ProjectProperties.poczatkowaSzerokoscPlanszy;
		
		/**
		 *   Jest to zmienna przechowująca wartość początkowej wysokości okna
		 */
		int y = ProjectProperties.poczatkowaWysokoscPlanszy;
		
		// Ustawianie Frame'a
		setLocation(100, 100);
		setSize(x, y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Dodawanie menu
		menu = new JMenuBar();
		setJMenuBar(menu);
		JMenu p1 = new JMenu("Menu"); 
		JMenu p2 = new JMenu("How2Play"); 
		
		menu.add(p1); 
		menu.add(p2);
		
		for(int i = 0;i < elementy.length;i++) {
			JMenuItem roz11 = new JMenuItem(elementy[i]);
			p1.add(roz11);
		}
		// klikanie w rozwijane menu		
		JMenuItem newgame = p1.getItem(0);		
		newgame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
				nowy = true;
				GamePanel.running = false;
				new StartingWindow();
				GamePanel.hit=0;
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JMenuItem scorelist = p1.getItem(1);
		scorelist.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getSource() != null) {
					File plik = new File("Wyniki.txt");
					String calatresc="";
					try{
						@SuppressWarnings("resource")
						Scanner in = new Scanner(plik);
						for(int i=1;i<11;i++) {	
							String line=in.nextLine();
							calatresc+=i+". "+line+"\n";
						}
					}
					catch (FileNotFoundException b) {
						System.out.println("Nie udało się wczytać pliku!");
					}
					JOptionPane.showMessageDialog(new JFrame(), calatresc,"Wyniki",JOptionPane.PLAIN_MESSAGE);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {}			
			@Override
			public void mouseClicked(MouseEvent e) {}		
		});
		
		JMenuItem pauza = p1.getItem(2);
		pauza.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(GamePanel.paused == false) {
					GamePanel.paused=true;
					sp.repaint();
				}
				else {
					GamePanel.paused=false;
					sp.repaint();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}	
		});
		
		JMenuItem celow = p1.getItem(3);
		celow.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				JFrame celowniki= new JFrame();
				celowniki.setLocation(20, 20);
				celowniki.setSize(x/3,y/3);
				celowniki.setLayout(new GridLayout(5,1));
				JLabel celownikinapis = new JLabel("Wybierz celownik");
				celownikinapis.setHorizontalAlignment(JLabel.CENTER);
				celownikinapis.setForeground(Color.RED);
				celownikinapis.setFont(new Font("SansSerif",Font.BOLD,14));
				celownikinapis.setHorizontalAlignment(JLabel.CENTER);
				JButton cel1 = new JButton ("Łuk");
				JButton cel2 = new JButton ("Pistolet");
				JButton cel3 = new JButton ("Czarny kursor");
				JButton cel4 = new JButton ("Domyślny");
				celowniki.add(celownikinapis);
				celowniki.add(cel1);
				celowniki.add(cel2);
				celowniki.add(cel3);
				celowniki.add(cel4);
				celowniki.setVisible(true);
			
				cel1.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mouseClicked(MouseEvent e) {
						Cursor c = new Cursor(Cursor.CROSSHAIR_CURSOR);
						setCursor(c);
						Toolkit t1 = Toolkit.getDefaultToolkit();
						Image img = t1.getImage("resources\\bow.png");
						Point point = new Point(0,0);
						Cursor cursor = t1.createCustomCursor(img, point, "Cursor");
						setCursor(cursor); 
					 }	
				 });
				 
				 cel2.addMouseListener(new MouseListener() {
					 @Override
					 public void mouseReleased(MouseEvent e) {}
					 @Override
					 public void mousePressed(MouseEvent e) {}
					 @Override
					 public void mouseEntered(MouseEvent e) {}
					 @Override
					 public void mouseExited(MouseEvent e) {}
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 Cursor c = new Cursor(Cursor.HAND_CURSOR);
						 setCursor(c);
						 Toolkit t1 = Toolkit.getDefaultToolkit();
						 Image img = t1.getImage("resources\\pistol.png");
						 Point point = new Point(0,0);
						 Cursor cursor = t1.createCustomCursor(img, point, "Cursor");
						 setCursor(cursor);
					 }		 
				 });
				 
				 cel3.addMouseListener(new MouseListener() {
					 @Override
					 public void mouseReleased(MouseEvent e) {}
					 @Override
					 public void mousePressed(MouseEvent e) {}
					 @Override
					 public void mouseEntered(MouseEvent e) {}
					 @Override
					 public void mouseExited(MouseEvent e) {}
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 Toolkit t1 = Toolkit.getDefaultToolkit();
						 Image img = t1.getImage("resources\\black_cursor.png");
						 Point point = new Point(0,0);
						 Cursor cursor = t1.createCustomCursor(img, point, "Cursor");
						 setCursor(cursor); 	
					 }
				 });
				 
				 cel4.addMouseListener(new MouseListener() {
					 @Override
					 public void mouseReleased(MouseEvent e) {}
					 @Override
					 public void mousePressed(MouseEvent e) {}
					 @Override
					 public void mouseEntered(MouseEvent e) {}
					 @Override
					 public void mouseExited(MouseEvent e) {}
					 @Override
					 public void mouseClicked(MouseEvent e)  {
						 Cursor c = new Cursor(Cursor.CROSSHAIR_CURSOR);
						 setCursor(c);	
					 }
				 }); 	
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}		
		});
		
		JMenuItem endgame = p1.getItem(4);
		endgame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {		
				Runtime.getRuntime().exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		}); 
		
		p2.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource() != null)
				{				
					File plik = new File("How2Play.txt");
					String calatresc="";
					String str;
					try {
						BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(new File("./How2Play.txt")), StandardCharsets.UTF_8));
						while((str = in.readLine()) != null) {
							calatresc+=str+"\n";		
						}
						in.close();
					}
					catch (Exception b){
						System.out.println("Nie udało się wczytać pliku!");	
					}
					JOptionPane.showMessageDialog(new JFrame(), calatresc, "How2Play", JOptionPane.PLAIN_MESSAGE);	
				}	
			}				
		});	
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		setLayout(new GridLayout(1, 1));
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		backim = new GamePanel();
		backim.setMinimumSize(new Dimension(x-150, y));
		backim.setPreferredSize(new Dimension(x-125, y));
		backim.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		panel.add(backim);
		backim.start();
		sp = new SidePanel();
		sp.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		sp.setPreferredSize(new Dimension(125, Integer.MAX_VALUE));
		sp.setMinimumSize(new Dimension(100, y));
		panel.add(sp);
		sp.start();
		add(panel);
		// stawienie kursora
		Cursor c = new Cursor(CROSSHAIR_CURSOR);
		setCursor(c);
		setVisible(true);
		backim.addMouseListener( new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				Object source = e.getSource();
				if(source != GamePanel.figures) {
					GamePanel.shots--;
					sound(new File("resources\\miss.WAV"));
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
		});
	}
	
	/**
	 * Funkcja umożliwia wprowadzenie dzwięku
	 * @param Sound Parametr pozwalający na dodanie odpowiedniego dzwięku
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
	 * @return Zwraca wartość wysokości Frame'a
	 */
	public  int getHeight() {
		return getSize().height;
	}
	
	/**
	 * @return Zwraca wartość szerokości Frame'a
	 */
	public int getWidth() {
		return getSize().width;
	}
}