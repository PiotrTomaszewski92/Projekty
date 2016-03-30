package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import login.Polaczenie;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Pol extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pol frame = new Pol(string);
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
	

	public static void addTextAndSelectToTextFieldToReset (JTextField textField, String newDato){
		String datoBuscado="";
		int nroActual=textField.getText().length();
		datoBuscado = newDato.substring(nroActual, newDato.length());
		if (newDato.isEmpty() || datoBuscado.isEmpty())
			return;
		try{
			textField.getDocument().insertString(textField.getCaretPosition(), datoBuscado, null);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		System.out.println("w addTextAndSelectToTextFieldToReset: ");
		textField.select(nroActual, textField.getText().length());
	}
	
	
	public static String getTextoApartirVector(String datoBuscar, String[] veDatos){
		int nroPoscion = getPositionVectorBusSemi(datoBuscar, veDatos);
		if(nroPoscion == -1){
				return datoBuscar;
		}
		System.out.println("w getTextoApartirVector: "+veDatos[nroPoscion]);
		return veDatos[nroPoscion];
		
		
	}
	
	public static int getPositionVectorBusSemi(String datoBuscar, String []veDatos){
		try{
			//veDatos.
			for (int i=0;i<veDatos.length;i++){
				if(datoBuscar.equals(veDatos[i].substring(0 , datoBuscar.length() ))){
				System.out.println("w getPositionVectorBusSemi: "+i);
					return i;}
			}
			
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null,e);
		}
		System.out.println("w getPositionVectorBusSemi: -1");
		return -1;
		
	}
	
	public static void uzupelnienie_tab(String []veDatos){
			try{
				
				String qq = "Select nazwa from przystanki";
				PreparedStatement p = conn.prepareStatement(qq);
				ResultSet re = p.executeQuery();
				int i=0;
				while (re.next()) {
					veDatos[i]=re.getString("nazwa");
					i++;
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,e);
			}
		
	}
	
	public static int ile_elementow(){
		int roz=0;
		try{
			String qq = "Select count(nazwa) as ile from przystanki";
			PreparedStatement p = conn.prepareStatement(qq);
			ResultSet re = p.executeQuery();
			re.next();
			roz=Integer.parseInt(re.getString("ile"));
			
		}
		catch(Exception e){
		}
		System.out.println(roz);
		return roz;
	}
	
	public static void change(int mins, JTextField txt_godz){
		String czas= txt_godz.getText(), napis,minutes=null,hours = null;
		String[] wynik1 = new String[2];
		int min_int=0,hour_int=0, j=1,i=0;
		wynik1=(czas).split(":");
		hour_int=Integer.parseInt(wynik1[0]);
		min_int=Integer.parseInt(wynik1[1]);
		System.out.println(hour_int+" || "+min_int);
		
		if(mins>0){
			  if(min_int>=50){
					i=1;
				}
				
	          minutes = Integer.toString((Integer.parseInt(wynik1[1])+mins )% 60); 
			  min_int=((Integer.parseInt(wynik1[1])+mins ) % 60);
			  
			  
			  
			  hour_int = ((Integer.parseInt(wynik1[0])+i ) % 24);
			  hours = Integer.toString((Integer.parseInt(wynik1[0])+i ) % 24).toString();
			if(hours.length()==1){
				hours = "0" + hour_int;
			}
		}
		
		else if(mins<0){
			  
			  if(min_int<10){
					i=-1;
					minutes = Integer.toString(60-(10-(Integer.parseInt(wynik1[1])))); 
					min_int=(60-(10-Integer.parseInt(wynik1[1])));
				}
				else{
	          minutes = Integer.toString((Integer.parseInt(wynik1[1])+mins )% 60); 
			  min_int=((Integer.parseInt(wynik1[1])+mins ) % 60);
			  }
			  
			  
					hour_int = ((Integer.parseInt(wynik1[0])+i ) % 24);
					hours = Integer.toString((Integer.parseInt(wynik1[0])+i ) % 24);
					if(hours.length() == 1){
						hours = "0" + hour_int;
					}
					
					
		}
		
		if(min_int==0){
			  minutes="00";
		}
		else if(minutes.length()== 1){
	        minutes = "0" + minutes;
	    }
		
		else if(hour_int<=(-1)){
					hour_int = 23;
					hours = Integer.toString(23);
				}
		
		
		txt_godz.setText(hours + ":" + minutes);
	}
	
	private int spr_przystanku(String nazwa){
		int ajdi=0;
		try{
			String qq = "Select count(id) as il from przystanki where nazwa='"+nazwa+"';";
			PreparedStatement p = conn.prepareStatement(qq);
			ResultSet re = p.executeQuery();
			re.next();
			ajdi=Integer.parseInt(re.getString("il"));
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "Yolo0"+e);
		}
		return ajdi;
	}
	
	private static String string;	
	static Connection conn = null;
	private JTextField txt_pocz;
	private JTextField txt_kon;
	private JTextField txt_godz;
	//private static ;
	
	public Pol(String string) {
		conn=Polaczenie.dbConn();
		setBounds(100, 100, 650, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		int ile = ile_elementow();
		
		String[] veDatos = new String[ile];
		
		
		uzupelnienie_tab(veDatos);
		
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image img3 = new ImageIcon(this.getClass().getResource("/tlo.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/ngt8.png")).getImage();
		Image img5 = new ImageIcon(this.getClass().getResource("/bg_white.png")).getImage();
		Image img4 = new ImageIcon(this.getClass().getResource("/bg_top.png")).getImage();
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_prz_pocz = new JLabel("Przystanek pocz\u0105tkowy");
		lbl_prz_pocz.setBounds(84, 190, 139, 14);
		contentPane.add(lbl_prz_pocz);
		
		JLabel lbl_prz_kon = new JLabel("Przystanek ko\u0144cowy");
		lbl_prz_kon.setBounds(84, 260, 139, 14);
		contentPane.add(lbl_prz_kon);
		
		/*for(int i=0;i<veDatos.length;i++){
			System.out.println("veDatos: "+veDatos[i]);
		}*/
		
		
		txt_pocz = new JTextField();
		txt_pocz.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!(arg0.getKeyCode()>=65 && arg0.getKeyCode()<=90 || arg0.getKeyCode()>=96 && arg0.getKeyCode()<=105 || arg0.getKeyCode()==arg0.VK_ENTER)){
					return;
				}
				if (txt_pocz.getText().isEmpty()){
					return;
				}
				if(veDatos.length==0){
					return;}
				addTextAndSelectToTextFieldToReset(txt_pocz, getTextoApartirVector(txt_pocz.getText(),veDatos));
				
			}
		});
		
		txt_pocz.setBounds(84, 210, 200, 26);
		contentPane.add(txt_pocz);
		txt_pocz.setColumns(10);
		
		
		txt_kon = new JTextField();
		txt_kon.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg1) {
				if(!(arg1.getKeyCode()>=65 && arg1.getKeyCode()<=90 || arg1.getKeyCode()>=96 && arg1.getKeyCode()<=105 || arg1.getKeyCode()==arg1.VK_ENTER)){
					return;
				}
				if (txt_kon.getText().isEmpty()){
					return;
				}
				if(veDatos.length==0){
					return;}
				addTextAndSelectToTextFieldToReset(txt_kon, getTextoApartirVector(txt_kon.getText(),veDatos));
			}
		});
		txt_kon.setColumns(10);
		txt_kon.setBounds(84, 280, 200, 26);
		contentPane.add(txt_kon);
		
		
		txt_godz = new JTextField();
		txt_godz.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txt_godz.setHorizontalAlignment(SwingConstants.CENTER);
		DateFormat godzina = new SimpleDateFormat("HH:mm");
		Date dateobj = new Date();
		txt_godz.setText(godzina.format(dateobj));
		txt_godz.setBounds(373, 280, 200, 50);
		contentPane.add(txt_godz);
		txt_godz.setColumns(10);
		
		JButton btnSzukaj = new JButton("Szukaj");
		btnSzukaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int i_p=spr_przystanku(txt_pocz.getText());
					int i_k=spr_przystanku(txt_kon.getText());
					if ((txt_pocz.getText().isEmpty())||(txt_kon.getText().isEmpty())||(txt_godz.getText().isEmpty())){
						JOptionPane.showMessageDialog(null, "Uzupe³nij wszystkie pola");
					}
					else if(txt_pocz.getText().equals(txt_kon.getText())) {
						JOptionPane.showMessageDialog(null, "Przystanek pocz¹tkowy nie mo¿e byæ taki sam jak koñcowy");
					}
					else if((i_p==0)||(i_k==0)) {
						JOptionPane.showMessageDialog(null, "Wprowadzono niepoprawn¹ nazwê przystanku");
					}
					else{
					Show_pol show = new Show_pol(txt_pocz.getText(),txt_kon.getText(),txt_godz.getText());
					show.setVisible(true);}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSzukaj.setForeground(new Color(255, 255, 255));
		btnSzukaj.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSzukaj.setBackground(new Color(57, 109, 135));
		btnSzukaj.setBorderPainted(false);
		btnSzukaj.setBounds(84, 361, 489, 71);
		contentPane.add(btnSzukaj);
		
		JButton btn_l = new JButton("<");
		btn_l.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change(-10,txt_godz);
			}
		});
		btn_l.setForeground(new Color(255, 255, 255));
		btn_l.setFont(new Font("Tahoma", Font.BOLD, 9));
		btn_l.setBackground(new Color(57, 109, 135));
		btn_l.setBorderPainted(false);
		btn_l.setBounds(334, 280, 41, 50);
		contentPane.add(btn_l);
		
		JButton btn_p = new JButton(">");
		btn_p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change(10,txt_godz);
			}
		});
		btn_p.setForeground(new Color(255, 255, 255));
		btn_p.setFont(new Font("Tahoma", Font.BOLD, 9));
		btn_p.setBackground(new Color(57, 109, 135));
		btn_p.setBorderPainted(false);
		btn_p.setBounds(571, 280, 41, 50);
		contentPane.add(btn_p);
		
		JLabel lblGodzinaOdjazdu = new JLabel("Godzina odjazdu");
		lblGodzinaOdjazdu.setBounds(421, 260, 113, 14);
		contentPane.add(lblGodzinaOdjazdu);
		
		JLabel lbl_bgWhite = new JLabel("New label");
		lbl_bgWhite.setBounds(10, 124, 624, 386);
		lbl_bgWhite.setIcon(new ImageIcon(img5));
		contentPane.add(lbl_bgWhite);
		
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
		panel.setIcon(new ImageIcon(img4));
		contentPane.add(panel);
		
		JLabel lbl_bg = new JLabel("");
		lbl_bg.setIcon(new ImageIcon(img2));
		lbl_bg.setBounds(0, 1, 644, 570);
		contentPane.add(lbl_bg);
		
		
	}
}
