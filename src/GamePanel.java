import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.Timer;

/**
 * 
 * Klasa dziedzicząca po JPanel wykorzystywana do wyrysowania tła graficznego oraz prostokątów na nim.
 */

public class GamePanel extends JPanel {
	/**
	 * Przechowuje szerokość początkową planszy
	 */
	public int x = ProjectProperties.poczatkowaSzerokoscPlanszy;
	/**
	 * Zmienna przechowująca wysokość początkową planszy
	 */
	public int y = ProjectProperties.poczatkowaWysokoscPlanszy;
	/**
	 * Zmienna przechowująca wartość aktualnego poziomu
	 */
	public static int level = 1;
	/**
	 * Zmienna przechowująca wartość pakietów dodatkowych (na początku jest 0)
	 */
	static int ilosc_pakietow = 0;
	/**
	 * Zmienna przechowująca wartość liczby losowej generowanej w funkcji
	 */
	private int losowaLiczba;
	/**
	 * Lista figur na planszy
	 */
	public static ArrayList<Shape> figures = new ArrayList<Shape>();
	/**
	 * Lista figur pakietów dodatkowych
	 */
	public static ArrayList<ShootingPacket> packets = new ArrayList<ShootingPacket>();
	/**
	 * Zmienne potrzebne do ustalenia pozycji i poruszania się obiektów
	 * randx,randy - odpowiedzialne za położenie losowe obiektu
	 * x1,y1 - odpowiedzialne za położenie obiektu względem początkowego położenia
	 * VelX,VelY - "szybkość" obiektu, o ile pikseli ma się poruszać
	 * tabx,taby - odpowiedzialne za uwzględnienie aktualnej szerokości i wysokości okna (skalowanie) - zawiera odpowiednio randx i randy
	 */
	private int[] randx, randy, x1, y1, VelX, VelY, tabx, taby;
	/**
	 * Liczba losowa
	 */
	private Random rand = new Random();
	/**
	 * Zmienna Image przechowująca wartość bieżącego tła 
	 */
	public Image img;
	/**
	 * Zmienna decydująca o tym, czy obiekty się poruszają i przemalowują (głównie)
	 */
	public static boolean running = false;
    /**
     * Przechowuje informację, o tym czy gra jest zapauzowana
     */
    public static boolean paused = false;
    /**
     *  Wątki gry - wykorzystywane do animacji w grze
     */
    public Thread thread, thread1, thread2, thread3, thread4;
    /**
     * Zmienna przechowuje wartość zestrzelonych obiektów
     */
    public static int hit = 0;
    /**
     * Zmienna potrzebna do ustalenia, czy gra się zakończyła
     */
    public static int end = 0;
    /**
     * Zmienna przechowująca ilość pozostałych strzałów
     */
    public static int shots;
    /**
     * Instancja klasy, która pozwala na synchroniczne wykonywanie wątków
     */
    public ResourceLock lock;
    /**
     * Instancja klasy czytająca wartość pliku .properties
     */
    public Configuration config;
    /**
     * Instancja klasy odpowiedzialnej za odczyt i zapis do pliku z wynikami
     */
    public ResultList reslist;
    /**
     * Konstruktor klasy BackImage
     */
	public GamePanel() {
		setOpaque(true);
		level = 1;
		removeFigures();
		config = new Configuration();
		config.getPropValues();
		if(ProjectProperties.tlo.equals("jednolite")) {			  
			  String [] kolory = ProjectProperties.kolorTla.split(" ");
			  Color color = new Color(Integer.parseInt(kolory[0]),Integer.parseInt(kolory[1]),Integer.parseInt(kolory[2]));	
			  setBackground(color);
		} 
		else {
			img = Toolkit.getDefaultToolkit().createImage(ProjectProperties.plikTla);
		}
		setMaximumSize(new Dimension((int)(2000*0.8), y));
		for(int i = 0; i < config.liczba_obiektow; i++) {
	        figures.add(new Shape());
	    }
		for(int i = 0; i < ilosc_pakietow; i++) {
	        packets.add(new ShootingPacket());
	    }
		tabx = new int[figures.size()+ilosc_pakietow];
	    taby = new int[figures.size()+ilosc_pakietow];
		randomLocation();
		shots = config.liczba_nabojow;
		reslist = new ResultList();
		thread = new Thread();
		thread1 = new Thread();
		thread2 = new Thread();
		thread3 = new Thread();
		thread4 = new Thread();
	}
	
	/**
	 * Funkcja zwracająca wysokość bieżącą okna
	 */
	public int getHeight() {
		return getSize().height;
	}
	
