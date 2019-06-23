package adreli_1_Con;

/**
 * Adreli
 * 
 * @version ADRELI_1_CON 1.0
 * 
 * @author Jan-Hendrik Hausner
 * @author John Budnik
 * @author Luca Weinmann
 * @author Daniel Springmann
 * 02.04.2019
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Adreli {
	/** {@link #personZaehler} zählt die Anzahl der Personen im Hauptspeicher */
	private static int personZaehler=0;
	/** {@link #cachePersonZaehler} zählt die Anzahl der Personen im Speicher
	 * für die Nummerierung {@link #nummerierenInDatei()} */
	private static int cachePersonZaehler = 0;
	/** {@link #pfad} Dateipfad + Dateiendung*/
	private static Path pfad = Paths.get("adreli.csv");
	/** {@link #ANZEIGEOPTIONEN} Ausgabe der Anzeigeoptionen*/
	private static final String ANZEIGEOPTIONEN [] = {
			new String("     Name: "), //0
			new String("  Vorname: "), //1
			new String("   Anrede: "), //2
			new String("   Straße: "), //3
			new String("      PLZ: "), //4
			new String("      Ort: "), //5
			new String("  Telefon: "), //6
			new String("      Fax: "), //7
			new String("Bemerkung: "), //8
	};
	
	/** {@link #einzelnePerson} Zwischenspeicher bei {@link #personAufnehmen()}
	 */
	private static String einzelnePerson[] = new String[9];
	/** {@link #personenListe} Hauptspeicher*/
	private static String personenListe [][] = new String[30][9];
	/** {@link #cachePersonenListe} zweiter Speicher
	 *	Einsatzzweck für {@link #nummerierenInDatei()}  
	 */
	private static String cachePersonenListe[][] = new String[30][9];
	
	/**
	 * main
	 * @param args
	 * void
	 * @see ch
	 * 
	 * Startpunkt des Programms.
	 * Mithilfe der Methode {@link #pruefeDatei()} wird die Existenz, Lesbarkeit
	 * und die Möglichkeit, die Datei zu bearbeiten, überprüft. 
	 * Falls dies nicht zutrifft, wird mit {@link Files#createFile()} 
	 * und dem Pfad {@link #pfad} eine neue Datei erstellt. 
	 * Zuletzt wird immer {@link #menu()} aufgerufen.
	 */
	public static void main(String[]args) {
		if (Adreli.pruefeDatei()) {
			Adreli.menu();
		}
		else {
			try {
				Files.createFile(pfad);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			Adreli.menu();
		}
	}
	
	
	/**
	 * Hilfsfunktion für {@link #menu()}
	 * pruefeDatei
	 * @return boolean
	 * 
	 * {@link #pruefeDatei()} wird 
	 * @return true, wenn für den Pfad {@link #pfad} die Datei existiert,
	 * die Datei regulär ist (Keine Links im Pfad) , die Datei lesbar und
	 * bearbeitbar.
	 * Andernfalls
	 * @return false.
	 */
	public static boolean pruefeDatei() {
		if (Files.exists(pfad, LinkOption.NOFOLLOW_LINKS) 
				&& Files.isRegularFile(pfad, LinkOption.NOFOLLOW_LINKS)
				&& Files.isReadable(pfad) && Files.isWritable(pfad)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Hauptmenü
	 * menu
	 * void
	 * 
	 * Zuerst wird das Menü angezeigt.
	 * Anschließend kann der Benutzer die gewünschte Zahl eingeben um die dafür 
	 * vorgesehene Methode aufzurufen:
	 * Verwaltungsfunktion 1 {@link #personAufnehmen()}
	 * Verwaltungsfunktion 2 {@link #personenAuflisten()}
	 * Verwaltungsfunktion 3 {@link #datenSichernDatei(boolean)}
	 * Verwaltungsfunktion 4 {@link #datenLadenDatei(boolean)}
	 * Verwaltungsfunktion 5 
	 * {@link #datenSortierenQuickSort(String[][], int, int)}
	 * Verwaltungsfunktion 6 {@link #nummerierenInDatei()}
	 * Verwaltungsfunktion 7 {@link #programmBeenden()}
	 */
	public static void menu() {
		boolean immer = true;
		while (immer) {
		   System.out.println(" ____________________________________________");
		   System.out.println("|                                            |");
		   System.out.println("|	   ADRELI - Adressverwaltung         |");
		   System.out.println("|--------------------------------------------|");
	       System.out.println("|       Wollen Sie...                        |");
		   System.out.println("|                                            |");
		   System.out.println("|             Eine neue Person aufnehmen > 1 |");
		   System.out.println("|                      Records auflisten > 2 |");
		   System.out.println("|          Records in eine Datei sichern > 3 |");
		   System.out.println("|          Records aus einer Datei laden > 4 |");
		   System.out.println("|            in-memory Records sortieren > 5 |");
		   System.out.println("|               Datei Zeilen nummerieren > 6 |");
		   System.out.println("|                 Das Programm verlassen > 7 |");
		   System.out.println("|____________________________________________|");
		   System.out.println();
		   	   boolean wahr = false;
		   	   do {
		   		   try {
		   			   Scanner sc = new Scanner(System.in);
		   			   int auswahl = sc.nextInt();
			   			switch (auswahl) {
						case 1 : Adreli.personAufnehmen();
							break;
						case 2 : Adreli.personenAuflisten();
								System.out.println(Adreli.personZaehler);
							break;
						case 3 : Adreli.datenSichernDatei(true);
							break;
						case 4 : Adreli.datenLadenDatei(true);
							break;
						case 5 : Adreli.datenSortierenQuickSort
								 (0, Adreli.personZaehler-1);
								//Start.sortMemoryDataBubbleSort();
							break;
						case 6 : Adreli.nummerierenInDatei();
							break;
						case 7 : Adreli.programmBeenden();
							break;
						default : 
							System.out.println
							("Sie haben keine Zahl von 1-7 eingegeben!");
							Adreli.menu();
			   			}
		   		   }
		   		   catch (InputMismatchException ime) {
		   			   System.out.println("Keine Zahl eingegeben");
		   			   wahr =true;
		   		   }
		   	   }
		   	   while (wahr);
		}
	}

	
	/**
	 * Verwaltungsfunktion 1
	 * personAufnehmen
	 * void
	 * 
	 * Mit der for-Schleife werden Stückweise die Anzeigeoptionen
	 * {@link #ANZEIGEOPTIONEN} (z.B. Name:) ausgegeben.
	 * Die Eingaben werden anschließend in 
	 * {@link #einzelnePerson} zwischengespeichert.
	 * 
	 * 	Für die einzelnen Eingaben bei jeder Anzeigeoption gilt jeweils für:
	 *  
	 *  PLZ, Telefonnummer, Fax: 
	 *  Wenn keine Zahl eingegeben wurde, wird
	 *  @exception InputMismatchException aufgefangen.
	 *  Die Variable goOn1 wieder auf true gesetzt.
	 *  --> Eingabe muss für die jeweilige Anzeigeoption erneut gemacht werden.
	 *   
	 * 	Name, Vorname, Anrede, Ort:
	 * 	Wenn ein Zeichen, das sich nicht im Ausdruck "[a-zA-Züöäß -]"
	 *  befindet, eingegebn wird, dan wird die Variable goOn1 wieder auf true
	 *  gesetzt.
	 *  --> Eingabe muss für die jeweilige Anzeigeoption erneut gemacht werden.
	 * 
	 * Straße, Bemerkung:
	 * goOn1 ist konstant false 
	 * --> Keine Wiederholung der Eingabe
	 * 
	 * Aufruf der Funktion {@link #kontrolleEingabe()}
	 */
	public static void personAufnehmen() {
		System.out.println("Geben sie bitte die Daten ein:"+"\n");
		for (int a=0; a<Adreli.ANZEIGEOPTIONEN.length;a++) {
			boolean goOn1= true;
			while (goOn1) {
				System.out.print(Adreli.ANZEIGEOPTIONEN[a]);
				Scanner sc = new Scanner(System.in);
				if (a==4 || a==6 || a==7) {
					goOn1 = false;
					try {
						int wert = sc.nextInt();
						Adreli.einzelnePerson[a] = Integer.toString(wert);	
					}
					catch (InputMismatchException ime) {
						goOn1 = true;
						switch (a) {
						case 4 : 
						System.out.println("Sie haben keine PLZ eingegeben!!!");
							break;
						case 6 : 
						System.out.println
						("Sie haben keine Telefonnummer eingegeben!!!");
							break;
						case 7 : 
						System.out.println
						("Sie haben keine Fax-Nummer eingeben!!!");
							break;
						}

					}
				}
				else if (a==0 | a==1 | a==2 | a==5) {
					goOn1 = false;
					Adreli.einzelnePerson[a] = sc.nextLine();
					for (int b=0; b<Adreli.einzelnePerson[a].length();b++) {
						String substring = 
								Adreli.einzelnePerson[a].substring(b, b+1);
						if (substring.matches("[^a-zA-Züöäß -]")) {
							switch (a) {
							case 0 : 
							System.out.println
							("Sie haben keinen Name eingegeben!!!");
								break;
							case 1 : 
							System.out.println
							("Sie haben keinen Vorname eingeben!!!");
								break;
							case 2 : 
							System.out.println
							("Sie haben keine Anrede eingeben!!!");
								break;
							case 5: 
							System.out.println
							("Sie haben keinen Ort eingegeben!!!");
								break;
							}
							goOn1=true;
							break;
						}
					}
				}
				else {
					goOn1 = false;
					Adreli.einzelnePerson[a] = sc.nextLine();
				}
			}
		}
		Adreli.kontrolleEingabe();
	}
	
	
	/**
	 * Hilfsfunktion für {@link #personAufnehmen()}
	 * kontrolleEingabe
	 * void
	 * 
	 * Es folgen zwei Fragen an den Benutzer:
	 * 1. "Stimmt's? (J/N)" mit Variable boolean goOn2
	 * 2. "Noch eine Person aufnehmen? (J/N)" mit Variable boolean goOn3
	 * Danach wird mit {@link Pattern#matches()} die Eingabe auf die Ausdrücke
	 * "[nN]" und "[jJ]" überprüft. 
	 * Die Variablen goOn2, goOn3 werden true gesetzt, falls der Benutzer
	 * keinen der Ausdrücke benutzt hat. 
	 * --> Die Eingabe muss wiederholt werden.
	 */
	public static void kontrolleEingabe() {
		boolean goOn2 = false;
		do {
			System.out.println("Stimmt's (J/N)");
			Scanner sc = new Scanner(System.in);
			String eingabe1 = sc.nextLine();  
			if (eingabe1.matches("[nN]")) {
				goOn2=false;
				Adreli.personAufnehmen();
			}
			else if (eingabe1.matches("[Jj]")) {
				goOn2=false;
				Adreli.personSichern(true);
				boolean goOn3 = false;
				do {
					System.out.println("Noch eine Person aufnehmen? (J/N)");
					String eingabe2 = sc.nextLine();
					if (eingabe2.matches("[Jj]")) {
						Adreli.personAufnehmen();
					}
					else if (eingabe2.matches("[nN]")) {
						goOn3 = false;
					}
					else {
						goOn3=true;
					}
				}
				while (goOn3);
			}
			else {
				goOn2=true;
			}
		}
		while (goOn2);
	}
	
	
	/**
	 * personSichern
	 * @param wahr
	 * void
	 * 
	 * Hilfsfunktion für
	 * {@link #personAufnehmen()} --> Verwaltungsfunktion 1
	 * {@link #datenLadenDatei(boolean)} --> Verwaltungsfunktion 4
	 * {@link #nummerierenInDatei()} --> Verwaltungsfunktion 6
	 * 
	 * Für Verwaltungsfunktion 1 und 4 gilt:
	 * @param wahr ist true.
	 * Die Daten vom Zwischenspeicher {@link #einzelnePerson} werden
	 * in den Hauptspeicher geladen {@link #personenListe}.
	 * Counter {@link #personZaehler} wird pro Datensatz um 1 erhöht.
	 * 
	 * Für Verwaltungsfunktion 6 gilt:
	 * @param wahr ist false.
	 * Die Daten vom Zwischenspeicher {@link #einzelnePerson} werden in
	 * den Speicher {@link #cachePersonenListe} zur Nummerierung gelegt.
	 * Counter {@link #cachePersonZaehler} wird pro Datensatz um 1 erhöht. 
	 */
	public static void personSichern(boolean wahr) {
		/** Für Verwaltungsfunktion 1 und 4: */
		if (wahr) {
			for (int a=0; 
					a<Adreli.personenListe[Adreli.personZaehler].length; a++) {
				Adreli.einzelnePerson[a] = Adreli.einzelnePerson[a].trim();
				Adreli.einzelnePerson[a] 
						= Adreli.einzelnePerson[a].replaceAll(" +", " ");
				Adreli.personenListe[Adreli.personZaehler][a] 
				= Adreli.einzelnePerson[a];
			}
			Adreli.personZaehler++; 
		}
		/** Für Verwaltungsfunktion 6 */
		else {
			for (int a=0; a<Adreli.cachePersonenListe
					[Adreli.cachePersonZaehler].length; a++) {
				Adreli.cachePersonenListe
				[Adreli.cachePersonZaehler][a] 
				= Adreli.einzelnePerson[a];
			}
			Adreli.cachePersonZaehler++;
		}
	}
	
	
	/**
	 * Verwaltungsfunktion 2
	 * personenAuflisten
	 * void
	 * 
	 * Die Datensätze werden zeilenweise aus dem Programmspeicher
	 * {@link #personenListe} geladen und auf der Konsole angezeigt. 
	 * Es wird mit Hilfe einer if-else Anweisung überprüft, ob ein weiterer
	 * Datensatz im Speicher {@link #personenListe} vorliegt.
	 * 
	 * Um zum nächsten Datensatz zu gelangen, muss 
	 * die Enter Taste gedrückt werden.
	 */
	public static void personenAuflisten() {
		for (int a=0; a<Adreli.personZaehler; a++) {
			System.out.println("Satzinhalt ("+(a+1)+". Satz)");
			for (int b=0; b<Adreli.personenListe[a].length; b++) {
				System.out.print(Adreli.ANZEIGEOPTIONEN[b]);
				System.out.println(Adreli.personenListe[a][b]);
			}
			System.out.println();
			
			if ((a+1) < Adreli.personZaehler) {
				System.out.println("Es gibt noch "+
					(Adreli.personZaehler-(a+1))+ " Datensaetze");
				System.out.println("Weiter mit \"Enter\"");
				Scanner sc = new Scanner(System.in);
				String eingabe = sc.nextLine();
			}
			else {
				System.out.println("Keine Datensaetze mehr da");
				System.out.println("Weiter mit \"Enter\"");
				Scanner sc = new Scanner(System.in);
				String eingabe = sc.nextLine();
			}
		}
	}
	
	
	/**
	 * Verwaltungsfunktion 3
	 * datenSichernDatei
	 * @param wahr
	 * void
	 * 
	 * Zuerst wird die alte Datei gelöscht und eine neue Datei erstellt.
	 * --> Die alten Daten werden somit überschrieben.
	 * 
	 * Wenn {@link #datenSichernDatei(boolean)}
	 * @param wahr = true, dann werden aus {@link #personenListe} zeilenweise
	 * die Datensätze unnummeriert in die Datei geschrieben.
	 * 
	 *  Wenn {@link #datenSichernDatei(boolean)}
	 *  @param wahr= false, dann werden aus {@link #cachePersonenListe}
	 *  zeilenweise die Datensätze nummeriert in die Datei geschrieben.
	 */
	public static void datenSichernDatei(boolean wahr) {
		try {
			Files.delete(pfad);
			Files.createFile(pfad);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		try (BufferedWriter bw = 
				Files.newBufferedWriter(pfad, Charset.defaultCharset())) {
			if (wahr) {
				for (int a=0; a<Adreli.personZaehler; a++) {
					for (int b=0; b<Adreli.personenListe[a].length; b++) {
						bw.write(Adreli.personenListe[a][b]+";");
					}
					bw.newLine();
				}
			}
			else {
				int nummerZaehler=1;
				for (int a=0; a<Adreli.cachePersonZaehler; a++) {
					for (int b=0; 
						b<Adreli.cachePersonenListe[a].length; b++) {
						if (b==0) {
							bw.write(Integer.toString(nummerZaehler)+";");
							nummerZaehler++;
						}
						bw.write(Adreli.cachePersonenListe[a][b]+";");
					}
					bw.newLine();
				}
			}
			Adreli.cachePersonZaehler=0;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	/**
	 * Verwaltungsfunktion 4
	 * datenLadenDatei
	 * @param wahr
	 * void
	 * Bei dieser Methode wird:
	 * 1. eine Zeile in eine String-Variable eingelesen.
	 * 2. Es wird geprüft, ob die Datei nummeriert wurde.
	 * 3. Wenn ja, dann fange das Einlesen in den Zwischenspeicher
	 * 	  {@link #einzelnePerson} bei Spalte 2 an.
	 * 	  Wenn nein, dann fange das Einlesen in den Zwischenspeicher
	 * 	  {@link #einzelnePerson} bei Spalte 1 an.
	 * 4. Führe die Methode {@link #personSichern(boolean)} aus.
	 * 5. Wiederhole 1-4 solange bis die String-Variable den Wert null besitzt.
	 * 
	 * Der Wahrheitswert wird an {@link #personSichern(boolean)} weitergeben.
	 */
	public static void datenLadenDatei(boolean wahr) {
		try (BufferedReader br = 
				Files.newBufferedReader(pfad, Charset.defaultCharset())) {
			int zeilenZaehler = 1;
			String zeile = null;
			while ((zeile=br.readLine()) != null) {
				String teile [] = zeile.split(";");
				if (teile[0].equals(Integer.toString(zeilenZaehler))) {
					for (int a=0; a<Adreli.einzelnePerson.length; a++) {
						Adreli.einzelnePerson[a] = teile[a+1];
					}
					zeilenZaehler++;
				}
				else {
					for (int a=0; a<Adreli.einzelnePerson.length;a++) {
						Adreli.einzelnePerson[a] = teile[a];
					}
				}
				Adreli.personSichern(wahr);
			}
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
		}
	}
	
	
	/**
	 * Verwaltungsfunktion 5
	 * datenSortierenQuickSort
	 * @param personenListe
	 * @param oben
	 * @param unten
	 * void
	 * Typischer Quicksort-Algorithmus
	 * ruft Hilfsfunktion {@link #teile(String[][], int, int)} auf
	 */
	public static void datenSortierenQuickSort
	(int oben, int unten)	{
	  	if (oben<unten) {
	  		int teile = Adreli.teile(oben, unten);
	  		Adreli.datenSortierenQuickSort(oben, teile-1);
	  		Adreli.datenSortierenQuickSort(teile+1, unten);
	  	}	
	}
	  
	
	 /**
	 * Hilfsfunktion für {@link #datenSortierenQuickSort(String[][], int, int)}
	 * teile
	 * @param personenListe
	 * @param oben
	 * @param unten
	 * @return Integer
	 */ 
	public static int teile(int oben, int unten) {	
		String pivot, hilfe;
	  	int i, j;
	  	pivot = personenListe[unten][0];
	  	i=oben;
	  	j=unten-1;
	  	while (i<=j) {
	  		if (personenListe[i][0].compareTo(pivot) > 0 ) {
	  			for (int a=0; a<personenListe[a].length;a++) {
	  				hilfe = personenListe[i][a];
	      			personenListe[i][a] = personenListe[j][a];
	      			personenListe[j][a] = hilfe;
	  			}
	  			j--;
	  		}
	  		else {
	  			i++;
	  		}
	  	}
	  	for (int a=0; a<personenListe[a].length; a++) {
	  		hilfe = personenListe[i][a];
	      	personenListe[i][a] = personenListe[unten][a];
	      	personenListe[unten][a] = hilfe;
	  	}
	  	return i;
	 }
	
	
	
	
	/*public static void sortMemoryDataBubbleSort() {
		String help;
		for ( int a=0; a<Start.personCounter; a++) {
			for (int b=1; b<Start.personCounter; b++) {
				if (Start.personList[b][0].compareTo(Start.personList[b-1][0]) 
						< 0) {
					for (int c=0; c<Start.personList[b].length;c++) {
						help = Start.personList[b-1][c];
						Start.personList[b-1][c] = Start.personList[b][c];
						Start.personList[b][c] = help;
					}
				}
			}
		}
	}*/
     
	
	/**
	 * Verwaltungsfunktion 6
	 * nummerierenInDatei
	 * void
	 * 
	 * Ablauf der Funktion:
	 * 1. Die Daten werden in der Funktion 
	 * {@link #datenLadenDatei(boolean)} aus der Datei geladen.
	 * 2. Die Daten werden in der Funktion
	 * {@link #datenSichernDatei(boolean)} mit Nummerierung wieder in die Datei
	 * geschrieben.
	 * Den Funktionen {@link #datenLadenDatei(boolean)}
	 * und {@link #datenSichernDatei(boolean)} wird der boolean
	 * Parameterwert false übergeben, um so in den jeweiligen Funktionen
	 * mit Hilfe einer if-else-Anweisung die Nummerierung zu berücksichtigen.
	 * 
	 * Für die Nummerierung ist ein weiterer Zwischenspeicher
	 * {@link #cachePersonenListe} und ein weiterer Counter 
	 * {@link #cachePersonZaehler} notwendig, da die Daten nicht im
	 * Hauptspeicher vorhanden sein sollen.
	 */
	public static void nummerierenInDatei() {
		Adreli.datenLadenDatei(false);
		Adreli.datenSichernDatei(false);
	}
	
	
	/**
	 * Verwaltungsfunktion 7
	 * programmBeenden
	 * void
	 * Das Programm wird verlassen.
	 */
	public static void programmBeenden() {
		System.out.println("Das Programm wurde verlassen!");
		System.exit(0);
	}
}