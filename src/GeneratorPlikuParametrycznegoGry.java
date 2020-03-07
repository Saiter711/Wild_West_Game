
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Properties;

/**
 * Program tworzy główny plik parametryczny gry. Program ten wraz z klasą {@link PIN}
 * zawiera kilka błędów logicznych i antywzorców, a być może i nie tylko takie błędy.
 * Przejrzyj program i spróbuj wskazać te błędy.
 * 
 * @author kmi
 * @version 1.0.0  6 marca 2019 18:30
 */
public class GeneratorPlikuParametrycznegoGry {
	/**
	 * Pin generowanej gry.
	 */
	private final PIN pin = PIN.PIN;
	
	/**
	 * Pint generowanej gry, jako int.
	 */
	private final int Pint = pin.pin;
	
	/**
	 * "Mały pin" - suma cyfr pinu, obliczana tak długo, aż będzie mniejsza
	 * od 10. (jest tu pewien błąd - jaki?)
	 */
	private int p = Pint;
	
	final private String lsep = System.getProperty("line.separator");
	
	/**
	 * Przygotowuje obiekt do obliczania cech gry.
	 */
	GeneratorPlikuParametrycznegoGry() {
		do {
			p = sumaCyfr(p); 
		} while (p > 9);
	}
	
	final private String nazwyGier[] = {
			"Strzelanka", "Strzelanie do obiektów", "Zestrzel obiekty", "Strzelanie",
			"Gra na PROZE, Strzelanka", "Shooter", "ShootAndGo",
	};
	
	final private String nazwyPlikówPoziomów[] = {
			"Poziom", "poziom", "poziom#", "level", "Level", "PoziomNr", "PoziomGryNumer_",
	};
	
	final private String rozszerzenia[] = {
			"txt", "properties", "prop", "props", "text", "gra",
	};
	
	final private String figuryObiektów[] = {
			"kwadraty", "prostokąty", "trójkąty", "kółka",
	};
	
	private final String losowo(String...strings) {
		return strings[(int)(Math.random() * strings.length)];
	}
	
	private final double losowo(double min, double max) {
		assert min <= max : "losowo(min, max): minimum większe od maksimum?";
		return min + Math.random() * (max - min);
	}
	
	private final int losowo(int min, int max) {
		assert min <= max : "losowo(min, max): minimum większe od maksimum?";
		return min + (int) (Math.random() * (1L + max - min));
	}
	
	private final String wgPinu(String...strings) {
		return strings[Pint % strings.length];
	}
	
	private final int wgPinu(int min, int max) {
		assert min <= max : "wgPinu(min, max): minimum większe od maksimum?";
		return min + Pint % (max - min + 1);
	}
	
