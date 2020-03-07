
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 * Klasa odpowiadająca za listę wyników
 * @author płcz
 *
 */
public class ResultList {
    /**
	 * Lista rekordów typu ListPosition
	 */
	public static ArrayList<ListPosition> list = new ArrayList<ListPosition>();
	
	/**
	 * Konstruktor klasy ResultList() - nie robi żadnej operacji
	 */
	public ResultList(){}
	
	/**
	 * Funkcja pozwalająca na dodanie rekordu
	 * @param lp parametr typu ListPosition dodawany do list
	 */
	public void add(ListPosition lp) {
		list.add(lp);
	}
	
	/**
	 * Funkcja realizująca sortowanie listy
	 */
	public void sorting() {
		Collections.sort(list, new Comparator<ListPosition>() {
			@Override
			public int compare(ListPosition l1, ListPosition l2) {
				return Integer.compare(l2.hit,l1.hit);
			}
		});
	}
	
	/**
	 * Funkcja realizująca wpisywanie zawartości listy do pliku
	 */
	public void save() {	 
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("wyniki.txt")));
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		for(int i=0;i<list.size();i++) {
			pw.println(list.get(i).nazwa+" "+list.get(i).trafienie);
		}
		pw.close();
	}
	
	/**
	 * Funkcja realizująca odczyt z pliku z wynikami 
	 */
	public void draw() {
		Properties props = new Properties();
		try (Reader r = new BufferedReader(new FileReader("wyniki.txt"))) {
			props.load(r);
		} 
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		props.forEach( (nazwaParametru, wartośćParametru) -> {
			list.add(new ListPosition((String)nazwaParametru, (String)wartośćParametru));
		});
	}
}
