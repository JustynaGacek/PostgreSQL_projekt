import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import javax.swing.table.*;


public class okno extends JFrame{

	//otwarcie polaczenia z baza
	PascalConnection pc; 
    Connection con;

	JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d = new JPanel(new FlowLayout(FlowLayout.LEFT));

	JPanel p_dzieci = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_opiekunowie = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_trenerzy = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_pozycje = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_druzyny = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_treningi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_osprzet = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_treningi_osprzet = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_s_prywatni = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_druzyny_s_prywatni = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_s_firmowi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_druzyny_s_firmowi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_mecze_plan = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_mecze_roz = new JPanel(new FlowLayout(FlowLayout.LEFT));

	JPanel d_dzieci = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_opiekunowie = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_trenerzy = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_druzyny = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_treningi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_s_prywatni = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_druzyny_s_prywatni = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_s_firmowi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_druzyny_s_firmowi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel d_mecze_plan = new JPanel(new FlowLayout(FlowLayout.LEFT));

	JPanel p_statystyka_mecze = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_harmonogram_druzyn = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_harmonogram_trenerow = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_harmonogram_sal = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_obciazenie_trenerow = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_druzyny_ilosc_sponsorow = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_pojemnosc_druzyn = new JPanel(new FlowLayout(FlowLayout.LEFT));

	JTabbedPane panelTabelowy = new JTabbedPane();

	JTabbedPane zakladka_dodawanie = new JTabbedPane();
	JTabbedPane zakladka_usuwanie = new JTabbedPane();
	JTabbedPane zakladka_tabele = new JTabbedPane();
	JTabbedPane zakladka_widoki = new JTabbedPane();

	JPanel dzieci = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField dzieci_imie = new JTextField(7);
	JTextField dzieci_nazwisko = new JTextField(7);
	JTextField dzieci_wiek = new JTextField(4);
	JTextField dzieci_data = new JTextField(7);
	JTextField dzieci_pozycja = new JTextField(4);
	JTextField dzieci_druzyna = new JTextField(4);
	JTextField dzieci_opiekun = new JTextField(4);
	JButton insert_dzieci = new JButton("dodaj");
	JTextField id_dziecka_do_usuniecia = new JTextField(4);
	JButton delete_dziecko = new JButton("usun");

	JPanel opiekunowie = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField opiekunowie_imie = new JTextField(7);
	JTextField opiekunowie_nazwisko = new JTextField(7);
	JTextField opiekunowie_telefon = new JTextField(7);
	JTextField opiekunowie_dowod = new JTextField(7);
	JTextField opiekunowie_adres = new JTextField(7);
	JButton insert_opiekunowie = new JButton("dodaj");
	JTextField id_opiekuna_do_usuniecia = new JTextField(4);
	JButton delete_opiekun = new JButton("usun");

	JPanel trenerzy = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField trenerzy_imie = new JTextField(7);
	JTextField trenerzy_nazwisko = new JTextField(7);
	JTextField trenerzy_telefon = new JTextField(7);
	JTextField trenerzy_dowod = new JTextField(7);
	JTextField trenerzy_adres = new JTextField(7);
	JButton insert_trenerzy = new JButton("dodaj");
	JTextField id_trenera_do_usuniecia = new JTextField(4);
	JButton delete_trener = new JButton("usun");
	
	JPanel pozycje = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField pozycje_nazwa = new JTextField(7);
	JButton insert_pozycje = new JButton("dodaj");

	JPanel druzyny = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField druzyny_nazwa = new JTextField(7);
	JTextField druzyny_trener = new JTextField(4);
	JTextField druzyny_min_wiek = new JTextField(4);
	JTextField druzyny_max_wiek = new JTextField(4);
	JButton insert_druzyny = new JButton("dodaj");
	JTextField id_druzyny_do_usuniecia = new JTextField(4);
	JButton delete_druzyna = new JButton("usun");

	JPanel treningi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField treningi_dzien = new JTextField(7);
	JTextField treningi_godzina = new JTextField(7);
	JTextField treningi_czas = new JTextField(7);
	JTextField treningi_sala = new JTextField(4);
	JTextField treningi_trener = new JTextField(4);
	JTextField treningi_druzyna = new JTextField(4);
	JButton insert_treningi = new JButton("dodaj");
	JTextField id_treningu_do_usuniecia = new JTextField(4);
	JButton delete_trening = new JButton("usun");

	JPanel s_prywatni = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField s_prywatni_imie = new JTextField(7);
	JTextField s_prywatni_nazwisko = new JTextField(7);
	JTextField s_prywatni_adres = new JTextField(7);
	JButton insert_s_prywatni = new JButton("dodaj");
	JTextField id_s_prywatnego_do_usuniecia = new JTextField(4);
	JButton delete_s_prywatny = new JButton("usun");

	JPanel druzyny_s_prywatni = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField druzyny_s_prywatni_druzyna = new JTextField(4);
	JTextField druzyny_s_prywatni_sponsor = new JTextField(4);
	JButton insert_druzyny_s_prywatni = new JButton("dodaj");
	JTextField id_lacz_druzyna_p_do_usuniecia = new JTextField(4);
	JTextField id_lacz_s_prywatny_do_usuniecia = new JTextField(4);
	JButton delete_druzyna_s_prywatny = new JButton("usun");

	JPanel s_firmowi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField s_firmowi_nazwa = new JTextField(7);
	JTextField s_firmowi_adres = new JTextField(7);
	JButton insert_s_firmowi = new JButton("dodaj");
	JTextField id_s_firmowego_do_usuniecia = new JTextField(4);
	JButton delete_s_firmowy = new JButton("usun");

	JPanel druzyny_s_firmowi = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField druzyny_s_firmowi_druzyna = new JTextField(4);
	JTextField druzyny_s_firmowi_sponsor = new JTextField(4);
	JButton insert_druzyny_s_firmowi = new JButton("dodaj");
	JTextField id_lacz_druzyna_f_do_usuniecia = new JTextField(4);
	JTextField id_lacz_s_firmowy_do_usuniecia = new JTextField(4);
	JButton delete_druzyna_s_firmowy = new JButton("usun");

	JPanel mecze_plan = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField mecze_plan_data = new JTextField(7);
	JTextField mecze_plan_miejsce = new JTextField(7);
	JTextField mecze_plan_przeciwnik = new JTextField(7);
	JTextField mecze_plan_druzyna = new JTextField(4);
	JButton insert_mecze_plan = new JButton("dodaj");
	JTextField id_meczu_do_usuniecia = new JTextField(4);
	JButton delete_mecz = new JButton("usun");

	JPanel mecze_roz = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextField mecze_roz_data = new JTextField(7);
	JTextField mecze_roz_punkty_druzyny = new JTextField(4);
	JTextField mecze_roz_punkty_przeciwnika = new JTextField(4);
	JTextField mecze_roz_przeciwnik = new JTextField(7);
	JTextField mecze_roz_druzyna = new JTextField(4);
	JButton insert_mecze_roz = new JButton("dodaj");

	JTable tabela_z_dziecmi;
	JTable tabela_z_opiekunami;
	JTable tabela_z_trenerami;
	JTable tabela_z_pozycjami;
	JTable tabela_z_druzynami;
	JTable tabela_z_treningami;
	JTable tabela_z_s_prywatnymi;
	JTable tabela_z_druzynami_s_prywatnymi;
	JTable tabela_z_s_firmowymi;
	JTable tabela_z_druzynami_s_firmowymi;
	JTable tabela_z_osprzetem;
	JTable tabela_z_treningami_osprzetem;
	JTable tabela_z_meczami_rozegranymi;
	JTable tabela_z_meczami_planowanymi;

