import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;

/**
  * Klasa rozszerzająca JFrame mająca na celu wywołanie okna startowego
 *	W klasie znajdują się liczne MouseListenery, które pozwalają na dodanie funkcjonalności znajdującym się w oknie przyciskom
 *Klasa pozwala na przejście do gry, wyświetlenie wyników, mini instrukcji gry oraz wyjście z aplikacji
 *Gdy naciśniemy przycisk "Nowa gra" pojawi się okno do wpisania nazwy użytkownika (trzeba wprowadzić jakikolwiek znak, żeby przejść dalej)
 *Następnie pojawi się okno z wyborem poziomu trudności, a po tym pojawi się okno z grą
 *Klasa korzysta z licznych JPaneli, JButtonów oraz layoutów
 */
public class StartingWindow extends JFrame  {
	/**
	* Zmienna statyczna określająca poziom trudności
	*/
	public static String poziomtrudnosci;
	/**
	 * Zmienna BufferedReader pozwalająca na odczyt z pliku
	 */
	public BufferedReader in;
	/**
	 * Zmienna statyczna przechowujące nazwe uzytkownika
	 */
	public static String nazwa;
	
	/**
	 * Konstuktor klasy StartingWindow odpowiedzialny za wywołanie okna startowego, jego wyglad oraz funkcjonalność.
	 * Okno startowe ma zaimplementowany wlasny layout ( BorderLayout ). Za pomoca interfejsu MouseListener uwarunkowalismy działanie programu.
	 * Okno startowe pozwala nam na rozpoczęcie nowej gry, wyświetlenie listy najlepszych wyników, samouczka oraz zakończenie gry.
	 */
	public StartingWindow() {
		super(ProjectProperties.nazwaGry);
		
		/**
		 * @param x Jest to zmienna przechowująca wartość początkowej szerokości okna z pliku "par.txt".
		 */
		int x = ProjectProperties.poczatkowaSzerokoscPlanszy;
		
		/**
		 * @param y Jest to zmienna przechowująca wartość początkowej wysokości okna.
		 */
		int y = ProjectProperties.poczatkowaWysokoscPlanszy;
		setLocation(100, 100);
		setSize(900,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(x, y);
		mainPanel.setBackground(new Color(255,255,255));
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);
		
		JPanel P = new JPanel();
		P.setLayout(new GridBagLayout());
		P.setBackground(new Color(255,255,255));
		JLabel napis11 = new JLabel("Menu Główne");
		napis11.setForeground(Color.ORANGE);
		napis11.setFont(new Font("SansSerif",Font.BOLD,40));
		napis11.setHorizontalAlignment(JLabel.CENTER);
		P.add(napis11);
		mainPanel.add(P,BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();	
		
		JButton przyciskwidmo2 = new JButton("Przycisk widmo 2");
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JButton przycisk1 = new JButton("Nowa Gra");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		P.add(przycisk1,gbc);
		
		
		JButton przycisk2 = new JButton("Lista Wynikow");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		P.add(przycisk2,gbc);
		
		
		JButton przycisk3 = new JButton("How2Play");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		P.add(przycisk3,gbc);
	
		
		JButton przycisk4 = new JButton("Zakoncz Gre");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		P.add(przycisk4,gbc);


		
		JButton przyciskwidmo = new JButton("Przycisk widmo");
		gbc.gridx = 5;
		gbc.gridy = 5;
		
		JPanel plewo = new JPanel();
		plewo.setBackground(new Color(255,255,255));
		plewo.setLayout(new BorderLayout());
		
		JLabel profile = new JLabel();
		profile.setBounds(10, 10, 205, 205);
		ImageIcon MyImage = new ImageIcon("resources\\logo.jpeg");
		Image img = MyImage.getImage();
		Image newimg = img.getScaledInstance(profile.getWidth(), profile.getWidth(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newimg);
		profile.setIcon(image);
		
		plewo.add(profile,BorderLayout.CENTER);
		
		mainPanel.add(plewo,BorderLayout.WEST);
		
		JPanel pprawo = new JPanel();
		pprawo.setBackground(new Color(255,255,255));
		pprawo.setLayout(new BorderLayout());
		
		JLabel lprawo = new JLabel();
		lprawo.setIcon(new ImageIcon("resources\\logo.jpeg"));
	
		pprawo.add(lprawo,BorderLayout.CENTER);
		mainPanel.add(pprawo,BorderLayout.EAST);
		
		JLabel napis2 = new JLabel("Autorzy: Patryk Łempio & Cezary Zalewski ");
		napis2.setForeground(Color.ORANGE);
		napis2.setFont(new Font("SansSerif",Font.BOLD,18));
		napis2.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(napis2,BorderLayout.SOUTH);
		
		JLabel napis1 = new JLabel("Witamy w grze: \n Wild West");
		napis1.setForeground(Color.ORANGE);
		napis1.setFont(new Font("SansSerif",Font.BOLD,30));
		napis1.setHorizontalAlignment(JLabel.CENTER);
		
		mainPanel.add(napis1,BorderLayout.NORTH);
	
		przycisk1.addMouseListener(new MouseListener() {
			@Override	
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {	
				Object result = JOptionPane.showInputDialog(null, "Podaj nazwe gracza: ");
				nazwa =(String) result;
				if( ((String) result).isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Musisz podac nazwe gracza!");
				}
			
				else if(((String) result).isEmpty() == false) {
					System.out.println(result);
					System.out.println(nazwa);
					
					JFrame poziomy = new JFrame();
					poziomy.setLocation(100, 100);
					poziomy.setSize(x/2,y/2);
					poziomy.setLayout(new GridLayout(ProjectProperties.liczbaStopniTrudnosci+1,1));
					JLabel poziomynapis = new JLabel("Wybierz poziom trudnosci");
					
					poziomynapis.setHorizontalAlignment(JLabel.CENTER);
					poziomynapis.setForeground(Color.ORANGE);
					poziomynapis.setFont(new Font("SansSerif",Font.BOLD,20));
					poziomynapis.setHorizontalAlignment(JLabel.CENTER);
				
					poziomy.add(poziomynapis);
				
					JButton latwy = new JButton("Łatwy");
					JButton sredni = new JButton("Sredni");
					JButton zaawansowany = new JButton("Zaawansowany");
					JButton trudny = new JButton("Trudny");
					JButton pro = new JButton("Pro");
					
					if(ProjectProperties.liczbaStopniTrudnosci == 2) {
						poziomy.add(latwy);
						poziomy.add(sredni);
					}
					else if(ProjectProperties.liczbaStopniTrudnosci == 3) {
						poziomy.add(latwy);
						poziomy.add(sredni);
						poziomy.add(zaawansowany);
					}
					else if (ProjectProperties.liczbaStopniTrudnosci == 4) {
						poziomy.add(latwy);
						poziomy.add(sredni);
						poziomy.add(zaawansowany);
						poziomy.add(trudny);
					}
					else if (ProjectProperties.liczbaStopniTrudnosci == 5) {
						poziomy.add(latwy);
						poziomy.add(sredni);
						poziomy.add(zaawansowany);
						poziomy.add(trudny);
						poziomy.add(pro);
					}
					latwy.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							poziomtrudnosci="Łatwy";
							new GameFrame();
							poziomy.dispose();
						}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseReleased(MouseEvent e) {}
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}	
					});
					
					sredni.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							poziomtrudnosci="Sredni";
							new GameFrame();
						}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseReleased(MouseEvent e) {}
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}	
					});
					
					zaawansowany.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							poziomtrudnosci="Zaawansowany";
							new GameFrame();
						}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseReleased(MouseEvent e) {}
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}	
					});
					
					trudny.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							poziomtrudnosci="Trudny";
							new GameFrame();
						}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseReleased(MouseEvent e) {}
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}	
					});
					
					pro.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							poziomtrudnosci="Pro";
							new GameFrame();
						}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseReleased(MouseEvent e) {}
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}
					});
					poziomy.setVisible(true);
				dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		}); 
		
		przycisk2.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getSource() != null) {
					File plik = new File("Wyniki.txt");
					String calatresc="";
					try {
						in = new BufferedReader( new InputStreamReader(new FileInputStream(new File("./Wyniki.txt")), StandardCharsets.UTF_8));
						for(int i=1;i<11;i++) {	
							String line=in.readLine();
							calatresc+=i+". "+line+"\n";
						}
						in.close();
					}
					catch (Exception b) {
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
		
		przycisk3.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {		
				if(e.getSource() != null) {				
					File plik = new File("How2Play.txt");
					String calatresc="\t";
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
					JOptionPane.showMessageDialog(new JFrame(), calatresc,"How2Play",JOptionPane.PLAIN_MESSAGE);	
				}	
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		przycisk4.addMouseListener(new MouseListener() {
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
		
		setVisible(true);
	}
}
