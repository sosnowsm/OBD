package obd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OBD_jdbc_4_homework_Ocenianie {

	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		String uzytkownik = "xmsosnow";
		String haslo = "xmsosnow";

		// Dane do tworzenia tabel

		String sql1 = "CREATE TABLE przedmiot (idp integer not null, nazwa_przedmiotu char(20) not null)";
		String sql2 = "CREATE TABLE nauczyciel (idn integer not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)";
		String sql3 = "CREATE TABLE ocenianie (idp integer not null, idn integer not null, idu integer not null, ido integer not null, rodzaj_oceny char(1) not null)";
		String sql4 = "CREATE TABLE uczen (idu integer not null, nazwisko_ucznia char(30) not null, imie_ucznia char (20) not null)";
		String sql5 = "CREATE TABLE ocena (ido integer not null, wartosc_opisowa char(20) not null, wartosc_numeryczna float not null)";

		// Przykłodowe dane

		String sql11 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (1, 'Kowalski', 'Jan')";
		String sql12 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (2, 'Wisniewski', 'Stefan')";
		String sql13 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (3, 'Jazwinski', 'Michal')";

		String sql21 = "INSERT INTO uczen(idu, nazwisko_ucznia, imie_ucznia) VALUES (1, 'Posmiech', 'Marian')";
		String sql22 = "INSERT INTO uczen(idu, nazwisko_ucznia, imie_ucznia) VALUES (2, 'Czerwinski', 'Konrad')";
		String sql23 = "INSERT INTO uczen(idu, nazwisko_ucznia, imie_ucznia) VALUES (3, 'Maciejko', 'Krzysztof')";

		String sql31 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (1, 'ATJ')";
		String sql32 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (2, 'PPJ')";
		String sql33 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (3, 'PRK')";

		String sql41 = "INSERT INTO ocena(ido, wartosc_opisowa, wartosc_numeryczna) VALUES (1, 'niedostateczny', 1)";
		String sql42 = "INSERT INTO ocena(ido, wartosc_opisowa, wartosc_numeryczna) VALUES (2, 'mierny', 2)";
		String sql43 = "INSERT INTO ocena(ido, wartosc_opisowa, wartosc_numeryczna) VALUES (3, 'dostateczny', 3)";
		String sql44 = "INSERT INTO ocena(ido, wartosc_opisowa, wartosc_numeryczna) VALUES (4, 'dobry', 4)";
		String sql45 = "INSERT INTO ocena(ido, wartosc_opisowa, wartosc_numeryczna) VALUES (5, 'bardzo dobry', 5)";

		int czyDalej = 1;
		boolean poprawnyNauczyciel = false;
		boolean poprawnyPrzedmiot = false;
		boolean poprawnyUczen = false;
		boolean poprawnaOcena = false;
		boolean poprawnyRodzajOceny = false;
		int idn = 0;
		int ido = 0;
		int idp = 0;
		String line;
		String sql;
		int idu = 0;
		String rodzajOceny = null;
		ResultSet rs;
		Scanner scanner = new Scanner(System.in);

		try {
			Connection polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
			System.out.println("AutoCommit: " + polaczenie.getAutoCommit());
			Statement polecenie = polaczenie.createStatement();

			// Tworzenie tabel

			try {
				System.out.println("execute: " + polecenie.execute(sql1));
				System.out.println("execute: " + polecenie.execute(sql2));
				System.out.println("execute: " + polecenie.execute(sql3));
				System.out.println("execute: " + polecenie.execute(sql4));
				System.out.println("execute: " + polecenie.execute(sql5));
			} catch (SQLException e) {
				System.out.println("Tabele zostaly utworzone przy poprzednim uruchomieniem programu.");
			}

			// Wypelnianie tabel przykladowymi danymi
			
			try {
				sql = "SELECT * FROM przedmiot WHERE idp = 1";
				rs = polecenie.executeQuery(sql);
				rs.next();
				//int czyDane = 
				rs.getInt("idp");
				System.out.println("Przykladowe dane zostaly wprowadzone przy poprzednim uruchomieniem programu.");
			} catch (SQLException e) {

				System.out.println("execute: " + polecenie.executeUpdate(sql11));
				System.out.println("execute: " + polecenie.executeUpdate(sql12));
				System.out.println("execute: " + polecenie.executeUpdate(sql13));

				System.out.println("execute: " + polecenie.executeUpdate(sql21));
				System.out.println("execute: " + polecenie.executeUpdate(sql22));
				System.out.println("execute: " + polecenie.executeUpdate(sql23));

				System.out.println("execute: " + polecenie.executeUpdate(sql31));
				System.out.println("execute: " + polecenie.executeUpdate(sql32));
				System.out.println("execute: " + polecenie.executeUpdate(sql33));

				System.out.println("execute: " + polecenie.executeUpdate(sql41));
				System.out.println("execute: " + polecenie.executeUpdate(sql42));
				System.out.println("execute: " + polecenie.executeUpdate(sql43));
				System.out.println("execute: " + polecenie.executeUpdate(sql44));
				System.out.println("execute: " + polecenie.executeUpdate(sql45));
			}

			// Ocenianie wlasciwe

			while (czyDalej == 1) {

				while (!poprawnyPrzedmiot) {
					try {
						System.out.println("Podaj id przedmiotu");
					
						line = scanner.nextLine();
						idp = Integer.parseInt(line);
						sql = "SELECT * FROM przedmiot WHERE idp = " + idp;
						rs = polecenie.executeQuery(sql);
						rs.next();
						System.out.println("Wybrales: " + rs.getString("nazwa_przedmiotu"));
						poprawnyPrzedmiot = true;

					} catch (NumberFormatException e) {
						System.out.println("Nie numeryczne dane.");
					} catch (SQLRecoverableException e) {
						System.out.println("Blad programu. Brak polaczenia.");
						return;
					} catch (SQLException e) {
						System.out.println("Indeks poza zakresem.");
					} catch (NoSuchElementException e) {
						System.out.println("Koniec programu. Uzytkownik wydal komende EOF.");
						return;
					}
				}

				while (!poprawnyNauczyciel) {
					try {
						System.out.println("Podaj id nauczyciela");
						line = scanner.nextLine();
						idn = Integer.parseInt(line);
						sql = "SELECT * FROM nauczyciel WHERE idn = " + idn;
						rs = polecenie.executeQuery(sql);
						rs.next();
						System.out.println("Wybrales: " + rs.getString("imie_nauczyciela") + " "
								+ rs.getString("nazwisko_nauczyciela"));
						poprawnyNauczyciel = true;
					} catch (NumberFormatException e) {
						System.out.println("Nie numeryczne dane.");
					} catch (SQLRecoverableException e) {
						System.out.println("Blad programu. Brak polaczenia.");
						return;
					} catch (SQLException e) {
						System.out.println("Indeks poza zakresem.");
					} catch (NoSuchElementException e) {
						System.out.println("Koniec programu. Uzytkownik wydal komende EOF.");
						return;
					}

				}

				while (!poprawnyUczen) {
					try {
						System.out.println("Podaj id ucznia");
						line = scanner.nextLine();
						idu = Integer.parseInt(line);
						sql = "SELECT * FROM uczen WHERE idu = " + idu;
						rs = polecenie.executeQuery(sql);
						rs.next();
						System.out.println(
								"Wybrales: " + rs.getString("imie_ucznia") + " " + rs.getString("nazwisko_ucznia"));
						poprawnyUczen = true;
					} catch (NumberFormatException e) {
						System.out.println("Nie numeryczne dane.");
					} catch (SQLRecoverableException e) {
						System.out.println("Blad programu. Brak polaczenia.");
						return;
					} catch (SQLException e) {
						System.out.println("Indeks poza zakresem.");
					} catch (NoSuchElementException e) {
						System.out.println("Koniec programu. Uzytkownik wydal komende EOF.");
						return;
					}
				}

				while (!poprawnaOcena) {
					try {
						System.out.println("Podaj id oceny");
						line = scanner.nextLine();
						ido = Integer.parseInt(line);
						sql = "SELECT * FROM ocena WHERE ido = " + ido;
						rs = polecenie.executeQuery(sql);
						rs.next();
						System.out.println("Wybrales: " + rs.getString("wartosc_opisowa"));
						poprawnaOcena = true;
					} catch (NumberFormatException e) {
						System.out.println("Nie numeryczne dane.");
					} catch (SQLRecoverableException e) {
						System.out.println("Blad programu. Brak polaczenia.");
						return;
					} catch (SQLException e) {
						System.out.println("Indeks poza zakresem.");
					} catch (NoSuchElementException e) {
						System.out.println("Koniec programu. Uzytkownik wydal komende EOF.");
						return;
					}
				}

				while (!poprawnyRodzajOceny) {
					System.out.println("Podaj rodzaj oceny (c/s)");
					rodzajOceny = scanner.nextLine();
					if (rodzajOceny.equals("c")) {
						poprawnyRodzajOceny = true;
					}
					if (rodzajOceny.equals("s")) {
						poprawnyRodzajOceny = true;
					}

				}

				sql = "INSERT INTO ocenianie (idp, idn, idu, ido, rodzaj_oceny) VALUES (" + idp + "," + idn + "," + idu
						+ "," + ido + ", '" + rodzajOceny + "')";
				System.out.println(sql);
				System.out.println("execute: " + polecenie.executeUpdate(sql));
				System.out.println("Kolejna ocena ? (t/n)");
				String czy = scanner.nextLine();
				if (czy.equals("t")) {
					poprawnyNauczyciel = false;
					poprawnyPrzedmiot = false;
					poprawnyUczen = false;
					poprawnaOcena = false;
					poprawnyRodzajOceny = false;
					czyDalej = 1;
				} else {
					czyDalej = 0;
					polaczenie.close();
					scanner.close();
				}
			}

			System.out.println("KONIEC.");
		} catch (NoSuchElementException e) {
			System.out.println("Koniec programu. Uzytkownik wydal komende EOF.");
			return;
		} catch (SQLRecoverableException e) {
			System.out.println("Blad programu. Brak polaczenia.");
			return;
		} catch (Exception e) {

			System.out.println("Blad programu.");
			e.printStackTrace();
			return;

		}
	}
}