	static Object[][] tabela;
	static Object[] kolumny_dzieci = {"Id dziecka", "Imie", "Nazwisko", "Wiek", "Data przyjecia", "Id pozycji", "Id druzyny", "Id opiekuna"};
	static ResultSet wiersze;
	static ResultSetMetaData metaDane;
	JScrollPane scroll_z_dzieci;
	static DefaultTableModel model_dzieci = new DefaultTableModel(tabela, kolumny_dzieci);

	Object[] kolumny_opiekunowie = {"Id opiekuna", "Imie", "Nazwisko", "Numer telefonu", "Numer dowodu", "Adres"};
	JScrollPane scroll_z_opiekunow;
	DefaultTableModel model_opiekunowie = new DefaultTableModel(tabela, kolumny_opiekunowie);

	Object[] kolumny_trenerzy = {"Id trenera", "Imie", "Nazwisko", "Numer telefonu", "Numer dowodu", "Adres"};
	JScrollPane scroll_z_trenerami;
	DefaultTableModel model_trenerzy = new DefaultTableModel(tabela, kolumny_trenerzy);

	Object[] kolumny_pozycje = {"Id pozycji", "Nazwa"};
	JScrollPane scroll_z_pozycjami;
	DefaultTableModel model_pozycje = new DefaultTableModel(tabela, kolumny_pozycje);

	Object[] kolumny_druzyny = {"Id drużyny", "Nazwa", "Id trenera", "Wiek minimalny", "Wiek maksymalny"};
	JScrollPane scroll_z_druzynami;
	DefaultTableModel model_druzyny = new DefaultTableModel(tabela, kolumny_druzyny);

	Object[] kolumny_treningi = {"Id treningu", "Dzień", "Godzina", "Czas trwania", "Numer sali", "Id trenera", "Id druzyny"};
	JScrollPane scroll_z_treningami;
	DefaultTableModel model_treningi = new DefaultTableModel(tabela, kolumny_treningi);

	Object[] kolumny_s_prywatni = {"Id sponsora prywatnego", "Imie", "Nazwisko", "Adres"};
	JScrollPane scroll_z_s_prywatnymi;
	DefaultTableModel model_s_prywatni = new DefaultTableModel(tabela, kolumny_s_prywatni);

	Object[] kolumny_druzyna_s_prywatni = {"Id druzyny" ,"Id sponsora prywatnego"};
	JScrollPane scroll_z_druzynami_z_prywatnymi;
	DefaultTableModel model_druzyny_s_prywatni = new DefaultTableModel(tabela, kolumny_druzyna_s_prywatni);

	Object[] kolumny_s_firmowi = {"Id sponsora firmowego", "Nazwa firmy", "Adres siedziby"};
	JScrollPane scroll_z_s_firmowymi;
	DefaultTableModel model_s_firmowi = new DefaultTableModel(tabela, kolumny_s_firmowi);

	Object[] kolumny_druzyna_s_firmowi = {"Id druzyny" ,"Id sponsora firmowego"};
	JScrollPane scroll_z_druzynami_s_firmowymi;
	DefaultTableModel model_druzyny_s_firmowi = new DefaultTableModel(tabela, kolumny_druzyna_s_firmowi);

	Object[] kolumny_mecze_rozegrane = {"Id meczu rozegranego" ,"Data", "Punkty druzyny", "Punkty przeciwnika", "Nazwa przeciwnika", "Id drużyny grającej"};
	JScrollPane scroll_z_meczmi_rozegranymi;
	DefaultTableModel model_mecze_rozegrane = new DefaultTableModel(tabela, kolumny_mecze_rozegrane);

	Object[] kolumny_mecze_planowane = {"Id meczu planowanego" ,"Data", "Miejsce", "Nazwa przeciwnika", "Id drużyny grającej"};
	JScrollPane scroll_z_meczmi_planowanymi;
	DefaultTableModel model_mecze_planowane = new DefaultTableModel(tabela, kolumny_mecze_planowane);


	Object[] kolumny_statystyka_mecze = {"Nazwa druzyny", "Mecze rozegrane", "Mecze wygrane", "Mecze zremisowane", "Mecze przegrane", "Procent wygranych"};
	JScrollPane scroll_z_statystyka_meczy;
	DefaultTableModel model_z_statystyka_meczy = new DefaultTableModel(tabela, kolumny_statystyka_mecze);

	Object[] kolumny_harmonogram_druzyn = {"Nazwa drużyny", "Dzień", "Poczętek treningu", "Koniec treningu", "Numer sali"};
	JScrollPane scroll_z_harmonogramem_druzyn;
	DefaultTableModel model_harmonogram_druzyn = new DefaultTableModel(tabela, kolumny_harmonogram_druzyn);

	Object[] kolumny_harmonogram_trenerow = {"Nazwisko trenera", "Imie", "Dzień", "Godzina rozpoczęcia", "Godzina zakończenia", "Numer sali"};
	JScrollPane scroll_z_harmonogramem_trenerow;
	DefaultTableModel model_harmonogram_trenerow = new DefaultTableModel(tabela, kolumny_harmonogram_trenerow);

	Object[] kolumny_harmonogram_sal = {"Numer sali", "Dzień", "Godzina rozpoczęcia", "Godzina zakończenia"};
	JScrollPane scroll_z_harmonogramem_sal;
	DefaultTableModel model_harmonogram_sal = new DefaultTableModel(tabela, kolumny_harmonogram_sal);

	Object[] kolumny_pojemnosc_druzyn = {"Nazwa drużyny", "Ilość dzieci"};
	JScrollPane scroll_z_pojemnoscia_druzyn;
	DefaultTableModel model_pojemnosc_druzyn = new DefaultTableModel(tabela, kolumny_pojemnosc_druzyn);

	Object[] kolumny_obciazenie_trenerow = {"Nazwisko trenera", "Imie", "Ilość godzin", "Ilość drużyn"};
	JScrollPane scroll_z_obciazeniem_trenerow;
	DefaultTableModel model_z_obciazeniem_trenerow = new DefaultTableModel(tabela, kolumny_obciazenie_trenerow);

	Object[] kolumny_druzyny_ilosc_sponsorow = {"Nazwa drużyny", "Ilość sponsorów prywatnych", "Ilość sponsorów firmowych", "Ilość wszystkich sponsorów"};
	JScrollPane scroll_z_druzyny_ilosc_sponsorow;
	DefaultTableModel model_z_druzyny_ilosc_sponsorow = new DefaultTableModel(tabela, kolumny_druzyny_ilosc_sponsorow);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public okno(){
		super("Dziecieca szkolka pilkarska - baza danych");

		//otwarcie polaczenia z baza
		pc = PascalConnection.getInstance();
    	con = pc.getConnection();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1450,750);

		dzieci.setPreferredSize(new Dimension(1300, 55));
		opiekunowie.setPreferredSize(new Dimension(1300, 55));
		trenerzy.setPreferredSize(new Dimension(1300, 55));
		pozycje.setPreferredSize(new Dimension(1300, 55));
		druzyny.setPreferredSize(new Dimension(1300, 55));
		treningi.setPreferredSize(new Dimension(1300, 55));
		s_prywatni.setPreferredSize(new Dimension(1300, 55));
		druzyny_s_prywatni.setPreferredSize(new Dimension(1300, 55));
		s_firmowi.setPreferredSize(new Dimension(1300, 55));
		druzyny_s_firmowi.setPreferredSize(new Dimension(1300, 55));
		mecze_plan.setPreferredSize(new Dimension(1300, 55));
		mecze_roz.setPreferredSize(new Dimension(1300, 55));

