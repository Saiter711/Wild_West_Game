import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
 * Klasa odpowiedzialna za wczytanie wartości zmiennych z pliku "poziom#1"
 *
 */
public class Configuration {
	/**
	 * Zmienna przechowująca liczbe naboji 
	 */
	public int liczba_nabojow;
	/**
	 * Zmienna przechowująca ilość obiektów
	 */
	public int liczba_obiektow;
	/**
	 * Zmienna przechowująca maksymalną wartość poruszania się obiektów
	 */
	public int max_predkosc;
	/**
	 * Zmienna przechowująca minimalną wartość poruszania się obiektów
	 */
	public int min_predkosc; 
	/**
	 * Zmienna przechowująca wartość różnicy prędkości poruszania się obiektów
	 */
	public int roznica_predkosci; 
	/**
	 * Zmienna określająca jaki plik graficzny wystąpi na 1 poziomie
	 */
	public String mapa1 = "";
	/**
	 * Zmienna określająca jaki plik graficzny wystąpi na 2 poziomie
	 */
	public String mapa2 = "";
	/**
	 * Zmienna określająca jaki plik graficzny wystąpi na 3 poziomie
	 */
	public String mapa3 = "";
	/**
	 * Zmienna określająca jaki plik graficzny wystąpi na 4 poziomie
	 */
	public String mapa4 = "";
	/**
	 * Zmienna określająca strumien wejściowy
	 */
	public InputStream inputStream;
	/**
	 * Funkcja odpowiedzialna za wczytanie wartości zmiennych
	 */
	public void getPropValues() {
		try {
			Properties prop = new Properties();
			String propFileName = "poziom#1.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} 
			else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			liczba_nabojow = Integer.parseInt(prop.getProperty("liczba_nabojow").trim());
			liczba_obiektow = Integer.parseInt(prop.getProperty("liczba_obiektow").trim());
			min_predkosc = Integer.parseInt(prop.getProperty("min_predkosc").trim());
			max_predkosc = Integer.parseInt(prop.getProperty("max_predkosc").trim());
			roznica_predkosci = Integer.parseInt(prop.getProperty("roznica_predkosci").trim());
			mapa1 = prop.getProperty("mapa_lvl1").trim();
			System.out.println("EEELO" + mapa1);
			mapa2 = prop.getProperty("mapa_lvl2").trim();
			System.out.println("EEELO" + mapa2);
			mapa3 = prop.getProperty("mapa_lvl3").trim();
			mapa4 = prop.getProperty("mapa_lvl4").trim();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				inputStream.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

