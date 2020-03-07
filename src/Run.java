import java.awt.*;
import java.io.IOException;
/**
 * 
 * Klasa, która uruchamia grę ( losuje też za każdym dane do pliku parametrycznego i konfiguracyjnego)
 *
 */
public class Run {
	public static void main(String[] args) {
		GeneratorPlikuParametrycznegoGry cg = new GeneratorPlikuParametrycznegoGry();
		String nazwaPlikuParametrycznego = "par.txt";
		cg.zapiszPlikParametryczny(nazwaPlikuParametrycznego);
		ProjectProperties prop = new ProjectProperties();
		prop.daneZPliku();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new StartingWindow();
			}
		});
	}
}