		d_dzieci.setPreferredSize(new Dimension(1300, 55));
		d_opiekunowie.setPreferredSize(new Dimension(1300, 55));
		d_trenerzy.setPreferredSize(new Dimension(1300, 55));
		d_druzyny.setPreferredSize(new Dimension(1300, 55));
		d_treningi.setPreferredSize(new Dimension(1300, 55));
		d_s_prywatni.setPreferredSize(new Dimension(1300, 55));
		d_druzyny_s_prywatni.setPreferredSize(new Dimension(1300, 55));
		d_s_firmowi.setPreferredSize(new Dimension(1300, 55));
		d_druzyny_s_firmowi.setPreferredSize(new Dimension(1300, 55));
		d_mecze_plan.setPreferredSize(new Dimension(1300, 55));

		dzieci.setBorder(BorderFactory.createTitledBorder("Dzieci: "));
		dzieci.add(new JLabel("Imie: "));
		dzieci.add(dzieci_imie);
		dzieci.add(new JLabel("Nazwisko: "));
		dzieci.add(dzieci_nazwisko);
		dzieci.add(new JLabel("Wiek: "));
		dzieci.add(dzieci_wiek);
		dzieci.add(new JLabel("Data przyjecia: "));
		dzieci.add(dzieci_data);
		dzieci.add(new JLabel("Id pozycji: "));
		dzieci.add(dzieci_pozycja);
		dzieci.add(new JLabel("Id druzyny: "));
		dzieci.add(dzieci_druzyna);
		dzieci.add(new JLabel("Id opiekuna: "));
		dzieci.add(dzieci_opiekun);
		dzieci.add(insert_dzieci);
		insert_dzieci.addActionListener(new eventKlikniecia());
		d_dzieci.add(new JLabel("	ID dziecka do usuniecia: "));
		d_dzieci.add(id_dziecka_do_usuniecia);
		d_dzieci.add(delete_dziecko);
		delete_dziecko.addActionListener(new eventKlikniecia());

		opiekunowie.setBorder(BorderFactory.createTitledBorder("Opiekunowie: "));
		opiekunowie.add(new JLabel("Imie: "));
		opiekunowie.add(opiekunowie_imie);
		opiekunowie.add(new JLabel("Nazwisko: "));
		opiekunowie.add(opiekunowie_nazwisko);
		opiekunowie.add(new JLabel("Numer telefonu: "));
		opiekunowie.add(opiekunowie_telefon);
		opiekunowie.add(new JLabel("Numer dowodu: "));
		opiekunowie.add(opiekunowie_dowod);
		opiekunowie.add(new JLabel("Adres: "));
		opiekunowie.add(opiekunowie_adres);
		opiekunowie.add(insert_opiekunowie);
		insert_opiekunowie.addActionListener(new eventKlikniecia());
		d_opiekunowie.add(new JLabel("	ID opiekuna do usuniecia: "));
		d_opiekunowie.add(id_opiekuna_do_usuniecia);
		d_opiekunowie.add(delete_opiekun);
		delete_opiekun.addActionListener(new eventKlikniecia());

		trenerzy.setBorder(BorderFactory.createTitledBorder("Trenerzy: "));
		trenerzy.add(new JLabel("Imie: "));
		trenerzy.add(trenerzy_imie);
		trenerzy.add(new JLabel("Nazwisko: "));
		trenerzy.add(trenerzy_nazwisko);
		trenerzy.add(new JLabel("Numer telefonu: "));
		trenerzy.add(trenerzy_telefon);
		trenerzy.add(new JLabel("Numer dowodu: "));
		trenerzy.add(trenerzy_dowod);
		trenerzy.add(new JLabel("Adres: "));
		trenerzy.add(trenerzy_adres);
		trenerzy.add(insert_trenerzy);
		insert_trenerzy.addActionListener(new eventKlikniecia());
		d_trenerzy.add(new JLabel("	ID trenera do usuniecia: "));
		d_trenerzy.add(id_trenera_do_usuniecia);
		d_trenerzy.add(delete_trener);
		delete_trener.addActionListener(new eventKlikniecia());

		druzyny.setBorder(BorderFactory.createTitledBorder("Druzyny: "));
		druzyny.add(new JLabel("Nazwa: "));
		druzyny.add(druzyny_nazwa);
		druzyny.add(new JLabel("ID trenera: "));
		druzyny.add(druzyny_trener);
		druzyny.add(new JLabel("Minimalny wiek: "));
		druzyny.add(druzyny_min_wiek);
		druzyny.add(new JLabel("Maksymalny wiek: "));
		druzyny.add(druzyny_max_wiek);
		druzyny.add(insert_druzyny);
		insert_druzyny.addActionListener(new eventKlikniecia());
		d_druzyny.add(new JLabel("ID druzyny do usuniecia: "));
		d_druzyny.add(id_druzyny_do_usuniecia);
		d_druzyny.add(delete_druzyna);
		delete_druzyna.addActionListener(new eventKlikniecia());

		treningi.setBorder(BorderFactory.createTitledBorder("Treningi: "));
		treningi.add(new JLabel("Dzien: "));
		treningi.add(treningi_dzien);
		treningi.add(new JLabel("Godzina: "));
		treningi.add(treningi_godzina);
		treningi.add(new JLabel("Czas trawnia: "));
		treningi.add(treningi_czas);
		treningi.add(new JLabel("Numer sali: "));
		treningi.add(treningi_sala);
		treningi.add(new JLabel("ID trenera: "));
		treningi.add(treningi_trener);
		treningi.add(new JLabel("ID drużyny: "));
		treningi.add(treningi_druzyna);
		treningi.add(insert_treningi);
		insert_treningi.addActionListener(new eventKlikniecia());
		d_treningi.add(new JLabel("	ID treningu do usuniecia: "));
		d_treningi.add(id_treningu_do_usuniecia);
		d_treningi.add(delete_trening);
		delete_trening.addActionListener(new eventKlikniecia());

		s_prywatni.setBorder(BorderFactory.createTitledBorder("Sponsorzy prywatni: "));
		s_prywatni.add(new JLabel("Imie: "));
		s_prywatni.add(s_prywatni_imie);
		s_prywatni.add(new JLabel("Nazwisko: "));
		s_prywatni.add(s_prywatni_nazwisko);
		s_prywatni.add(new JLabel("Adres: "));
		s_prywatni.add(s_prywatni_adres);
		s_prywatni.add(insert_s_prywatni);
		insert_s_prywatni.addActionListener(new eventKlikniecia());
		d_s_prywatni.add(new JLabel(" ID sponsora prywatnego do usuniecia: "));
		d_s_prywatni.add(id_s_prywatnego_do_usuniecia);
		d_s_prywatni.add(delete_s_prywatny);
		delete_s_prywatny.addActionListener(new eventKlikniecia());

		druzyny_s_prywatni.setBorder(BorderFactory.createTitledBorder("Tabela łaczaca Druzyny i Sponsorow prywatnych: "));
		druzyny_s_prywatni.add(new JLabel("Id druzyny"));
		druzyny_s_prywatni.add(druzyny_s_prywatni_druzyna);
		druzyny_s_prywatni.add(new JLabel("Id sponsora prywatnego"));
		druzyny_s_prywatni.add(druzyny_s_prywatni_sponsor);
		druzyny_s_prywatni.add(insert_druzyny_s_prywatni);
		insert_druzyny_s_prywatni.addActionListener(new eventKlikniecia());
		d_druzyny_s_prywatni.add(new JLabel("ID druzyny: "));
		d_druzyny_s_prywatni.add(id_lacz_druzyna_p_do_usuniecia);
		d_druzyny_s_prywatni.add(new JLabel("ID sponsora prywatnego: "));
		d_druzyny_s_prywatni.add(id_lacz_s_prywatny_do_usuniecia);
		d_druzyny_s_prywatni.add(delete_druzyna_s_prywatny);
		delete_druzyna_s_prywatny.addActionListener(new eventKlikniecia());