	/**
	 * Funkcja zwracająca bieżącą szerokość okna
	 */
	public int getWidth() {
		return getSize().width;
	}
	
	/**
	 * Funkcja losująca położenie i szybkość poruszania się obiektów.
	 * Zawarte są w niej poziomy trudności (w zależności od stopnia trudności, obiekty poruszają się szybciej lub wolniej
	 */
    public void randomLocation() { 
        randx = new int[figures.size()+ilosc_pakietow];
        randy = new int[figures.size()+ilosc_pakietow];
        VelX = new int[figures.size()+ilosc_pakietow];
        VelY = new int[figures.size()+ilosc_pakietow];
        x1 = new int[figures.size()+ilosc_pakietow];
        y1 = new int[figures.size()+ilosc_pakietow];
        for(int i = 0; i < figures.size(); i++) {
            randx[i] = rand.nextInt(x-figures.get(i).getwidth()-150);
            randy[i] = rand.nextInt(y-figures.get(i).getheight()-80);
            while(VelX[i] == 0 || VelY[i] == 0) {
            	if(StartingWindow.poziomtrudnosci == "Łatwy") {
            		losowa(config.min_predkosc,config.max_predkosc);
            	}
            	else if(StartingWindow.poziomtrudnosci == "Sredni") {
            		losowa((int)(config.min_predkosc * (ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));
            	}
            	else if(StartingWindow.poziomtrudnosci == "Trudny") {
            		losowa((int)(config.min_predkosc * (2*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (2*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));            		
            	}
            	else if(StartingWindow.poziomtrudnosci == "Zaawansowany") {
            		losowa((int)(config.min_predkosc * (3*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (3*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));
            	}            	
            	else if(StartingWindow.poziomtrudnosci == "Pro") {
            		losowa((int)(config.min_predkosc * (4*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (4*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));	
            	}            	
            	if(level == 1) {
            		VelX[i]=losowaLiczba;
            		VelY[i]=losowaLiczba;	
	            }
	            else if(level == 2) {
	                VelX[i]=losowaLiczba + config.roznica_predkosci;
	                VelY[i]=losowaLiczba+ config.roznica_predkosci;
	            }
	            else if(level == 3) {
	                VelX[i]=losowaLiczba + 2*config.roznica_predkosci;
	                VelY[i]=losowaLiczba+ 2*config.roznica_predkosci;
	            }
	            else if(level == 4) {
	                VelX[i]=losowaLiczba + 3*config.roznica_predkosci;
	                VelY[i]=losowaLiczba+ 3*config.roznica_predkosci;
	            }
            	x1[i]=0;
            	y1[i]=0;
            }
        }
        
        for(int i = config.liczba_obiektow; i < config.liczba_obiektow + packets.size() ; i++){
            randx[i] = rand.nextInt(x-packets.get(i-config.liczba_obiektow).getwidth()-150);
            randy[i] = rand.nextInt(y-packets.get(i-config.liczba_obiektow).getheight()-80);
        
            while(VelX[i] == 0 || VelY[i] == 0) {
            	if(StartingWindow.poziomtrudnosci=="Łatwy") {
            		losowa(config.min_predkosc,config.max_predkosc);
            	}
            	else if(StartingWindow.poziomtrudnosci=="Sredni") {
            		losowa((int)(config.min_predkosc * (ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));
            	}
            	else if(StartingWindow.poziomtrudnosci=="Trudny") {
            		losowa((int)(config.min_predkosc * (2*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (2*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));            		
            	}
            	else if(StartingWindow.poziomtrudnosci=="Zaawansowany") {
            		losowa((int)(config.min_predkosc * (3*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (3*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));
            	}            	
            	else if(StartingWindow.poziomtrudnosci=="Pro") {
            		losowa((int)(config.min_predkosc * (4*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)),
            				(int)(config.max_predkosc * (4*ProjectProperties.zmianaStopniaTrudnosci/100 + 1)));	
            	} 
            	if(level == 1) {
        			VelX[i]=losowaLiczba;
        			VelY[i]=losowaLiczba;
        		}
        		else if(level == 2) {
        			VelX[i]=losowaLiczba + config.roznica_predkosci;
        			VelY[i]=losowaLiczba+ config.roznica_predkosci;
                }
        		else if(level == 3) {
        			VelX[i]=losowaLiczba + 2*config.roznica_predkosci;
        			VelY[i]=losowaLiczba+ 2*config.roznica_predkosci;
                }
        		else if(level == 4) {
        			VelX[i]=losowaLiczba + 3*config.roznica_predkosci;
        			VelY[i]=losowaLiczba+ 3*config.roznica_predkosci;
                }
            	x1[i]=0;
            	y1[i]=0;
            }
        }
    }
    
    /**
     * Funkcja odpowiedzialna za narysowanie obiektów na planszy
     * @param g Odpowiada za właściwe wyjście (ekran)
     */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getSize().width;
		int height = getSize().height;
		for(int i = 0; i < figures.size(); i++) {
	        tabx[i] = width - x + randx[i]+133;
	        taby[i] = height - y + randy[i]-194+256;
		}
		for(int i = config.liczba_obiektow; i <config.liczba_obiektow + packets.size() ; i++) {
			tabx[i] = width - x + randx[i]+133;
			taby[i] = height - y + randy[i]-194+256;
		}
		if(ProjectProperties.tlo.equals("plikGraficzny")) {
			g.drawImage(img, 0, 0, width, height, this);	
		}
		if(!figures.isEmpty()) {
            for (int i = 0; i < figures.size(); i++) {
                if(!figures.get(i).isClicked()) {
                	if(level ==1) {
                		figures.get(i).setSizze((int)(width*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100), 
                				(int)(height*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100));	
                	}
                	else if(level == 2) {
                		figures.get(i).setSizze((int)(width*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100-5), 
                				(int)(height*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100-5));	
                	}
                	else if(level == 3) {
                		figures.get(i).setSizze((int)(width*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100-10), 
                				(int)(height*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100-10));	
                	}
                	else if(level == 4) {
                		figures.get(i).setSizze((int)(width*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100-15), 
                				(int)(height*ProjectProperties.poczatkowaSzerokoscObiektuGryJPSP/100-15));	
                	}
                    if(ProjectProperties.figuraObiektuGry.equals("kwadraty")) {
                    	figures.get(i).setBounds(tabx[i] + x1[i], taby[i] + y1[i], figures.get(i).getwidth(), figures.get(i).getwidth());
                    }
                    else {
                    	figures.get(i).setBounds(tabx[i] + x1[i], taby[i] + y1[i], figures.get(i).getwidth(), figures.get(i).getheight());
                    }
                	figures.get(i).setBackground(new Color(0, 0, 0, 0));
                    figures.get(i).setOpaque(false);
                    add(figures.get(i));
                }
            }          
		}
		if(!packets.isEmpty()) {
            for (int i = config.liczba_obiektow; i <config.liczba_obiektow + packets.size() ; i++) {
                if(!packets.get(i-config.liczba_obiektow).isClicked()) {
                	packets.get(i-config.liczba_obiektow).setSizze((int)(width*0.05), (int)(height*0.05));
                	packets.get(i-config.liczba_obiektow).setBounds(tabx[i] + x1[i], taby[i] + y1[i], 
                			packets.get(i-config.liczba_obiektow).getwidth(), packets.get(i-config.liczba_obiektow).getheight());
                	packets.get(i-config.liczba_obiektow).setBackground(new Color(0, 0, 0, 0));
                	packets.get(i-config.liczba_obiektow).setOpaque(false);
                    add(packets.get(i-config.liczba_obiektow));
                }
            }          
		}
	}
	
	/**
	 * Funkcja odpowiedzialna za poruszanie się obiektów
	 */
	public void move() {
	    for (int i = 0; i < figures.size(); i++) {
	        if(ProjectProperties.figuraObiektuGry.equals("kwadraty")) {
	            if (x1[i] + tabx[i] < 0 || x1[i] + tabx[i] > getSize().width - figures.get(i).getwidth())
	                VelX[i] = -VelX[i];
	            
	            if (y1[i] + taby[i] < 0 || y1[i] + taby[i] > (getSize().height - figures.get(i).getwidth()))
	                VelY[i] = -VelY[i];
	        }
	        else {
	        	if (x1[i] + tabx[i] < 0 || x1[i] + tabx[i] > getSize().width - figures.get(i).getwidth())
	 	            VelX[i] = -VelX[i];
	        	
	 	        if (y1[i] + taby[i] < 0 || y1[i] + taby[i] > (getSize().height - figures.get(i).getheight()))
	 	            VelY[i] = -VelY[i];
	        }
	        x1[i] += VelX[i];
	        y1[i] += VelY[i];
	        remove(figures.get(i));
	    }
	    
	    for (int i = config.liczba_obiektow; i <config.liczba_obiektow + packets.size() ; i++) {
	        if (x1[i] + tabx[i] < 0 || x1[i] + tabx[i] > getSize().width - packets.get(i-config.liczba_obiektow).getwidth()) {
	        	VelX[i] = -VelX[i];
	        }
	        if (y1[i] + taby[i] < 0 || y1[i] + taby[i] > (getSize().height - packets.get(i-config.liczba_obiektow).getheight())) {
	        	VelY[i] = -VelY[i];
	        }
	        x1[i] += VelX[i];
	        y1[i] += VelY[i];
	        remove(packets.get(i-config.liczba_obiektow));
	    }
	}
	
	/**
	 * Funkcja usuwa obiekty z ekranu oraz czyści listy obietków
	 */
	public void removeFigures() {
	    for(int i = 0; i < figures.size(); i++) {
	    	remove(figures.get(i));	
	    }
	    for(int i =0; i<packets.size();i++) {
	    	remove(packets.get(i));
	    }
	    figures.clear();
	    packets.clear();
	}
	
	/**
	 * Funkcja dodaje obiekty do list (w zależności od podanej w pliku .properties ilości)
	 */
    public void addFigures() {
	    for(int i = 0; i < config.liczba_obiektow; i++) {
	        figures.add(new Shape());
	    } 
		for(int i = 0; i < ilosc_pakietow; i++) {
	       packets.add(new ShootingPacket());
	    } 
    }

	/**
	 *Funkcja ustawia określoną wartość zmiennej
	 * @param paused określa czy gra jest zapauzowana
	 */
    public void setPaused(boolean paused) {
    	this.paused = paused;
	}
	/**
	 *Funkcja ustawia określoną wartość zmiennej
	 * @param running określa czy gra jest uruchomiona
	 */
	public  void setRunning(boolean running) {
	    this.running = running;
	}

	/**
	  * Metoda umożliwiająca odświeżanie planszy gry, startuje wątki (poruszanie się obiektów i ich wyświetlanie)
	  */
	public void start(){
		lock = new ResourceLock();
	    if(running) {
		    return;	
	    }
		running=true;
		thread = new Thread(new Runnable() {           
			public void run() { 
				synchronized(lock) {
					while(lock.flag != 1) {
						try {
		            		lock.wait();
						}
	            		catch(Exception e) {
	            			e.printStackTrace();
	            		}
					}
					int fps = 60;
					img = Toolkit.getDefaultToolkit().createImage(config.mapa1);
					double timePerTick = 1000000000/fps;
					double delta = 0;
					long now;
					long lastTime = System.nanoTime();
					while(running) {
						now = System.nanoTime();
						delta+=(now-lastTime)/timePerTick;
						lastTime=now;
						if(delta>=1) {
							if(!paused) {
								move();
							}
							repaint();
							delta--;    
						}
						if(end == 1) {
							lock.flag=5;
							lock.notifyAll();
							running =false;
						}
						if(hit == config.liczba_obiektow) {
							lock.flag = 2;	
						}
						if(lock.flag == 2) {
							level =2;
							lock.notifyAll();
							running = false;
						}
					} 
				} 
			}
		});
		thread1 = new Thread(new Runnable() { 
			public void run() {
				synchronized(lock) {
					while(lock.flag != 2) {
						try {
		            		lock.wait();
		            	}
		            	catch(Exception e) {
		            		e.printStackTrace();
		            	}
					}
	            	if(shots < 70) {
	            		ilosc_pakietow = 1;
	            	}
 	    	        removeFigures();
 			        addFigures();
 			        img = Toolkit.getDefaultToolkit().createImage(config.mapa2);
 			        tabx = new int[figures.size()+ilosc_pakietow];
	    	        taby = new int[figures.size()+ilosc_pakietow];
 			        randomLocation();
 			        running= true;
	            	int fps = 60;
	     	        double timePerTick = 1000000000/fps;
	     	        double delta = 0;
	     	        long now;
	     	        long lastTime = System.nanoTime();
	     	        while(running) {
	     	        	now = System.nanoTime();
	     	            delta+=(now-lastTime)/timePerTick;
	     	            lastTime=now;
	     	            if(delta>=1) {
	     	                if(!paused) {
	     	                    move();
	     	                }
	     	                repaint();
	     	                delta--;   
	     	            }
	     	            if(end == 1) {
	     	        	   lock.flag=5;
	     	        	   lock.notifyAll();
	     	        	   running =false;
	     	            }
	     	            if(hit == config.liczba_obiektow *2) {
	     	            	lock.flag = 3;
	     	            }
	     	            if(lock.flag == 3) {
	     	            	level =3;
	     	            	lock.notifyAll();
	     	            	running = false;
	     	            }
	     	        }   
	            } 
	        }
		});
		thread2 = new Thread(new Runnable() { 
			public void run() {
				synchronized(lock) {
					while(lock.flag != 3){
						try {
		            		lock.wait();
		            		}
		            	catch(Exception e) {
		            		e.printStackTrace();
		            	}
					}
	            	if(shots < 30) {
	            		ilosc_pakietow = 1;
	            	}
 	    	        removeFigures();
 			        addFigures();
 			        img = Toolkit.getDefaultToolkit().createImage(config.mapa3);
 			        tabx = new int[figures.size()+ilosc_pakietow];
	    	        taby = new int[figures.size()+ilosc_pakietow];
 			        randomLocation();
 			        running= true;
 			        int fps = 60;
	     	        double timePerTick = 1000000000/fps;
	     	        double delta = 0;
	     	        long now;
	     	        long lastTime = System.nanoTime();
	     	        while(running) {
	     	            now = System.nanoTime();
	     	            delta+=(now-lastTime)/timePerTick;
	     	            lastTime=now;
	     	            if(delta>=1) {
	     	                if(!paused) {
	     	                    move();
	     	                }
	     	                repaint();
	     	                delta--;   
	     	            } 
	     	            if(end == 1) {
	     	            	lock.flag=5;
	     	            	lock.notifyAll();
	     	            	running =false;		     	    		 
	     	            }
	     	            if(hit == config.liczba_obiektow *3) {
	     	            	lock.flag = 4;
	     	            }
	     	            if(lock.flag == 4) {
	     	            	level =4;
	     	            	lock.notifyAll();
	     	            	running = false;
	     	            }
	     	      	}  
	          	} 
			}
		});
		thread3 = new Thread(new Runnable() { 
			public void run() {
				synchronized(lock) {
					while(lock.flag != 4) {
						try {
		            		lock.wait();
		            	}
		            	catch(Exception e) {
		            		e.printStackTrace();
		            	}	
					}
	            	if(shots < 10) {
	            		ilosc_pakietow = 1;
	            	}
 	    	        removeFigures();
 			        addFigures();
 			        img = Toolkit.getDefaultToolkit().createImage(config.mapa4);
 			        tabx = new int[figures.size()+ilosc_pakietow];
	    	        taby = new int[figures.size()+ilosc_pakietow];
 			        randomLocation();
 			        running= true;
 			        int fps = 60;
	     	        double timePerTick = 1000000000/fps;
	     	        double delta = 0;
	     	        long now;
	     	        long lastTime = System.nanoTime();
	     	        
	     	        while(running) {
	     	        	now = System.nanoTime();
	     	            delta+=(now-lastTime)/timePerTick;
	     	            lastTime=now;
	     	            if(delta>=1) {
	     	            	if(!paused) {
	     	                    move();
	     	                }
	     	                repaint();
	     	                delta--;   
	     	            }
	     	            if(end == 1)
	     	            {
	     	            	lock.flag=5;
	     	            	lock.notifyAll();
	     	            	running =false;
	     	            }
	     	            if(hit == config.liczba_obiektow *4) {
	     	            	JOptionPane.showMessageDialog(null, "KONIEC GRY! \n Brawo " + StartingWindow.nazwa  + ", twój wynik to: " + hit);
	     	            	reslist.draw();
	     	        		reslist.add(new ListPosition(StartingWindow.nazwa, Integer.toString(hit)));
	     	        		reslist.sorting();
	     	        		reslist.save();
	     	        		System.exit(0);
	     	            }
	     	        }  
				} 
			}
		});
		thread4 = new Thread(new Runnable() { 
			public void run() {
				synchronized(lock) {
					while(lock.flag != 5) {
						try {
		            		lock.wait();
						}
		            	catch(Exception e) {
		            		e.printStackTrace();
		            	}	
					}
	            	JOptionPane.showMessageDialog(null, "KONIEC GRY! \n Brawo " + StartingWindow.nazwa  + ", twój wynik to: " + GamePanel.hit);
	       		 	reslist.draw();
	       		 	reslist.add(new ListPosition(StartingWindow.nazwa, Integer.toString(GamePanel.hit)));
	       		 	reslist.sorting();
	       		 	reslist.save();
	       		 	System.exit(0);  
				} 
			}
		});
		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}
	
	/**
	 * Metoda stopująca wszyskie wątki
	 */
	public void stop() {
		running = false;
		try {
			thread.join();
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Funkcja losuje liczbę z przedziału
	 * @param min Określa minimum przedziału
	 * @param max Określa maksimum przedziału
	 */
	public void losowa(int min, int max) {
		losowaLiczba= min+(int)(Math.random()*max);
	}
}
