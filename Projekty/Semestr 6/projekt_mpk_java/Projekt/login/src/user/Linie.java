package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DropMode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import login.Polaczenie;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import javax.swing.JButton;

public class Linie extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Linie frame = new Linie(string);
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
	
	private static String dzien(int format) {
		 switch (format){
		 case 1:	return "Poniedzia³ek"; 
		 case 2:	return "Wtorek"; 
		 case 3:	return "Œroda"; 
		 case 4:	return "Czwartek"; 
		 case 5:	return "Pi¹tek"; 
		 case 6:	return "Sobota"; 
		 case 7:	return "Niedziela"; 
		 default:	return "";
		 }
	}
	
	public static void kalendarz (JLabel lbl_czas){
		DateFormat dzien = new SimpleDateFormat("dd.MM.yyyy");
		DateFormat godzina = new SimpleDateFormat("HH:mm");
		DateFormat today = new SimpleDateFormat("u");
		Date dateobj = new Date();
		String dzienn = dzien(Integer.parseInt(today.format(dateobj)));
		lbl_czas.setText(dzienn+", "+dzien.format(dateobj)+", "+godzina.format(dateobj));
		
	}
	
	public static void wypis_list(int nr, JList<String> list_tram, JList<String> list_autob){
		String tram_nr="", aut_nr="";
		//System.out.println("przyjêto: "+comboBox.getSelectedItem());
		
		if (nr==0) {
			System.out.println("Dzienne");
			tram_nr="0 and 59";
			aut_nr="100 and 599";
			
		} else {
			System.out.println("Nocne");
			tram_nr="60 and 99";
			aut_nr="600 and 999";
		}
		
		DefaultListModel<String> tr = new DefaultListModel();
		DefaultListModel<String> aut = new DefaultListModel();
		try{
			//tramwaje 
			String query="select * from linie where typ='tramwaj' and id between "+tram_nr+" order by id;";
			System.out.println(query);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet res = pst.executeQuery();
			
			//autobusy
			
		
			while (res.next()) {
				String m=res.getString("id");
				tr.addElement(m);
			}
			
			//query.close();
			String query2="select * from linie where typ='autobus'and id between "+aut_nr+"; ";
			System.out.println(query2);
			PreparedStatement pst2 = conn.prepareStatement(query2);
			ResultSet res2 = pst2.executeQuery();
			while (res2.next()) {
				String m2=res2.getString("id");
				aut.addElement(m2);
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}finally{
			list_tram.setModel(tr);
			list_autob.setModel(aut);
			
		} 
	}
	
	private static String string;
	static Connection conn = null;
	private JList list_tram;
	private JList list_autob;
	private int idl, idp;
	private JLabel lblNewLabel;
	private JButton btn_kier1, btn_kier2;
	
	
	
	
	public Linie(String string) {
		conn=Polaczenie.dbConn();
		
		setBounds(100, 100, 650, 600);
		setResizable(false);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		
		Image img3 = new ImageIcon(this.getClass().getResource("/tlo.png")).getImage();
		
		Image img2 = new ImageIcon(this.getClass().getResource("/ngt8.png")).getImage();
		contentPane.setLayout(null);
		
		JScrollPane scrollPane_tram = new JScrollPane();
		scrollPane_tram.setBounds(102, 190, 166, 170);
		scrollPane_tram.setBorder(null);
		contentPane.add(scrollPane_tram);
		JList<String> list_tram = new JList();
		list_tram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//System.out.println(list_tram.getSelectedValue());
				idl = Integer.parseInt(list_tram.getSelectedValue());
				lblNewLabel.setVisible(true);
				btn_kier1.setVisible(true);
				btn_kier2.setVisible(true);
				try{
					String qquery = "SELECT distinct(liniaId), (select nazwa from przystanki where id=przystanekPoczatkowy) as nazwaa, przystanekPoczatkowy from odjazdy  where liniaId='"+idl+"'";
					PreparedStatement pstt = conn.prepareStatement(qquery);
					ResultSet ress = pstt.executeQuery();
						ress.next();
						btn_kier1.setText(ress.getString("nazwaa"));
						ress.next();
						btn_kier2.setText(ress.getString("nazwaa"));
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		JList<String> list_autob = new JList();
		list_autob.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				idl = Integer.parseInt(list_autob.getSelectedValue());
				lblNewLabel.setVisible(true);
				btn_kier1.setVisible(true);
				btn_kier2.setVisible(true);
				try{
					String qquery = "SELECT distinct(liniaId), (select nazwa from przystanki where id=przystanekPoczatkowy) as nazwaa, przystanekPoczatkowy from odjazdy  where liniaId='"+idl+"'";
					PreparedStatement pstt = conn.prepareStatement(qquery);
					ResultSet ress = pstt.executeQuery();
						ress.next();
						btn_kier1.setText(ress.getString("nazwaa"));
						btn_kier1.equals(ress.getString("przystanekPoczatkowy"));
						ress.next();
						btn_kier2.setText(ress.getString("nazwaa"));
				}catch(Exception ex){
					//JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		scrollPane_tram.setViewportView(list_tram);
		wypis_list(0,list_tram,list_autob);
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wypis_list(comboBox.getSelectedIndex(),list_tram,list_autob);
				
				
				
			}
		});
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"   Linie dzienne", "   Linie nocne"}));
		comboBox.setBounds(102, 144, 381, 38);
		contentPane.add(comboBox);
		
		
		
		JScrollPane scrollPane_aut = new JScrollPane();
		scrollPane_aut.setBounds(317, 190, 166, 176);
		scrollPane_aut.setBorder(null);
		contentPane.add(scrollPane_aut);
		
		//JList list_autob = new JList();
		scrollPane_aut.setViewportView(list_autob);
		
		lblNewLabel = new JLabel("Przystanek pocz¹tkowy: ");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel.setForeground(SystemColor.window);
		lblNewLabel.setBounds(102, 389, 326, 50);
		lblNewLabel.setVisible(false);
		
		btn_kier1 = new JButton("");
		btn_kier1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					/////////////////////linia////////////////idprzystanku///////////////
					Rozklad roz = new Rozklad(idl,btn_kier1.getText(),btn_kier2.getText());
					////////////////////////////////////////////////////
					roz.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btn_kier1.setForeground(new Color(255, 255, 255));
		btn_kier1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_kier1.setBackground(new Color(57, 109, 135));
		btn_kier1.setBorderPainted(false);
		btn_kier1.setBounds(237, 433, 246, 23);
		btn_kier1.setVisible(false);
		btn_kier1.setBorder(null); 
		contentPane.add(btn_kier1);
		
		btn_kier2 = new JButton("");
		btn_kier2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					/////////linia///////////idprzystanku///////////////
					Rozklad roz = new Rozklad(idl,btn_kier2.getText(),btn_kier1.getText());
					////////////////////////////////////////////////////
					roz.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btn_kier2.setForeground(new Color(255, 255, 255));
		btn_kier2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_kier2.setBackground(new Color(57, 109, 135));
		btn_kier2.setBorderPainted(false);
		btn_kier2.setBounds(237, 463, 246, 23);
		btn_kier2.setVisible(false);
		btn_kier2.setBorder(null); 
		contentPane.add(btn_kier2);
		
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");		
		label.setIcon(new ImageIcon(img));
		label.setBounds(10, 1, 120, 111);		
		contentPane.add(label);
		
		JLabel lblCreatedBy = new JLabel("Copyright\u00A9 2015 Tomaszewski, Sokulski, Szydlak, Pytlak");
		lblCreatedBy.setForeground(new Color(255, 255, 255));
		lblCreatedBy.setFont(new Font("Trajan Pro", Font.PLAIN, 11));
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedBy.setBounds(0, 521, 644, 50);
		contentPane.add(lblCreatedBy);
		
		JLabel lbl_stopka = new JLabel("");
		lbl_stopka.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_stopka.setBounds(0, 521, 644, 50);
		lbl_stopka.setIcon(new ImageIcon(img3));
		contentPane.add(lbl_stopka);
		
		JLabel lbl_czas = new JLabel("");
		lbl_czas.setHorizontalAlignment(SwingConstants.RIGHT);
		kalendarz (lbl_czas);
		lbl_czas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_czas.setForeground(new Color(255, 255, 255));
		lbl_czas.setBounds(237, 11, 397, 79);
		contentPane.add(lbl_czas);
				
		JLabel panel = new JLabel();
		panel.setOpaque(false);
		panel.setBounds(0, -3, 644, 121);
		Image img4 = new ImageIcon(this.getClass().getResource("/bg_top.png")).getImage();
		panel.setIcon(new ImageIcon(img4));
		contentPane.add(panel);
		
		JLabel lbl_bg = new JLabel("");
		lbl_bg.setIcon(new ImageIcon(img2));
		lbl_bg.setBounds(0, 1, 644, 570);
		contentPane.add(lbl_bg);
	}
}