	void zapiszPlikParametryczny(String nazwaPliku) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(nazwaPliku)));
		} catch (IOException ioe) {
			pin.wypiszKomunikatyIZakończ("Nie udało się otworzyć pliku parametrycznego " +
					"do zapisu", nazwaPliku, ioe);
		}
		pw.println("# Plik parametryczny gry." + lsep
				+ "# Komentarz zaczyna się od znaku # i obowiązuje do końca linii." + lsep
				+ "# Parametry są zapisane w postaci par {nazwa}={wartość}." + lsep
				+ "# Plik nalezy wczytać do obiektu java.util.Properties metodą load() tej klasy" + lsep
				+ "# wg wzoru w programie generatora plików parametrycznych." + lsep
				+ "#" + lsep
				+ "nazwaGry=" + losowo(nazwyGier) + " [" + pin + "]" + lsep 
				+ "# Nazwa gry powinna wyświetlac się gdzieś na planszy albo w tytule okna." + lsep
				+ "#" + lsep
				+ "# 1. Poziomy gry i pliki opisujące poziom gry. Zawartośc pliku opisującego poziom" +lsep
				+ "#    trzeba opracować i przygotować samodzielnie wg metody stosowanej w tym pliku." + lsep
				+ "#" + lsep 
				+ "liczbaPoziomów=" + (3 + (p % 3)) + lsep
				+ "# dodatnia liczba całkowita" + lsep
				+ "nazwaBazowaPlikuZOpisemPoziomu=" + wgPinu(nazwyPlikówPoziomów) + lsep
				+ "numeracjaPoziomówZaczynaSięOd=" + wgPinu(0, 1) + lsep
				+ "# liczba 0 lub 1" + lsep
				+ "rozszerzeniePlikuZOpisemPoziomu=" + wgPinu(rozszerzenia) + lsep
				+ "#" + lsep
				+ "# 2. Stopnie trudności" + lsep 
				+ "#" + lsep
				+ "liczbaStopniTrudności=" + losowo(2, 5) + lsep
				+ "# dodatnia liczba całkowita" + lsep
				+ "zmianaStopniaTrudności=" + losowo(20, 45) + lsep
				+ "# liczba całkowita w zakresie 20..45 - o tyle procent zwiększają się " 
				+ "i/lub zmniejszają" + lsep + "# się parametry wpływające na stopień trudności"
				+ " np. prędkość, czas, liczba punktów do zdobycia itp." + lsep
				+ "#" + lsep
				+ "# 3. Różne wymiary" + lsep
				+ "#" + lsep
				+ "początkowaSzerokośćPlanszy=" + losowo(800, 1000) + lsep
				+ "początkowaWysokośćPlanszy=" + losowo(600, 700) + lsep
				+ "początkowaSzerokośćObiektuGryJakoProcentPoczątkowejSzerokościPlanszy=" 
				+ (float) losowo(5d, 9d) + lsep
				+ "#" + lsep
				+ "# 4. Rysowanie tła i obiektów, do których się strzela" + lsep
				+ "#");
		if (losowo(0, 1) == 0) { // tło jednolite
			pw.println("tło=jednolite" + lsep
					+ "kolorTła=" + losowo(0, 255) + " " + losowo(0, 255) + " " + losowo(0, 255) + lsep
					+ "# składowe R G B koloru tła, każda w zakresie 0..255");
		} else { // tło graficzne
			pw.println("tło=plikGraficzny" + lsep
					+ "plikTła=resources\\\\background1.jpeg" + lsep
					+ "# w ogólności tu można wstawić (względną!) ścieżkę do własnego pliku tła");
		}
		if (losowo(0, 1) == 0) { // obiekty rysowane jako kształty
			pw.println("obiektyGry=figuryGeometryczne" + lsep
					+ "figuraObiektuGry=" + losowo(figuryObiektów) + lsep
					+ "# jedno z: kwadraty prostokąty trójkąty kółka");
		} else { // obiekty rysowane jako pliki graficzne
			pw.println("obiektyGry=plikGraficzny" + lsep
					+ "plikObiektu=butelka.png" + lsep
					+ "# w ogólności tu można wstawić (względną!) ścieżkę do własnego pliku z obiektem graficznym");
		}
		pw.println("#" + lsep
				+ "# Jeśli uważasz, że warto dodać jeszcze jakieś parametry dodaj je w dodatkowym" + lsep
				+ "# pliku konfiguracyjnym, obsługiwanym niezależnie od tego pliku parametrycznego."
				);
		pw.close();
	}
	
	void wczytajIWypiszPlikParametryczny(String nazwaPliku) {
		Properties props = new Properties();
		try (Reader r = new BufferedReader(new FileReader("par.txt"))) {
			props.load(r);
		} catch (FileNotFoundException fnfe) {
			pin.wypiszKomunikatyIZakończ("Nie znaleziono pliku parametrycznego",
					nazwaPliku, fnfe); 
		} catch (IOException ioe) {
			pin.wypiszKomunikatyIZakończ("Wystąpil błąd odczytu pliku parametrycznego",
					nazwaPliku, ioe);
		}
		props.forEach( (nazwaParametru, wartośćParametru) -> {
			System.out.println("[" + nazwaParametru + "]=[" + wartośćParametru + "]");
		});
	}

	
	/**
	 * Oblicza sumę cyfr podanej liczby. Czy jest efektywniejsza realizacja (bez pętli)?
	 * @param a
	 * @return 
	 */
	private int sumaCyfr(int a) {
		String s = "" + a;
		int sumaCyfr = 0;
		for (int i = 0; i < s.length(); i++) {
			sumaCyfr += (s.charAt(i)) - '0';
		}
		return sumaCyfr;
	}
	
	/**
	 * Tworzy plik parametryczny (.\par.txt) gry na podstawie pinu zawartego
	 * w pliku .\pin.txt.
	 * @param args nieużywane.
	 */
	/*public static void main(String... args) {
		System.out.println(PIN.PIN);
		GeneratorPlikuParametrycznegoGry cg = new GeneratorPlikuParametrycznegoGry();
		System.out.println("p: " + cg.p);
		String nazwaPlikuParametrycznego = "par.txt";
		cg.zapiszPlikParametryczny(nazwaPlikuParametrycznego);
		cg.wczytajIWypiszPlikParametryczny(nazwaPlikuParametrycznego);
	}*/
}
