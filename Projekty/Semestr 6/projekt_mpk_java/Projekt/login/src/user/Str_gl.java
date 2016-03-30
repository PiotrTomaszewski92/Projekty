package user;
import login.Login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Str_gl extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Str_gl frame = new Str_gl();
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
	
	public Str_gl() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		
		Image img3 = new ImageIcon(this.getClass().getResource("/tlo.png")).getImage();
		
		Image img2 = new ImageIcon(this.getClass().getResource("/ngt8.png")).getImage();
		
		JLabel label = new JLabel("");		
		label.setIcon(new ImageIcon(img));
		label.setBounds(10, 1, 120, 111);		
		contentPane.add(label);
		
		JButton btn_l = new JButton("Zobacz rozk\u0142ad jazdy linii");
		btn_l.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Linie linie = new Linie("Linie");
					linie.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_l.setForeground(new Color(255, 255, 255));
		btn_l.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_l.setBackground(new Color(57, 109, 135));
		btn_l.setBorderPainted(false);
		btn_l.setBounds(165, 212, 315, 54);
		contentPane.add(btn_l);
		
		JButton btn_p = new JButton("Wyszukaj po\u0142\u0105czenie");
		btn_p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Pol pol = new Pol("Polaczenie");
					pol.setVisible(true);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btn_p.setBounds(165, 277, 315, 54);
		btn_p.setForeground(new Color(255, 255, 255));
		btn_p.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_p.setBackground(new Color(57, 109, 135));
		btn_p.setBorderPainted(false);
		contentPane.add(btn_p);
		
		JButton btn_k = new JButton("Skontaktuj si\u0119 z nami");
		btn_k.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Kontakt k = new Kontakt();
				k.setVisible(true);
			}
		});
		btn_k.setBounds(165, 342, 315, 54);
		btn_k.setForeground(new Color(255, 255, 255));
		btn_k.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_k.setBackground(new Color(57, 109, 135));
		btn_k.setBorderPainted(false);
		contentPane.add(btn_k);
		
		JLabel lbl_stopka = new JLabel("");
		lbl_stopka.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
				Login log = new Login();
				log.frame.setVisible(true);
					System.out.println("no siema!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblCreatedBy = new JLabel("Copyright\u00A9 2015 Tomaszewski, Sokulski, Szydlak, Pytlak");
		lblCreatedBy.setForeground(new Color(255, 255, 255));
		lblCreatedBy.setFont(new Font("Trajan Pro", Font.PLAIN, 11));
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedBy.setBounds(0, 521, 644, 50);
		contentPane.add(lblCreatedBy);
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
		lbl_bg.setBounds(0, 0, 644, 571);
		contentPane.add(lbl_bg);
	}
}
