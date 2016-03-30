package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import login.Polaczenie;

import java.awt.SystemColor;

import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Rozklad extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Rozklad frame = new Rozklad(nr,pocz, kon);
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
	
	public void wypisz_godz(int id_l, int przyst_pocz, int przyst){
		try{
			String zapyt = "SELECT godzina FROM przystankiOdjazdy as po LEFT JOIN przystanki as p ON(po.przystanekId = p.id) WHERE po.odjazdId IN (SELECT po.odjazdId FROM `przystankiOdjazdy` as po LEFT JOIN `odjazdy` as o ON (po.odjazdId = o.id) WHERE po.przystanekId = '"+przyst_pocz+"' AND o.liniaId = '"+id_l+"' AND po.przystanekPoczatkowy = 1 ) and p.id = '"+przyst+"'";
			String zapyt_count = "SELECT count(godzina) as ile FROM przystankiOdjazdy as po LEFT JOIN przystanki as p ON(po.przystanekId = p.id) WHERE po.odjazdId IN (SELECT po.odjazdId FROM `przystankiOdjazdy` as po LEFT JOIN `odjazdy` as o ON (po.odjazdId = o.id) WHERE po.przystanekId = '"+przyst_pocz+"' AND o.liniaId = '"+id_l+"' AND po.przystanekPoczatkowy = 1 ) and p.id = '"+przyst+"'";
			
			PreparedStatement p_zap = conn.prepareStatement(zapyt);
			PreparedStatement p_zap_count = conn.prepareStatement(zapyt_count);
			
			ResultSet re_zap = p_zap.executeQuery();
			ResultSet re_zap_count = p_zap_count.executeQuery();
			
			re_zap_count.next();
			int ile = Integer.parseInt(re_zap_count.getString("ile"));
			//System.out.println();
			
			String[] wynik1 = null;
			wynik2= new String [ile][2];
			int i=0;
			
			//================================
			re_zap.next();
			String s = re_zap.getString("godzina");
			wynik1=(s).split(":");
			wynik2[i][0]=wynik1[0];
			wynik2[i][1]=wynik1[1];
			//================================
			
			while((re_zap.next())){
							
				s = re_zap.getString("godzina");
				wynik1=(s).split(":");
				
				if (wynik2[i][0].equals(wynik1[0]))
				{
					wynik2[i][1]=wynik2[i][1]+", "+wynik1[1];
				}
				else 
				{	
					++i;
					wynik2[i][0]=wynik1[0];
					wynik2[i][1]=wynik1[1];
				}
				
			}
		
			DefaultTableModel dtm = new DefaultTableModel(0, 0);
			String[] head = new String[] { "Godz.", "Minuty" };
			dtm.setColumnIdentifiers(head);

			int count = 0;
			//		while(wynik2[count][0]!=null){
			//			 dtm.addRow(new Object[] { wynik2[count][0], wynik2[count][1] });
			//			 count++;
			//		}
			while(count<12){
			System.out.println(wynik2[count][0]+" || "+wynik2[count][1]); count++;
			 dtm.addRow(new Object[] { wynik2[count][0], wynik2[count][1] });
			}
			
			scrollPane_1.setBounds(237, 165, 386, 330);
			contentPane.add(scrollPane_1);	
			
			table = new JTable();
			table.setEnabled(false);
			scrollPane_1.setViewportView(table);
			table.getTableHeader();
			table.setModel(dtm);
			table.getColumnModel().getColumn(0).setMaxWidth(55);
			
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "Yolo1"+e);
		}
		
	}
	
	public void wypisz_przystanki(String poczz){
				
		ajdi_p=id_przystanku(poczz);
		
		//najpierw pobierz pierwsz¹ godzine odjazdu dla przystanku pocz¹tkowego i danej linii
		try{
			String qqquery = "select przystankiodjazdy.godzina as hour from przystankiodjazdy inner join odjazdy on przystankiodjazdy.odjazdId=odjazdy.id where odjazdy.liniaId="+nr+" and odjazdy.przystanekPoczatkowy="+ajdi_p+" limit 1";
			PreparedStatement ppp = conn.prepareStatement(qqquery);
			ResultSet reee = ppp.executeQuery();
			reee.next();
			godzina = reee.getString("hour");
			
		}
		catch(Exception ex){
			//JOptionPane.showMessageDialog(null, "Yolo2"+ex);
		}
		
		//nastepnie wyszukaj przystanki na danej linii i wpisuje na liste
		DefaultListModel<String> tr = new DefaultListModel();
		try{
			String query = "select (select nazwa from przystanki where id = przystanekId) as nazwa from odjazdy right join przystankiodjazdy on odjazdy.id=przystankiodjazdy.odjazdId where liniaId="+nr+" and odjazdy.przystanekPoczatkowy="+ajdi_p+" and odjazdy.godzina='"+godzina+"'";
			PreparedStatement pp = conn.prepareStatement(query);
			ResultSet ree = pp.executeQuery();   
			while (ree.next()) {
				//System.out.println(ree.getString("nazwa"));
				String m=ree.getString("nazwa");
				tr.addElement(m);
				
			}
		}
		catch(Exception ex){
			//JOptionPane.showMessageDialog(null, "Yolo3"+ex);
		}finally{
			list.setModel(tr);
		}
		
	}
	
	private void nazwa_przystanku(int ajdi){
		try{
			String qq = "Select nazwa from przystanki where id="+ajdi+";";
			PreparedStatement p = conn.prepareStatement(qq);
			ResultSet re = p.executeQuery();
			re.next();
			System.out.print(re.getString("nazwa"));
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "Yolo4"+e);
		}
	}
	
	private void zainteresowanie(int ajdi){
		try{
			String query = "select count(liniaId) as ile from zanteresowanielinia where liniaId='"+ajdi+"'";
			PreparedStatement pp = conn.prepareStatement(query);
			ResultSet ree = pp.executeQuery(); 
			ree.next();
			if (Integer.parseInt(ree.getString("ile"))==0){
				String query2="insert into zanteresowanielinia (liniaId,stopienZainteresowania) values("+ajdi+",1);";
				PreparedStatement pst2 = conn.prepareStatement(query2);
				pst2.execute();
			}
			else{
				String query2 = "select stopienZainteresowania from zanteresowanielinia where liniaId='"+ajdi+"'";
				PreparedStatement pp2 = conn.prepareStatement(query2);
				ResultSet ree2 = pp2.executeQuery(); 
				ree2.next();
				int up =(Integer.parseInt(ree2.getString("stopienZainteresowania")))+1; 
				String query3="UPDATE zanteresowanieLinia SET stopienZainteresowania = '"+up+"' WHERE liniaId="+ajdi+";";
				PreparedStatement pst3 = conn.prepareStatement(query3);
				pst3.execute();
			}
		}
		catch(Exception ex){
		}
	}
	
	

	/**
	 * Create the frame.
	 */
	private static int nr;
	private int ajdi_p;
	private static String pocz, kon;
	Connection conn = null;
	private String godzina;
	private JTable table;
	private String[][] wynik2;
	private JList list = new JList();
	private JScrollPane scrollPane_1 = new JScrollPane();
	private int id_koncowa;
	private String nazwa_koncowa;
	
	
	public Rozklad(int nrr,String poczz, String konn) {
		//setDefaultCloseOperation(JFrame.);
		//..SwingUtilities.updateComponentTreeUI(JFrame);
		pocz=poczz;
		kon=konn;
		nr = nrr;
		System.out.println(nr+"  ||  "+poczz+" || "+konn);
		
		conn=Polaczenie.dbConn();
		setBounds(100, 100, 650, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		zainteresowanie(nrr);
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image img3 = new ImageIcon(this.getClass().getResource("/tlo.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/ngt8.png")).getImage();
		Image img5 = new ImageIcon(this.getClass().getResource("/bg_white.png")).getImage();
		Image img4 = new ImageIcon(this.getClass().getResource("/bg_top.png")).getImage();
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 165, 200, 330);
		contentPane.add(scrollPane);
		
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//System.out.println();
				String przy = (String) list.getSelectedValue();
				System.out.println(przy);
				wypisz_godz(nrr,ajdi_p,id_przystanku(przy));
				
				
			}
		});
		scrollPane.setViewportView(list);
		
		
	//=======================================
		wypisz_godz(nrr,id_przystanku(poczz),id_przystanku(poczz));
		// (id_linii, przystanek poczatkowy, przystanek)
		
	//=======================================
		
		
		
		
		JLabel lbl_zmien_kier = new JLabel();
		lbl_zmien_kier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			
				wypisz_przystanki(kon);
				String pom=pocz;
				pocz=kon;
				kon=pom;
				lbl_zmien_kier.setText(pocz);
		
			}
		});
		lbl_zmien_kier.setBounds(20, 143, 285, 14);
		lbl_zmien_kier.setText(pocz);
		contentPane.add(lbl_zmien_kier);
		
		JLabel lblZmieNa = new JLabel("Zmie\u0144 kierunek na:");
		lblZmieNa.setBounds(20, 129, 200, 14);
		contentPane.add(lblZmieNa);
		
		//=======================================
		
		JLabel lbl_Linia = new JLabel(""+nrr+"");
		lbl_Linia.setForeground(SystemColor.textHighlight);
		lbl_Linia.setFont(new Font("Trajan Pro 3", Font.BOLD, 24));
		lbl_Linia.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Linia.setBounds(237, 124, 200, 50);
		contentPane.add(lbl_Linia);
		
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
		
		wypisz_przystanki(poczz);
		
		
		
		//System.out.println("\n Ostatni index: "+list.getLastVisibleIndex());
	}
}
