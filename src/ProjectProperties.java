import javax.swing.*;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * 
 * Klasa przechowuje wszystkie wartości potrzebne do działania programu odczytane z pliku parametrycznego i konfiguracyjnego
 * Każdej nazwie zmiennej odpowiada nazwa parametru w plikach.
 */
public class ProjectProperties {
	/**
	 * Parametr opisujący nazwę gry
	 */
	public static String nazwaGry;
	/**
	 * Parametr opisujący ilość poziomów
	 */
	public static int liczbaPoziomow;
	/**
	 * Parametr opisujący nazwę bazową pliku z opisem poziomu
	 */
	public static String nazwaBazowaPlikuZOpisemPoziomu;
	/**
	 * Parametr opisujący od jakiej cyfry zaczyna się liczba poziomów ( 0 lub 1)
	 */
	public static int numeracjaPoziomowZaczynaSieOd;
	/**
	 * Parametr opisujący jakie rozszerzenie powinien mieć plik z opisem poziomu
	 */
	public static String rozszerzeniePlikuZOpisemPoziomu;
	/**
	 * Parametr opisuje ilość stopni trundości
	 */
	public static int liczbaStopniTrudnosci;
	/**
	 * Parametr opisujący o ile procent ma się zmieniać stopień trudności przy jego wzroście
	 */
	public static int zmianaStopniaTrudnosci;
	/**
	 * Parametr opisujący początkową szerokość planszy
	 */
	public static int poczatkowaSzerokoscPlanszy;
	/**
	 * Parametr opisujący początkową wysokość planszy
	 */
	public static int poczatkowaWysokoscPlanszy;
	/**
	 * Parametr opisujący wielkość obiektu do zestrzelenia względem początkowej szerokości planszy
	 */
	public static double poczatkowaSzerokoscObiektuGryJPSP;
	/**
	 * Parametr opisujący jakie tło ma wypełniać planszę (jednolite lub plik graficzny)
	 */
	public static String tlo;
	/**
	 * Parametr opisujący nazwę plikt tła
	 */
	public static String plikTla;
	/**
	 * Parametr, w którym określone jest, czym będą obiekty gry (plikiem graficznym albo kształtem)
	 */
	public static String obiektyGry;
	/**
	 * Pole, które zawiera nazwę pliku obiektu gry
	 */
	public static String plikObiektu;
	/**
	 * Pole opisujące kształt obiektu (kwadrat, prostokąt, trójkąt lub kółko)
	 */
	public static String figuraObiektuGry = "";
	/**
	 * Pole, opisujące jaki kolor ma wyświetlać tło (RGB)
	 */
	public static String kolorTla;
	/**
	 * Pole przechowujące nazwę celownika
	 */
	public static String celownik;
	/**
	 * Pole przechowujące liczbę żyć gracza
	 */
	public static int liczbaZyc;
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do nazwy gry zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultnazwaGry = "Wild West";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do ilości poziomów zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultliczbaPoziomow = "3";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do nazwy bazowej pliku z opisem poziomu zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultnazwaBazowaPlikuZOpisemPoziomu = "Level";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do numeracji od której zaczyna się poziom zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultnumeracjaPoziomowZaczynaSieOd = "1";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do rozszerzeniaPlikuZOpisemPoziomu zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultrozszerzeniePlikuZOpisemPoziomu = "properties";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do licznbyStopniTrudności zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultliczbaStopniTrudnosci = "3";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do zmianyStopniaTrudności zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultzmianaStopniaTrudnosci = "25";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do początkowejSzerokościPlanszy zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultpoczatkowaSzerokoscPlanszy = "800";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do początkowejWysokościPlanszy zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultpoczatkowaWysokoscPlanszy = "700";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do początkowejSzerokościObiektuGryJPSP zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultpoczatkowaSzerokoscObiektuGryJPSP = "6.125";
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do tla zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaulttlo = null;
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do plikuTła zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultplikTla = null;
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do obiektówGry zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultobiektyGry = null;
	/**
	 * Zmienna pomocniczna, w razie błędu odczytu do plikuObiektu zostałaby wpisana wartość tej zmiennej
	 */
	public static String defaultplikObiektu = null;
	/**
	 * Instancja klasy Properties do odczytywania danych z pliku
	 */
	private Properties prp = null;
	
