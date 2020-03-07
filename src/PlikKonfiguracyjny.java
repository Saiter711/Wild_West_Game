import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Klasa tworząca dodatkowy plik konfiguracyjny
 * do pliku wpisywane są parametry takie jak liczba żyć lub pliki.jpeg, które przechowują grafikę celowników
 *  
 * @author PLCZ
 */
public class PlikKonfiguracyjny {
	/**
	 * Znak końca linii
	 */
	final private String nl = System.getProperty("line.separator");
	
	/**
	 * Tablica typu String przechowująca nazwy plików graficznych do celowników
	 */
	final private String celowniki[] = {
			"celownika.jpeg", "celownikb.jpeg", "celownikc.jpeg"
	};
	
	/**
	 * Tablica typu int przechowująca możliwe wartości początkowe żyć gracza w grze.
	 */
	final private int zycia[] = {
			1, 2, 3
	};
	
	/**
	 *  Funkcja losuje z jej argumentów jeden obiekt typu String
	 * @param strings Zmienna ilość argumentów typu String
	 * @return Zwraca obiekt typu String (jeden z podanych argumentów).
	 */
	private final String losowo(String...strings) {
		return strings[(int)(Math.random() * strings.length)];
	}
	
	/**
	 * Funkcja losuje liczbę początkowych żyć.
	 * @param max Ilość liczb w tablicy
	 * @return Zwraca jedną z wartości w tablicy życia
	 */
	private final int losowoindeks(int max) {
		int x;
		try {
			x = zycia[(int)(Math.random() * (max))];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.err.print("Nieprawidlowy indeks");
		}
		return zycia[(int)(Math.random() * (max))];
	}
	
	/**
	 * Funkcja zapisuje dane o ilości żyć i celowniku do pliku
	 * @param nazwaPliku Nazwa pliku do którego mają być wpisane dane
	 */
	void zapiszPlikKonfiguracyjny(String nazwaPliku) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(nazwaPliku)));
		} 
		catch (IOException ioe) {
			System.err.print("Nie udało się otworzyć pliku do zapisu");
		}
		pw.println("celownik=" + losowo(celowniki) + nl + "życia=" + losowoindeks(3) +nl);
		pw.close();
	}
}
