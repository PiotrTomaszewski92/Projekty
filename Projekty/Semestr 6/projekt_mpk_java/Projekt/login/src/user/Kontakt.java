package user;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import login.Polaczenie;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Kontakt extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Kontakt frame = new Kontakt();
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
	
	public static void wyslij_email (String nazw, String email, String textt) throws UnsupportedEncodingException{
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "...";
        
        String url = "http://tomaszew.com/mpk/api/contact.php?name=" + URLEncoder.encode(nazw, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8") + "&message=" + URLEncoder.encode(textt, "UTF-8");
        
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
        String ok = builder.toString();
        

    	JOptionPane.showMessageDialog(null, ok);

        /*try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("demon17@o2.pl", "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        }*/
		
	}
	
	static Connection conn = null;
	private JTextField textField;
	private JTextField textField_1;
	
	public Kontakt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		conn=Polaczenie.dbConn();
		setBounds(100, 100, 650, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		
		Image img3 = new ImageIcon(this.getClass().getResource("/tlo.png")).getImage();
		
		Image img2 = new ImageIcon(this.getClass().getResource("/ngt8.png")).getImage();
		
		Image img5 = new ImageIcon(this.getClass().getResource("/bg_white.png")).getImage();
		contentPane.setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(59, 273, 397, 20);
		contentPane.add(textField_1);
		
		textField = new JTextField();
		textField.setBounds(59, 217, 397, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(59, 337, 397, 121);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JButton btnNewButton = new JButton("WY\u015ALIJ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((textField.getText().isEmpty())||(textField_1.getText().isEmpty())||(textPane.getText().isEmpty())){
					JOptionPane.showMessageDialog(null, "Uzupe³nij wszystkie pola");
				}
				else{
					try {
						wyslij_email (textField.getText(),textField_1.getText(),textPane.getText());
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(new Color(57, 109, 135));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBounds(473, 336, 130, 121);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("KONTAKT");
		lblNewLabel.setForeground(new Color(0, 0, 102));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 143, 624, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-MAIL");
		lblNewLabel_1.setBounds(59, 248, 144, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblTreWiadomoci = new JLabel("TRE\u015A\u0106 WIADOMO\u015ACI");
		lblTreWiadomoci.setBounds(59, 310, 144, 14);
		contentPane.add(lblTreWiadomoci);
		
		JLabel lblImiINazwisko = new JLabel("IMI\u0118 I NAZWISKO");
		lblImiINazwisko.setBounds(59, 187, 144, 14);
		contentPane.add(lblImiINazwisko);
		
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
		Image img4 = new ImageIcon(this.getClass().getResource("/bg_top.png")).getImage();
		panel.setIcon(new ImageIcon(img4));
		contentPane.add(panel);
		
		JLabel lbl_bg = new JLabel("");
		lbl_bg.setIcon(new ImageIcon(img2));
		lbl_bg.setBounds(0, 1, 644, 570);
		contentPane.add(lbl_bg);
	}
}