	/**
	 * Funkcja wczytuje plik i ładuje jego dane do obiektu klasy Properties
	 * @param nazwa Nazwa pliku w którym znajdują się dane.
	 */
	private void wczytajPlik(String nazwa) {
		if(prp == null) {
			try (Reader fr = new BufferedReader(new FileReader("./"+nazwa+".txt"))) {
				(prp = new Properties()).load(fr);
			}
			catch (Throwable e){
				System.err.print("Nie udało się wczytać pliku");
			}
		}		
	}
	
	/**
	 * Funkcja wpisuje do odpowiednich zmiennych odpowiednie wartości z pliku.
	 */
	public void daneZPliku() {
		wczytajPlik("par");
		String wartosc = null;
		nazwaGry = prp.getProperty("nazwaGry", defaultnazwaGry).trim();
		try {
			wartosc = prp.getProperty("liczbaPoziomów", defaultliczbaPoziomow).trim();
			liczbaPoziomow = Integer.parseInt(wartosc);
			wartosc = prp.getProperty("numeracjaPoziomówZaczynaSięOd", defaultnumeracjaPoziomowZaczynaSieOd).trim();
			numeracjaPoziomowZaczynaSieOd = Integer.parseInt(wartosc);
			wartosc = prp.getProperty("liczbaStopniTrudności", defaultliczbaStopniTrudnosci).trim();
			liczbaStopniTrudnosci = Integer.parseInt(wartosc);
			wartosc = prp.getProperty("zmianaStopniaTrudności", defaultzmianaStopniaTrudnosci).trim();
			zmianaStopniaTrudnosci = Integer.parseInt(wartosc);
			wartosc = prp.getProperty("początkowaSzerokośćPlanszy", defaultpoczatkowaSzerokoscPlanszy).trim();
			poczatkowaSzerokoscPlanszy = Integer.parseInt(wartosc);
			wartosc = prp.getProperty("początkowaWysokośćPlanszy", defaultpoczatkowaWysokoscPlanszy).trim();
			poczatkowaWysokoscPlanszy = Integer.parseInt(wartosc);
			wartosc = prp.getProperty("początkowaSzerokośćObiektuGryJPSP", defaultpoczatkowaSzerokoscObiektuGryJPSP).trim();
			poczatkowaSzerokoscObiektuGryJPSP = Double.parseDouble(wartosc);			
		}
		catch (Throwable t) {
			System.err.print("Błąd parsowania liczby");
		}
		nazwaBazowaPlikuZOpisemPoziomu = prp.getProperty("nazwaBazowaPlikuZOpisemPoziomu", defaultnazwaBazowaPlikuZOpisemPoziomu).trim();
		rozszerzeniePlikuZOpisemPoziomu = prp.getProperty("rozszerzeniePlikuZOpisemPoziomu", defaultrozszerzeniePlikuZOpisemPoziomu).trim();
		tlo = prp.getProperty("tło", defaulttlo).trim();
		if(tlo.contentEquals("plikGraficzny")) {
			plikTla = prp.getProperty("plikTła", defaultplikTla).trim();
		}
		else {
			kolorTla = prp.getProperty("kolorTła").trim();
		}
		obiektyGry = prp.getProperty("obiektyGry", defaultobiektyGry).trim();
		if(obiektyGry.equals("figuryGeometryczne")) {
			figuraObiektuGry = prp.getProperty("figuraObiektuGry").trim();
		}
		else {
			plikObiektu = prp.getProperty("plikObiektu", defaultplikObiektu).trim();
		}
		prp = null;
		wczytajPlik("konf");
		try {
			wartosc = prp.getProperty("życia").trim();
			liczbaZyc = Integer.parseInt(wartosc);
		}
		catch (Throwable b){
			System.err.print("Błąd parsowania pliku");
		}
		celownik = prp.getProperty("celownik").trim();
	}
	/*public static void main(String... args) {
		PlikKonfiguracyjny p = new PlikKonfiguracyjny();
		p.zapiszPlikKonfiguracyjny("konf.txt");
		GeneratorPlikuParametrycznegoGry cg = new GeneratorPlikuParametrycznegoGry();
		String nazwaPlikuParametrycznego = "par.txt";
		cg.zapiszPlikParametryczny(nazwaPlikuParametrycznego);
		Properties1 xd = new Properties1();
		xd.daneZPliku();
		System.out.println(tlo+" " + plikObiektu+" " + obiektyGry + " " + celownik);
	}*/
}