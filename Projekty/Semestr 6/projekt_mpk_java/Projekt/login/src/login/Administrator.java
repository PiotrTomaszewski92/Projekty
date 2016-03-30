package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Administrator extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Administrator frame = new Administrator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public Administrator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 834, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane AdminPanel = new JTabbedPane(JTabbedPane.TOP);
		AdminPanel.setToolTipText("");
		AdminPanel.setBounds(10, 11, 798, 424);
		contentPane.add(AdminPanel);
		
		JPanel info = new JPanel();
		AdminPanel.addTab("Informacje", null, info, null);
		info.setLayout(null);
		
		JLabel lbllpytlakgmailcom = new JLabel("lpytlak27@gmail.com");
		lbllpytlakgmailcom.setBounds(203, 185, 230, 14);
		info.add(lbllpytlakgmailcom);
		
		JLabel lblRsokulskioppl = new JLabel("rsokulski@op.pl");
		lblRsokulskioppl.setBounds(203, 221, 230, 14);
		info.add(lblRsokulskioppl);
		
		JLabel lblSzydwojgmailcom = new JLabel("szydwoj@gmail.com ");
		lblSzydwojgmailcom.setBounds(203, 257, 230, 14);
		info.add(lblSzydwojgmailcom);
		
		JLabel lblPiotrekhagmailcom = new JLabel("piotrekha15@gmail.com");
		lblPiotrekhagmailcom.setBounds(203, 293, 230, 14);
		info.add(lblPiotrekhagmailcom);
		
		JLabel lblMpkAplikacja = new JLabel("MPK APLIKACJA 2015");
		lblMpkAplikacja.setHorizontalAlignment(SwingConstants.CENTER);
		lblMpkAplikacja.setBounds(10, 11, 773, 36);
		lblMpkAplikacja.setFont(new Font("Calibri", Font.BOLD, 29));
		info.add(lblMpkAplikacja);
		
		JLabel lblWersja = new JLabel("Wersja: 1.0");
		lblWersja.setHorizontalAlignment(SwingConstants.CENTER);
		lblWersja.setBounds(10, 58, 773, 46);
		info.add(lblWersja);
		
		JLabel lblTwrcy = new JLabel("Tw\u00F3rcy: ");
		lblTwrcy.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTwrcy.setBounds(10, 134, 138, 46);
		info.add(lblTwrcy);
		
		JLabel lblNewLabel = new JLabel("Pytlak \u0141ukasz\t\t");
		lblNewLabel.setBounds(49, 180, 230, 25);
		info.add(lblNewLabel);
		
		JLabel lblSokulskiRafa = new JLabel("Sokulski Rafa\u0142");
		lblSokulskiRafa.setBounds(49, 216, 230, 25);
		info.add(lblSokulskiRafa);
		
		JLabel lblSzydlakWojcieck = new JLabel("Szydlak Wojcieck");
		lblSzydlakWojcieck.setBounds(49, 252, 230, 25);
		info.add(lblSzydlakWojcieck);
		
		JLabel lblTomaszewskiPiotr = new JLabel("Tomaszewski Piotr");
		lblTomaszewskiPiotr.setBounds(49, 288, 230, 25);
		info.add(lblTomaszewskiPiotr);
		
		JLabel lblInformacjeOWersji = new JLabel("Informacje o wersji");
		lblInformacjeOWersji.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInformacjeOWersji.setBounds(543, 143, 209, 28);
		info.add(lblInformacjeOWersji);
		
		JTextPane txtpnWerscjaEdycja = new JTextPane();
		txtpnWerscjaEdycja.setText("Werscja: 1.0\r\n\r\nEdycja, dodawanie, usuwanie godzin odjazdu dla linii z danych przystank\u00F3w mozliwe tylko w strukturze bazy danych.");
		txtpnWerscjaEdycja.setBounds(543, 180, 209, 151);
		info.add(txtpnWerscjaEdycja);
		
		JPanel dodaj = new JPanel();
		AdminPanel.addTab("Dodaj", null, dodaj, null);
		AdminPanel.setBackgroundAt(1, new Color(255, 102, 0));
		dodaj.setLayout(null);
		
		JLabel lblDodaj = new JLabel("DODAJ");
		lblDodaj.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblDodaj.setBounds(347, 5, 100, 28);
		dodaj.add(lblDodaj);
		
		JButton btn_addPrzystanek = new JButton("Przystanek");
		btn_addPrzystanek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//frame.
				
				try {
					AddPrzystanek przystanek = new AddPrzystanek("add");
					przystanek.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
				
			}
		});
		btn_addPrzystanek.setBounds(10, 44, 773, 69);
		dodaj.add(btn_addPrzystanek);
		
		JButton btn_addBilety = new JButton("Bilety");
		btn_addBilety.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddBilety bilety = new AddBilety("add");
					bilety.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_addBilety.setBounds(10, 204, 773, 69);
		dodaj.add(btn_addBilety);
		
		JButton btn_addLinie = new JButton("Linie");
		btn_addLinie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddLinie linie = new AddLinie("add");
				linie.setVisible(true);
			}
		});
		btn_addLinie.setBounds(10, 124, 773, 69);
		dodaj.add(btn_addLinie);
		
		JButton btn_addOdjazdy = new JButton("Odjazdy");
		btn_addOdjazdy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//AddOdjazdy odjazdy = new AddOdjazdy("add");
				JOptionPane.showMessageDialog(null, "W wersji 1.0 ta opcja jest niedostêpna");
				//odjazdy.setVisible(true);
			}
		});
		btn_addOdjazdy.setBounds(10, 284, 773, 69);
		dodaj.add(btn_addOdjazdy);
		
		JPanel usun = new JPanel();
		AdminPanel.addTab("Usuñ", null, usun, null);
		usun.setLayout(null);
		
		JLabel lblUsu = new JLabel("Usu\u0144");
		lblUsu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblUsu.setBounds(356, 5, 143, 36);
		usun.add(lblUsu);
		
		JButton btn_delPrzystanek = new JButton("Przystanek");
		btn_delPrzystanek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddPrzystanek przystanek = new AddPrzystanek("del");
					przystanek.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_delPrzystanek.setBounds(10, 52, 773, 69);
		usun.add(btn_delPrzystanek);
		
		JButton btn_delLinie = new JButton("Linie");
		btn_delLinie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AddLinie linie = new AddLinie("del");
					linie.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_delLinie.setBounds(10, 132, 773, 69);
		usun.add(btn_delLinie);
		
		JButton btn_delBilety = new JButton("Bilety");
		btn_delBilety.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddBilety bilety = new AddBilety("del");
					bilety.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_delBilety.setBounds(10, 212, 773, 69);
		usun.add(btn_delBilety);
		
		JButton btn_delOdjazdy = new JButton("Odjazdy");
		btn_delOdjazdy.setBounds(10, 292, 773, 69);
		usun.add(btn_delOdjazdy);
		btn_delOdjazdy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//AddOdjazdy odjazdy = new AddOdjazdy("del");
					//odjazdy.setVisible(true);
					JOptionPane.showMessageDialog(null, "W wersji 1.0 ta opcja jest niedostêpna");
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		
		JPanel aktualizuj = new JPanel();
		AdminPanel.addTab("Aktualizuj", null, aktualizuj, null);
		aktualizuj.setLayout(null);
		
		JLabel lblAktualizuj = new JLabel("Aktualizuj");
		lblAktualizuj.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblAktualizuj.setBounds(328, 11, 160, 33);
		aktualizuj.add(lblAktualizuj);
		
		JButton btn_upPrzystabek = new JButton("Przystanek");
		btn_upPrzystabek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddPrzystanek przystanek = new AddPrzystanek("akt");
					przystanek.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_upPrzystabek.setBounds(10, 55, 773, 69);
		aktualizuj.add(btn_upPrzystabek);
		
		JButton btn_upLinie = new JButton("Linie");
		btn_upLinie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AddLinie linie = new AddLinie("akt");
					linie.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_upLinie.setBounds(10, 135, 773, 69);
		aktualizuj.add(btn_upLinie);
		
		JButton btn_upBilety = new JButton("Bilety");
		btn_upBilety.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddBilety bilety = new AddBilety("akt");
					bilety.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_upBilety.setBounds(10, 215, 773, 69);
		aktualizuj.add(btn_upBilety);
		
		JButton btn_upOdjazdy = new JButton("Odjazdy");
		btn_upOdjazdy.setBounds(10, 295, 773, 69);
		aktualizuj.add(btn_upOdjazdy);
		btn_upOdjazdy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//AddOdjazdy odjazdy = new AddOdjazdy("akt");
					//odjazdy.setVisible(true);
					JOptionPane.showMessageDialog(null, "W wersji 1.0 ta opcja jest niedostêpna");
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
	}
}
