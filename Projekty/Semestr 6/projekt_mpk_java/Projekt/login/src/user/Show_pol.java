package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import login.Polaczenie;

import org.json.*;

public class Show_pol extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Show_pol frame = new Show_pol(od,doo,czas);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
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
	
	private int id_przystanku(String nazwa){
		int ajdi=0;
		try{
			String qq = "Select id from przystanki where nazwa='"+nazwa+"';";
			PreparedStatement p = conn.prepareStatement(qq);
			ResultSet re = p.executeQuery();
			re.next();
			ajdi=Integer.parseInt(re.getString("id"));
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "Yolo0"+e);
		}
		return ajdi;
	}
	
	

	private static String od, doo, czas;
	Connection conn = null;
	/**
	 * Create the frame.
	 */
	public Show_pol(String odd, String dooo, String czass) {
		od=odd;
		doo=dooo;
		czas=czass;
		//setDefaultCloseOperation(Pol.setVisible(true););
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image img3 = new ImageIcon(this.getClass().getResource("/tlo.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/ngt8.png")).getImage();
		Image img5 = new ImageIcon(this.getClass().getResource("/bg_white.png")).getImage();
		Image img4 = new ImageIcon(this.getClass().getResource("/bg_top.png")).getImage();

		conn=Polaczenie.dbConn();
		
		System.out.println(odd+" -> "+id_przystanku(odd)+" || "+dooo+" -> "+id_przystanku(dooo)+" || "+czass);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPrzystanekPocztkowy = new JLabel("Przystanek pocz\u0105tkowy: ");
		lblPrzystanekPocztkowy.setBounds(30, 151, 182, 14);
		contentPane.add(lblPrzystanekPocztkowy);
		
		JLabel lblPrzystanekKocowy = new JLabel("Przystanek ko\u0144cowy:");
		lblPrzystanekKocowy.setBounds(30, 183, 182, 14);
		contentPane.add(lblPrzystanekKocowy);
		
		JLabel lbl_p1 = new JLabel("");
		lbl_p1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lbl_p1.setBounds(222, 151, 233, 14);
		contentPane.add(lbl_p1);
		
		JLabel lbl_p2 = new JLabel("");
		lbl_p2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lbl_p2.setBounds(222, 183, 233, 14);
		contentPane.add(lbl_p2);
		
		JLabel lbl_godz = new JLabel("");
		lbl_godz.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lbl_godz.setBounds(513, 170, 103, 14);
		contentPane.add(lbl_godz);
		
		JLabel lblGodzinaOdjazdu = new JLabel("Godzina odjazdu");
		lblGodzinaOdjazdu.setBounds(513, 151, 103, 14);
		contentPane.add(lblGodzinaOdjazdu);
		
		
		
		//ustawienie tekstow do labeli
		lbl_p1.setText(odd);
		lbl_p2.setText(dooo);
		lbl_godz.setText(czass);
		
		try {
			String json = this.getJSON(odd, doo, czas);
			JSONObject obj = new JSONObject(json);
	        
	        int status = obj.getInt("status");
	        int top = 223;
	        if( status == 1 ) {
	        	JSONArray data = obj.getJSONArray("data");
	        	for (int i = 0; i < data.length(); i++) {
	        		String text = "Linia: ";
	        		JSONArray lines = data.getJSONObject(i).getJSONArray("lines");
	        		System.out.println(lines);
	        		for(int j=0; j < lines.length(); j++) {
	        			if( j > 0 ) {
	        				text += " -> ";
	        			}
	        			text += lines.getString(j);
	        		}
	        		text += "     Godzina: ";
	        		text += data.getJSONObject(i).getString("hours");
	        		text += "     Czas podró¿y: ";
	        		text += data.getJSONObject(i).getString("time");
	        		
	        		JLabel nowaLabelka = new JLabel(text);
	        		nowaLabelka.setBounds(140, top, 382, 14);
	        		contentPane.add(nowaLabelka);
	        		
	        		JSONObject currentData = data.getJSONObject(i);
	        		
	        		JButton btnMore = new JButton("Szczegó³y");
	        		btnMore.addActionListener(new ActionListener() {
	        			public void actionPerformed(ActionEvent e) {
	        				try{
	        					Szczegoly_pol more = new Szczegoly_pol(currentData, odd, doo);
	        					more.setVisible(true);
	        				} catch (Exception ex) {
	        					ex.printStackTrace();
	        				}
	        			}
	        		});
	        		btnMore.setForeground(new Color(255, 255, 255));
	        		btnMore.setFont(new Font("Tahoma", Font.BOLD, 9));
	        		btnMore.setBackground(new Color(57, 109, 135));
	        		btnMore.setBorderPainted(false);
	        		btnMore.setBounds(30, top-3, 100, 20);
	        		contentPane.add(btnMore);
	        		
	        		top+=50;
	        	}
	        } else {
	        	//JOptionPane.showMessageDialog(null, obj.getString("error"));
	        }
	        
	        
	        
		} catch(Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Nie uda³o siê pobraæ po³¹czenia...");
		}
		
		//=============end============
		
		
		
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
	
	public String getJSON(String start, String end, String time) throws UnsupportedEncodingException {
		String url = "http://tomaszew.com/mpk/api/api.php?from=" + URLEncoder.encode(start, "UTF-8") + "&to=" + URLEncoder.encode(end, "UTF-8") + "&time=" + URLEncoder.encode(time, "UTF-8") + "&limit=5";
        System.out.println(url);
		URL u;
        StringBuilder builder = new StringBuilder();
        try {
            u = new URL(url);
            try {
                BufferedReader theHTML = new BufferedReader(new InputStreamReader(u.openStream(), "UTF8"));
                String thisLine;
                while ((thisLine = theHTML.readLine()) != null) {
                    builder.append(thisLine).append("\n");
                } 
            } 
            catch (Exception e) {
                System.err.println(e);
            }
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parseable URL");
            System.err.println(e);
        }
        String json = builder.toString();
        
        return json;
    }
}