		s_firmowi.setBorder(BorderFactory.createTitledBorder("Sponsorzy firmowi: "));
		s_firmowi.add(new JLabel("Nazwa firmy: "));
		s_firmowi.add(s_firmowi_nazwa);
		s_firmowi.add(new JLabel("Adres siedziby: "));
		s_firmowi.add(s_firmowi_adres);
		s_firmowi.add(insert_s_firmowi);
		insert_s_firmowi.addActionListener(new eventKlikniecia());
		d_s_firmowi.add(new JLabel("ID sponsora firmowego do usuniecia: "));
		d_s_firmowi.add(id_s_firmowego_do_usuniecia);
		d_s_firmowi.add(delete_s_firmowy);
		delete_s_firmowy.addActionListener(new eventKlikniecia());



		druzyny_s_firmowi.setBorder(BorderFactory.createTitledBorder("Tabela łaczaca Druzyny i Sponsorow firmowych: "));
		druzyny_s_firmowi.add(new JLabel("ID druzyny"));
		druzyny_s_firmowi.add(druzyny_s_firmowi_druzyna);
		druzyny_s_firmowi.add(new JLabel("ID sponsora prywatnego"));
		druzyny_s_firmowi.add(druzyny_s_firmowi_sponsor);
		druzyny_s_firmowi.add(insert_druzyny_s_firmowi);
		insert_druzyny_s_firmowi.addActionListener(new eventKlikniecia());
		d_druzyny_s_firmowi.add(new JLabel("ID druzyny: "));
		d_druzyny_s_firmowi.add(id_lacz_druzyna_f_do_usuniecia);
		d_druzyny_s_firmowi.add(new JLabel("ID sponsora firmowego: "));
		d_druzyny_s_firmowi.add(id_lacz_s_firmowy_do_usuniecia);
		d_druzyny_s_firmowi.add(delete_druzyna_s_firmowy);
		delete_druzyna_s_firmowy.addActionListener(new eventKlikniecia());

		mecze_plan.setBorder(BorderFactory.createTitledBorder("Mecze planowane: "));
		mecze_plan.add(new JLabel("Data: "));
		mecze_plan.add(mecze_plan_data);
		mecze_plan.add(new JLabel("Miejsce: "));
		mecze_plan.add(mecze_plan_miejsce);
		mecze_plan.add(new JLabel("Nazwa przeciwnika: "));
		mecze_plan.add(mecze_plan_przeciwnik);
		mecze_plan.add(new JLabel("ID druzyny: "));
		mecze_plan.add(mecze_plan_druzyna);
		mecze_plan.add(insert_mecze_plan);
		insert_mecze_plan.addActionListener(new eventKlikniecia());
		d_mecze_plan.add(new JLabel("ID meczu planowanego do usuniecia: "));
		d_mecze_plan.add(id_meczu_do_usuniecia);
		d_mecze_plan.add(delete_mecz);
		delete_mecz.addActionListener(new eventKlikniecia());


		mecze_roz.setBorder(BorderFactory.createTitledBorder("Mecze rozegrane: "));
		mecze_roz.add(new JLabel("Data: "));
		mecze_roz.add(mecze_roz_data);
		mecze_roz.add(new JLabel("Punkty zdobyte przez druzyne: "));
		mecze_roz.add(mecze_roz_punkty_druzyny);
		mecze_roz.add(new JLabel("Punkty zdobyte przez przeciwnika: "));
		mecze_roz.add(mecze_roz_punkty_przeciwnika);
		mecze_roz.add(new JLabel("Nazwa przeciwnika: "));
		mecze_roz.add(mecze_roz_przeciwnik);
		mecze_roz.add(new JLabel("ID druzyny: "));
		mecze_roz.add(mecze_roz_druzyna);
		mecze_roz.add(insert_mecze_roz);
		insert_mecze_roz.addActionListener(new eventKlikniecia());


		p.add(dzieci);
		p.add(opiekunowie);
		p.add(trenerzy);
		p.add(druzyny);
		p.add(treningi);
		p.add(s_prywatni);
		p.add(druzyny_s_prywatni);
		p.add(s_firmowi);
		p.add(druzyny_s_firmowi);
		p.add(mecze_plan);
		p.add(mecze_roz);

		d.add(d_dzieci);
		d.add(d_trenerzy);
		d.add(d_opiekunowie);
		d.add(d_druzyny);
		d.add(d_treningi);
		d.add(d_s_prywatni);
		d.add(d_druzyny_s_prywatni);
		d.add(d_s_firmowi);
		d.add(d_druzyny_s_firmowi);
		d.add(d_mecze_plan);

		//wyswietlanie wszystkich tabel
		wyswietlTabeleDzieci();
		p_dzieci.add(scroll_z_dzieci);

		wyswietlTabeleOpiekunow();
		p_opiekunowie.add(scroll_z_opiekunow);

		wyswietlTabeleTrenerow();
		p_trenerzy.add(scroll_z_trenerami);

		wyswietlTabelePozycji();
		p_pozycje.add(scroll_z_pozycjami);

		wyswietlTabeleDruzyn();
		p_druzyny.add(scroll_z_druzynami);

		wyswietlTabeleTreningow();
		p_treningi.add(scroll_z_treningami);

		wyswietlTebeleSponsorowPrywatnych();
		p_s_prywatni.add(scroll_z_s_prywatnymi);

		wyswietlTabeleLaczacaDruzynySPrywatnych();
		p_druzyny_s_prywatni.add(scroll_z_druzynami_z_prywatnymi);

		wyswietlTabeleSponsorowFirmowych();
		p_s_firmowi.add(scroll_z_s_firmowymi);

		wyswietlTabeleLaczacaDruzynySFirmowych();
		p_druzyny_s_firmowi.add(scroll_z_druzynami_s_firmowymi);

		wyswietlTabeleMeczyRozegranych();
		p_mecze_roz.add(scroll_z_meczmi_rozegranymi);

		wyswietlTabeleMeczyPlanowanych();
		p_mecze_plan.add(scroll_z_meczmi_planowanymi);

		wyswietlWidokStatystykaMeczy();
		p_statystyka_mecze.add(scroll_z_statystyka_meczy);

		wyswietlWidokZHarmonogramemDruzyn();
		p_harmonogram_druzyn.add(scroll_z_harmonogramem_druzyn);

		wyswietlWidokZHarmonogramemTrenerow();
		p_harmonogram_trenerow.add(scroll_z_harmonogramem_trenerow);

		wyswietlWidokZHarmonogramemSal();
		p_harmonogram_sal.add(scroll_z_harmonogramem_sal);

		wyswietlWidokDruzynyIloscSponsorow();
		p_druzyny_ilosc_sponsorow.add(scroll_z_druzyny_ilosc_sponsorow);

		wyswietlWidokZObciazeniemTrenerow();
		p_obciazenie_trenerow.add(scroll_z_obciazeniem_trenerow);

		wyswietlWidokZPojemnosciaDruzyn();
		p_pojemnosc_druzyn.add(scroll_z_pojemnoscia_druzyn);

