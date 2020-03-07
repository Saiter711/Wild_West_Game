import javax.swing.*;
import java.awt.*;
/**
 * Klasa dziedzicząca po JPanel mająca na celu wyświetlanie danych na panelu bocznym
 * @author płcz
 *
 */
public class ScorePanel extends JPanel {
	/**
	 * Zmienna klasy String, zawiera całą wiadomość, która ma zostać wyświetlona na panelu
	 * 
	 */
	private String score;
	/**
	 * Zmienna klasy String, zawiera typ wyniku (czy to poziom, wynik, czy strzały)
	 */
	private String name;
	/**
	 * Pole klasy, które zawiera liczbe do wyświetlenia
	 */
	private int hit;
	
	/**
	 * Konstruktor klasy ScorePanel
	 * @param x Podajemy tu typ wyniku (wpisywane do zmiennej score)
	 */
	public ScorePanel(String x) {
		name = x;
		setLayout(new GridLayout(1, 1));
		setOpaque(true);
	}
	
	/**
	 * Metoda pozwalająca na rysowanie każdego wyniku
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        score = name + hit;
        if(name == "Wynik: ") {
        	g.setFont(new Font("Verdana", Font.BOLD, 16));
        }
        else if(name == "Strzały: ") {
        	g.setFont(new Font("Verdana", Font.BOLD, 13));
        }
        else {
        	g.setFont(new Font("Verdana", Font.BOLD, 16));
        }
        g.drawString(score, (int)(ProjectProperties.poczatkowaSzerokoscPlanszy*0.02), (int)(ProjectProperties.poczatkowaWysokoscPlanszy*0.12));
    }
	
	/**
	 * Metoda ustanawiająca wartość zmiennej hit
	 * @param number Jest to liczba wpisywana do zmiennej hit
	 */
	public void setScore(int number) {
		hit = number;
		repaint();
	}
}
