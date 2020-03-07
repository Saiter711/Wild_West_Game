/**
 * Klasa opisująca jeden rekord w liście wyników
 * @author płcz
 *
 */
public class ListPosition {
	/**
	 * Opisuje nazwę gracza
	 */
	public String nazwa;
	
	/**
	 * Opisuje ilość trafionych strzałów
	 */
	public String trafienie;
	
	/**
	 * Opisuje ilość trafionych strzałów (ale jest to liczba całkowita)
	 */
	public int hit;
	
	/**
	 * Funkcja pozwalająca na tworzenie nowych rekordów
	 * @param a wpisywana do nazwy użytkownika
	 * @param b wpisywana do trafień
	 */
	public ListPosition(String a, String b) {
		nazwa = a;
		trafienie = b;
		hit = Integer.parseInt(trafienie);
	}
}