		//zakladka_usuwanie.add(panelDoUsuwania);

		panelTabelowy.add("Dodawanie", p);
		panelTabelowy.add("Usuwanie", d);
		panelTabelowy.add("Tabele", zakladka_tabele);
		panelTabelowy.add("Widoki", zakladka_widoki);

		zakladka_tabele.addTab("Dzieci", p_dzieci);
		zakladka_tabele.addTab("Opiekunowie", p_opiekunowie);
		zakladka_tabele.addTab("Trenrzy", p_trenerzy);
		zakladka_tabele.addTab("Pozycje", p_pozycje);
		zakladka_tabele.addTab("Drużyny", p_druzyny);
		zakladka_tabele.addTab("Treningi", p_treningi);
		zakladka_tabele.addTab("Spons. prywatni", p_s_prywatni);
		zakladka_tabele.addTab("Drużyny-Spons. prywatni", p_druzyny_s_prywatni);
		zakladka_tabele.addTab("Spons. firmowi", p_s_firmowi);
		zakladka_tabele.addTab("Drużyny-Spons. firmowi", p_druzyny_s_firmowi);
		zakladka_tabele.addTab("Mecze planowane", p_mecze_plan);
		zakladka_tabele.addTab("Mecze rozegrane", p_mecze_roz);

		zakladka_widoki.add("Statystyka meczy", p_statystyka_mecze);
		zakladka_widoki.add("Harmonogram druzyn", p_harmonogram_druzyn);
		zakladka_widoki.add("Harmonogram trenerów", p_harmonogram_trenerow);
		zakladka_widoki.add("Harmonogram sal", p_harmonogram_sal);
		zakladka_widoki.add("Obciażenie trenerów", p_obciazenie_trenerow);
		zakladka_widoki.add("Ilość sponsorów dla druzyn", p_druzyny_ilosc_sponsorow);
		zakladka_widoki.add("Pojemność druzyn", p_pojemnosc_druzyn);

		add(panelTabelowy);


		setVisible(true);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args){
		new okno();

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private class eventKlikniecia implements ActionListener{
		//funkcja obslugujaca wszystkie klikniecia przyciskow
		public void actionPerformed(ActionEvent e){

			if(e.getSource() == insert_dzieci){
				try{
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.dzieci VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
            		ps.setString(1, dzieci_imie.getText());
            		ps.setString(2, dzieci_nazwisko.getText());
            		ps.setInt(3, Integer.parseInt(dzieci_wiek.getText()));
            		ps.setDate(4, java.sql.Date.valueOf(dzieci_data.getText()));
            		ps.setInt(5, Integer.parseInt(dzieci_pozycja.getText()));
            		ps.setInt(6, Integer.parseInt(dzieci_druzyna.getText()));
            		ps.setInt(7, Integer.parseInt(dzieci_opiekun.getText()));

	            	if(ps.executeUpdate()!=0){       	
	            		model_dzieci.getDataVector().removeAllElements();
						model_dzieci.fireTableDataChanged();
		           		wyswietlTabeleDzieci();
		           		dzieci_imie.setText("");
		            	dzieci_nazwisko.setText("");
		            	dzieci_wiek.setText("");
		             	dzieci_data.setText("");
		             	dzieci_pozycja.setText("");
		             	dzieci_druzyna.setText("");
		             	dzieci_opiekun.setText("");
		             	aktualizuj();
		            }
	             	
             	}
             	catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem dziecka " + message);
				}
			}

            if(e.getSource() == delete_dziecko){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.dzieci WHERE id_dziecka=";
					q += Integer.parseInt(id_dziecka_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_dzieci.getDataVector().removeAllElements();
						model_dzieci.fireTableDataChanged();
	             		wyswietlTabeleDzieci();
	             		id_dziecka_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem dziecka " + message);
				}
        	}

			if(e.getSource() == insert_opiekunowie){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.opiekunowie VALUES(DEFAULT, ?, ?, ?, ?, ?)");
            		ps.setString(1, opiekunowie_imie.getText());
            		ps.setString(2, opiekunowie_nazwisko.getText());
            		ps.setString(3, opiekunowie_telefon.getText());
            		ps.setString(4, opiekunowie_dowod.getText());
            		ps.setString(5, opiekunowie_adres.getText());
            		if(ps.executeUpdate()!=0){
	            		model_opiekunowie.getDataVector().removeAllElements();
						model_opiekunowie.fireTableDataChanged();
	            		wyswietlTabeleOpiekunow();
	             		opiekunowie_imie.setText("");
	             		opiekunowie_nazwisko.setText("");
	             		opiekunowie_telefon.setText("");
	             		opiekunowie_dowod.setText("");
	             		opiekunowie_adres.setText("");
	             		aktualizuj();
             		}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem opiekuna " + message);
				}
			}

			if(e.getSource() == delete_opiekun){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.opiekunowie WHERE id_opiekuna=";
					q += Integer.parseInt(id_opiekuna_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_opiekunowie.getDataVector().removeAllElements();
						model_opiekunowie.fireTableDataChanged();
	             		wyswietlTabeleOpiekunow();
	             		id_opiekuna_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem opiekuna " + message);
				}
            
            
        	}

			if(e.getSource() == insert_druzyny){	
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.druzyny VALUES(DEFAULT, ?, ?, ?, ?)");
            		ps.setString(1, druzyny_nazwa.getText());
            		ps.setInt(2, Integer.parseInt(druzyny_trener.getText()));
            		ps.setInt(3, Integer.parseInt(druzyny_min_wiek.getText()));
            		ps.setInt(4, Integer.parseInt(druzyny_max_wiek.getText()));
            		if(ps.executeUpdate()!=0){
	            		model_druzyny.getDataVector().removeAllElements();
						model_druzyny.fireTableDataChanged();
	            		wyswietlTabeleDruzyn();
	            		druzyny_nazwa.setText("");
	            		druzyny_trener.setText("");
	            		druzyny_min_wiek.setText("");
	            		druzyny_max_wiek.setText("");
	            		aktualizuj();
	            	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem druzyny " + message);
				}
			}

          if(e.getSource() == delete_druzyna){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.druzyny WHERE id_druzyny=";
					q += Integer.parseInt(id_druzyny_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_druzyny.getDataVector().removeAllElements();
						model_druzyny.fireTableDataChanged();
	             		wyswietlTabeleDruzyn();
	             		id_druzyny_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem drużyny " + message);
				}
            
            
        	}

			if(e.getSource() == insert_s_prywatni){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.sponsorzyPrywatni VALUES(DEFAULT, ?, ?, ?)");
            		ps.setString(1, s_prywatni_imie.getText());
            		ps.setString(2, s_prywatni_nazwisko.getText());
            		ps.setString(3, s_prywatni_adres.getText());
            		if(ps.executeUpdate()!=0){
	            		model_s_prywatni.getDataVector().removeAllElements();
						model_s_prywatni.fireTableDataChanged();
	             		wyswietlTebeleSponsorowPrywatnych();
	             		s_prywatni_imie.setText("");
	             		s_prywatni_nazwisko.setText("");
	             		s_prywatni_adres.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem sponsora prywatnego " + message);
				}
			}

			if(e.getSource() == delete_s_prywatny){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.sponsorzyPrywatni WHERE id_s_prywatnego=";
					q += Integer.parseInt(id_s_prywatnego_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_s_prywatni.getDataVector().removeAllElements();
						model_s_prywatni.fireTableDataChanged();
	             		wyswietlTebeleSponsorowPrywatnych();
	             		id_s_prywatnego_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem sponsora prywatnego " + message);
				}
            
            
        	}

			if(e.getSource() == insert_s_firmowi){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.sponsorzyFirmowi VALUES(DEFAULT, ?, ?)");
            		ps.setString(1, s_firmowi_nazwa.getText());
            		ps.setString(2, s_firmowi_adres.getText());
            		if(ps.executeUpdate()!=0){
	            		model_s_firmowi.getDataVector().removeAllElements();
						model_s_firmowi.fireTableDataChanged();
	             		wyswietlTabeleSponsorowFirmowych();
	             		s_firmowi_nazwa.setText("");
	             		s_firmowi_adres.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem sponsora firmowego " + message);
				}
			}

			if(e.getSource() == delete_s_firmowy){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.sponsorzyfirmowi WHERE id_s_firmowgo=";
					q += Integer.parseInt(id_s_firmowego_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_s_firmowi.getDataVector().removeAllElements();
						model_s_firmowi.fireTableDataChanged();
	             		wyswietlTabeleSponsorowFirmowych();
	             		id_s_firmowego_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem sponsora firmowego " + message);
				}
            
            
        	}

			if(e.getSource() == insert_druzyny_s_prywatni){	
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.druzynaSponsorPrywatny VALUES(?, ?)");
            		ps.setInt(1, Integer.parseInt(druzyny_s_prywatni_druzyna.getText()));
            		ps.setInt(2, Integer.parseInt(druzyny_s_prywatni_sponsor.getText()));
            		if(ps.executeUpdate()!=0){
	            		model_druzyny_s_prywatni.getDataVector().removeAllElements();
						model_druzyny_s_prywatni.fireTableDataChanged();
	            		wyswietlTabeleLaczacaDruzynySPrywatnych();
	            		druzyny_s_prywatni_druzyna.setText("");
	            		druzyny_s_prywatni_sponsor.setText("");
	            		aktualizuj();
	            	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem druzyna-sponsor prywatny " + message);
				}
			}

			if(e.getSource() == delete_druzyna_s_prywatny){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.druzynaSponsorPrywatny WHERE id_druzyny=";
					q += Integer.parseInt(id_lacz_druzyna_p_do_usuniecia.getText());
					q += " AND id_s_prywatnego=";
					q += Integer.parseInt(id_lacz_s_prywatny_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_druzyny_s_prywatni.getDataVector().removeAllElements();
						model_druzyny_s_prywatni.fireTableDataChanged();
	             		wyswietlTabeleLaczacaDruzynySPrywatnych();
	             		id_lacz_druzyna_p_do_usuniecia.setText("");
	             		id_lacz_s_prywatny_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem w tabeli laczacej druzyne i sponsora prywatnego " + message);
				}
            
            
        	}

			if(e.getSource() == insert_druzyny_s_firmowi){	
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.druzynaSponsorFirmowy VALUES(?, ?)");
            		ps.setInt(1, Integer.parseInt(druzyny_s_firmowi_druzyna.getText()));
            		ps.setInt(2, Integer.parseInt(druzyny_s_firmowi_sponsor.getText()));
            		if(ps.executeUpdate()!=0){
	            		model_druzyny_s_firmowi.getDataVector().removeAllElements();
						model_druzyny_s_firmowi.fireTableDataChanged();
	            		wyswietlTabeleLaczacaDruzynySFirmowych();
	            		druzyny_s_firmowi_druzyna.setText("");
	            		druzyny_s_firmowi_sponsor.setText("");
	            		aktualizuj();
	            	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem druzyna-sponsor firmowy " + message);
				}
			}

			if(e.getSource() == delete_druzyna_s_firmowy){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.druzynaSponsorFirmowy WHERE id_druzyny=";
					q += Integer.parseInt(id_lacz_druzyna_f_do_usuniecia.getText());
					q += " AND id_s_firmowgo=";
					q += Integer.parseInt(id_lacz_s_firmowy_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_druzyny_s_firmowi.getDataVector().removeAllElements();
						model_druzyny_s_firmowi.fireTableDataChanged();
	             		wyswietlTabeleLaczacaDruzynySFirmowych();
	             		id_lacz_druzyna_f_do_usuniecia.setText("");
	             		id_lacz_s_firmowy_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem w tabeli laczacej druzyne i sponsora firmowego " + message);
				}
			}
            

			if(e.getSource() == insert_trenerzy){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.trenerzy VALUES(DEFAULT, ?, ?, ?, ?, ?)");
            		ps.setString(1, trenerzy_imie.getText());
            		ps.setString(2, trenerzy_nazwisko.getText());
            		ps.setString(3, trenerzy_telefon.getText());
            		ps.setString(4, trenerzy_dowod.getText());
            		ps.setString(5, trenerzy_adres.getText());
            		if(ps.executeUpdate()!=0){
	            		model_trenerzy.getDataVector().removeAllElements();
						model_trenerzy.fireTableDataChanged();
	             		wyswietlTabeleTrenerow();
	             		trenerzy_imie.setText("");
	             		trenerzy_nazwisko.setText("");
	             		trenerzy_telefon.setText("");
	             		trenerzy_dowod.setText("");
	             		trenerzy_adres.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem trenera " + message);
				}
			}

			if(e.getSource() == delete_trener){
				try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.trenerzy WHERE id_trenera=";
					q += Integer.parseInt(id_trenera_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_trenerzy.getDataVector().removeAllElements();
						model_trenerzy.fireTableDataChanged();
	             		wyswietlTabeleTrenerow();
	             		id_trenera_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem trenera " + message);
				}
			}


			if(e.getSource() == insert_mecze_roz){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.meczeRozegrane VALUES(DEFAULT, ?, ?, ?, ?, ?)");
            		ps.setDate(1, java.sql.Date.valueOf(mecze_roz_data.getText()));
            		ps.setInt(2, Integer.parseInt(mecze_roz_punkty_druzyny.getText()));
            		ps.setInt(3, Integer.parseInt(mecze_roz_punkty_przeciwnika.getText()));
            		ps.setString(4, mecze_roz_przeciwnik.getText());
            		ps.setInt(5, Integer.parseInt(mecze_roz_druzyna.getText()));
            		if(ps.executeUpdate()!=0){
	            		model_mecze_rozegrane.getDataVector().removeAllElements();
						model_mecze_rozegrane.fireTableDataChanged();
	            		wyswietlTabeleMeczyRozegranych();
	            		mecze_roz_data.setText("");
	            		mecze_roz_punkty_druzyny.setText("");
	            		mecze_roz_punkty_przeciwnika.setText("");
	            		mecze_roz_przeciwnik.setText("");
	            		mecze_roz_druzyna.setText("");
	            		aktualizuj();
	            	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem meczu rozegranego " + message);
				}
			}

			if(e.getSource() == insert_mecze_plan){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.meczePlanowane VALUES(DEFAULT, ?, ?, ?, ?)");
            		ps.setDate(1, java.sql.Date.valueOf(mecze_plan_data.getText()));
            		ps.setString(2, mecze_plan_miejsce.getText());
            		ps.setString(3, mecze_plan_przeciwnik.getText());
            		ps.setInt(4, Integer.parseInt(mecze_plan_druzyna.getText()));
            		if(ps.executeUpdate()!=0){
	            		model_mecze_planowane.getDataVector().removeAllElements();
						model_mecze_planowane.fireTableDataChanged();
	             		wyswietlTabeleMeczyPlanowanych();
	             		mecze_plan_data.setText("");
	             		mecze_plan_miejsce.setText("");
	             		mecze_plan_przeciwnik.setText("");
	             		mecze_plan_druzyna.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem meczu planownaego " + message);
				}
			}

			if(e.getSource() == insert_treningi){
				
        		try{  			
            		PreparedStatement ps = con.prepareStatement("INSERT INTO projekt.treningi VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)");
            		ps.setString(1, treningi_dzien.getText());
            		ps.setTime(2, java.sql.Time.valueOf(treningi_godzina.getText()));
            		ps.setFloat(3, Float.parseFloat(treningi_czas.getText()));
            		ps.setInt(4, Integer.parseInt(treningi_sala.getText()));
            		ps.setInt(5, Integer.parseInt(treningi_trener.getText()));
            		ps.setInt(6, Integer.parseInt(treningi_druzyna.getText()));
            		if(ps.executeUpdate()!=0){
	            		model_treningi.getDataVector().removeAllElements();
						model_treningi.fireTableDataChanged();
	            		wyswietlTabeleMeczyPlanowanych();
	             		treningi_dzien.setText("");
	             		treningi_godzina.setText("");
	             		treningi_czas.setText("");
	             		treningi_sala.setText("");
	             		treningi_trener.setText("");
	             		treningi_druzyna.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){					
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(p, message);
            		System.err.println("Problem z dodaniem treningu " + message);
				}
			}

			if(e.getSource() == delete_trening){
				
        		try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.treningi WHERE id_treningu=";
					q += Integer.parseInt(id_treningu_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_treningi.getDataVector().removeAllElements();
						model_treningi.fireTableDataChanged();
	             		wyswietlTabeleTreningow();
	             		id_treningu_do_usuniecia.setText("");
	             		aktualizuj();
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem meczu planownego " + message);
				}
			}

			if(e.getSource() == delete_mecz){
				
        		try{  			
            		PreparedStatement ps;  
            		String q = "DELETE FROM projekt.meczeplanowane WHERE id_m_planowanego=";
					q += Integer.parseInt(id_meczu_do_usuniecia.getText());
					q += ";";
					ps = con.prepareStatement(q);
            		if(ps.executeUpdate()!=0){
	            		model_mecze_planowane.getDataVector().removeAllElements();
						model_mecze_planowane.fireTableDataChanged();
	             		wyswietlTabeleMeczyPlanowanych();
	             		id_meczu_do_usuniecia.setText("");
	             		aktualizuj();
						JOptionPane.showMessageDialog(d, "Dodaj usunięty macz do tabeli meczy rozegranych");
	             	}
        		}
        		catch(Exception ex){
        			String message = ex.getMessage();
            		JOptionPane.showMessageDialog(d, message);
            		System.err.println("Problem z usunięciem meczu planownego " + message);
				}
			}
		}
	}

	//ponizej znajduja się funkcje do wypisywania wszystkich widokow i tabel
	void wyswietlTabeleDzieci(){
		try{
			Statement s = con.createStatement();
			String select_dzieci = "SELECT * FROM projekt.dzieci;";
			wiersze = s.executeQuery(select_dzieci);
			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getString(3), wiersze.getInt(4), wiersze.getDate(5), wiersze.getInt(6), wiersze.getInt(7), wiersze.getInt(8)};
				model_dzieci.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli dzieci");
		}

		tabela_z_dziecmi = new JTable(model_dzieci);
		tabela_z_dziecmi.setPreferredScrollableViewportSize(new Dimension(1000,600));
		scroll_z_dzieci = new JScrollPane(tabela_z_dziecmi);
	}

	void wyswietlTabeleOpiekunow(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.opiekunowie;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getString(3), wiersze.getString(4), wiersze.getString(5), wiersze.getString(6)};
				model_opiekunowie.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli opiekunowie");
		}

		tabela_z_opiekunami = new JTable(model_opiekunowie);
		tabela_z_opiekunami.setPreferredScrollableViewportSize(new Dimension(1000,600));

		scroll_z_opiekunow = new JScrollPane(tabela_z_opiekunami);
	}

	void wyswietlTabeleTrenerow(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.trenerzy;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getString(3), wiersze.getString(4), wiersze.getString(5), wiersze.getString(6)};
				model_trenerzy.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli trenerzy");
		}

		tabela_z_trenerami = new JTable(model_trenerzy);		
		tabela_z_trenerami.setPreferredScrollableViewportSize(new Dimension(1200, 600));

		scroll_z_trenerami = new JScrollPane(tabela_z_trenerami);
	}

	void wyswietlTabelePozycji(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.pozycje;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2)};
				model_pozycje.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli pozycje");
		}

		tabela_z_pozycjami = new JTable(model_pozycje);
		tabela_z_pozycjami.setPreferredScrollableViewportSize(new Dimension(400, 600));

		scroll_z_pozycjami = new JScrollPane(tabela_z_pozycjami);
	}

	void wyswietlTabeleDruzyn(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.druzyny;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getInt(3), wiersze.getInt(4), wiersze.getInt(5)};
				model_druzyny.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli druzyny");
		}

		tabela_z_druzynami = new JTable(model_druzyny);
		tabela_z_druzynami.setPreferredScrollableViewportSize(new Dimension(1000, 600));

		scroll_z_druzynami = new JScrollPane(tabela_z_druzynami);
	}

	void wyswietlTabeleTreningow(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.treningi;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getTime(3), wiersze.getFloat(4), wiersze.getInt(5), wiersze.getInt(6), wiersze.getInt(7)};
				model_treningi.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli treningi");
		}

		tabela_z_treningami = new JTable(model_treningi);
		tabela_z_treningami.setPreferredScrollableViewportSize(new Dimension(1300, 600));

		scroll_z_treningami = new JScrollPane(tabela_z_treningami);
	}

	void wyswietlTebeleSponsorowPrywatnych(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.sponsorzyPrywatni;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getString(3), wiersze.getString(4)};
				model_s_prywatni.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli Sponsorzy prywatni");
		}

		tabela_z_s_prywatnymi = new JTable(model_s_prywatni);
		tabela_z_s_prywatnymi.setPreferredScrollableViewportSize(new Dimension(800, 600));

		scroll_z_s_prywatnymi = new JScrollPane(tabela_z_s_prywatnymi);
	}

	void wyswietlTabeleLaczacaDruzynySPrywatnych(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.druzynaSponsorPrywatny;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getInt(2)};
				model_druzyny_s_prywatni.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli laczacej Druzyny i Sponsorow prywatnych");
		}

		tabela_z_druzynami_s_prywatnymi = new JTable(model_druzyny_s_prywatni);
		tabela_z_druzynami_s_prywatnymi.setPreferredScrollableViewportSize(new Dimension(400, 600));

		scroll_z_druzynami_z_prywatnymi = new JScrollPane(tabela_z_druzynami_s_prywatnymi);
	}

	void wyswietlTabeleSponsorowFirmowych(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.sponsorzyFirmowi;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getString(3)};
				model_s_firmowi.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli Sponsorzy firmowi");
		}

		tabela_z_s_firmowymi = new JTable(model_s_firmowi);
		tabela_z_s_firmowymi.setPreferredScrollableViewportSize(new Dimension(600, 600));

		scroll_z_s_firmowymi = new JScrollPane(tabela_z_s_firmowymi);
	}

	void wyswietlTabeleLaczacaDruzynySFirmowych(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.druzynaSponsorFirmowy;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getInt(2)};
				model_druzyny_s_firmowi.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli laczacej Druzyny i Sponsorow firmowych");
		}

		tabela_z_druzynami_s_firmowymi = new JTable(model_druzyny_s_firmowi);
		tabela_z_druzynami_s_firmowymi.setPreferredScrollableViewportSize(new Dimension(400, 600));

		scroll_z_druzynami_s_firmowymi = new JScrollPane(tabela_z_druzynami_s_firmowymi);
	}


	void wyswietlTabeleMeczyRozegranych(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.meczeRozegrane;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getDate(2), wiersze.getInt(3), wiersze.getInt(4), wiersze.getString(5), wiersze.getInt(6)};
				model_mecze_rozegrane.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli mecze rozegrane");
		}

		tabela_z_meczami_rozegranymi = new JTable(model_mecze_rozegrane);
		tabela_z_meczami_rozegranymi.setPreferredScrollableViewportSize(new Dimension(1200, 600));

		scroll_z_meczmi_rozegranymi = new JScrollPane(tabela_z_meczami_rozegranymi);
	}

	void wyswietlTabeleMeczyPlanowanych(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.meczePlanowane;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getDate(2), wiersze.getString(3), wiersze.getString(4), wiersze.getInt(5)};
				model_mecze_planowane.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w tabeli mecze planowane");
		}

		tabela_z_meczami_planowanymi = new JTable(model_mecze_planowane);
		tabela_z_meczami_planowanymi.setPreferredScrollableViewportSize(new Dimension(1000, 600));

		scroll_z_meczmi_planowanymi = new JScrollPane(tabela_z_meczami_planowanymi);
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	void wyswietlWidokStatystykaMeczy(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.statystyka_mecze;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getString(1), wiersze.getInt(2), wiersze.getInt(3), wiersze.getInt(4), wiersze.getInt(5), wiersze.getInt(6)};
				model_z_statystyka_meczy.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku statystyka meczy");
		}

		JTable widok_z_statystyka_meczy = new JTable(model_z_statystyka_meczy);
		widok_z_statystyka_meczy.setPreferredScrollableViewportSize(new Dimension(1200, 600));

		scroll_z_statystyka_meczy = new JScrollPane(widok_z_statystyka_meczy);
	}

	void wyswietlWidokZHarmonogramemDruzyn(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.harmonogram_druzyn;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getString(1), wiersze.getString(2), wiersze.getTime(3), wiersze.getTime(4), wiersze.getInt(5)};
				model_harmonogram_druzyn.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku harmonogram druzyn");
		}

		JTable widok_z_harmonogramem_druzyn = new JTable(model_harmonogram_druzyn);
		widok_z_harmonogramem_druzyn.setPreferredScrollableViewportSize(new Dimension(1000, 600));

		scroll_z_harmonogramem_druzyn = new JScrollPane(widok_z_harmonogramem_druzyn);
	}

	void wyswietlWidokZHarmonogramemTrenerow(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.harmonogram_trenerow;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getString(1), wiersze.getString(2), wiersze.getString(3), wiersze.getTime(4), wiersze.getTime(5), wiersze.getInt(6)};
				model_harmonogram_trenerow.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku harmonogram trenerow");
		}

		JTable widok_z_harmonogramem_trenerow = new JTable(model_harmonogram_trenerow);
		widok_z_harmonogramem_trenerow.setPreferredScrollableViewportSize(new Dimension(1200, 600));

		scroll_z_harmonogramem_trenerow = new JScrollPane(widok_z_harmonogramem_trenerow);
	}

	void wyswietlWidokZHarmonogramemSal(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.harmonogram_sal;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getInt(1), wiersze.getString(2), wiersze.getTime(3), wiersze.getTime(4)};
				model_harmonogram_sal.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku harmonogram sal");
		}

		JTable widok_z_harmonogramem_sal = new JTable(model_harmonogram_sal);
		widok_z_harmonogramem_sal.setPreferredScrollableViewportSize(new Dimension(800, 600));

		scroll_z_harmonogramem_sal = new JScrollPane(widok_z_harmonogramem_sal);
	}

	void wyswietlWidokDruzynyIloscSponsorow(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.druzyny_ilosc_sponsorow;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getString(1), wiersze.getInt(2), wiersze.getInt(3), wiersze.getInt(4)};
				model_z_druzyny_ilosc_sponsorow.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku druzyny ilosc sponsorow");
		}

		JTable widok_z_druzyny_ilosc_sponsorow = new JTable(model_z_druzyny_ilosc_sponsorow);
		widok_z_druzyny_ilosc_sponsorow.setPreferredScrollableViewportSize(new Dimension(1000, 600));

		scroll_z_druzyny_ilosc_sponsorow = new JScrollPane(widok_z_druzyny_ilosc_sponsorow);
	}

	void wyswietlWidokZObciazeniemTrenerow(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.obciazenie_trenerow;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getString(1), wiersze.getString(2), wiersze.getFloat(3), wiersze.getInt(4)};
				model_z_obciazeniem_trenerow.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku obciazenie trenerow");
		}

		JTable widok_z_obciazeniem_trenerow = new JTable(model_z_obciazeniem_trenerow);
		widok_z_obciazeniem_trenerow.setPreferredScrollableViewportSize(new Dimension(800, 600));

		scroll_z_obciazeniem_trenerow = new JScrollPane(widok_z_obciazeniem_trenerow);
	}

	void wyswietlWidokZPojemnosciaDruzyn(){
		try{
			Statement s = con.createStatement();
			String select = "SELECT * FROM projekt.pojemnosc_druzyn;";
			wiersze = s.executeQuery(select);

			Object[] tempWiersz;

			while(wiersze.next()){
				tempWiersz = new Object[]{wiersze.getString(1), wiersze.getInt(2)};
				model_pojemnosc_druzyn.addRow(tempWiersz);
			}
		}
		catch(SQLException ex){
			System.out.println("Blad w widoku pojemnosc druzyn");
		}

		JTable widok_z_pojemnosc_druzyn = new JTable(model_pojemnosc_druzyn);
		widok_z_pojemnosc_druzyn.setPreferredScrollableViewportSize(new Dimension(500, 600));

		scroll_z_pojemnoscia_druzyn = new JScrollPane(widok_z_pojemnosc_druzyn);
	}

	//punkcja aktualizujaca wszystkie widoki po jakiejkolwiek zminianie w bazie
	void aktualizuj(){
		model_pojemnosc_druzyn.getDataVector().removeAllElements();
		model_pojemnosc_druzyn.fireTableDataChanged();
		model_z_obciazeniem_trenerow.getDataVector().removeAllElements();
		model_z_obciazeniem_trenerow.fireTableDataChanged();
		model_z_druzyny_ilosc_sponsorow.getDataVector().removeAllElements();
		model_z_druzyny_ilosc_sponsorow.fireTableDataChanged();
		model_harmonogram_sal.getDataVector().removeAllElements();
		model_harmonogram_sal.fireTableDataChanged();
		model_harmonogram_trenerow.getDataVector().removeAllElements();
		model_harmonogram_trenerow.fireTableDataChanged();
		model_harmonogram_druzyn.getDataVector().removeAllElements();
		model_harmonogram_druzyn.fireTableDataChanged();
		model_z_statystyka_meczy.getDataVector().removeAllElements();
		model_z_statystyka_meczy.fireTableDataChanged();
		wyswietlWidokZPojemnosciaDruzyn();
		wyswietlWidokZObciazeniemTrenerow();
		wyswietlWidokDruzynyIloscSponsorow();
		wyswietlWidokZHarmonogramemSal();
		wyswietlWidokZHarmonogramemTrenerow();
		wyswietlWidokZHarmonogramemDruzyn();
		wyswietlWidokStatystykaMeczy();
	}
}