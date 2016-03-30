package login;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class AddOdjazdy<comboBox_przystanek> extends JFrame {

	private JPanel contentPane;
	Connection conn = null;
	private JTable table;
	private JTextField textLinia;
	private JComboBox comboBox_przystanek;
	private String liniaa;
	private Component myTable;
	
	Object[][] data;
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			private String string;

			public void run() {
				try {
					AddOdjazdy frame = new AddOdjazdy(string);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void UpdateTable(String linia, int poczatkowy){
		try{
			String sql = "select liniaId as Nr, (select nazwa from przystanki where przystanki.id=przystankiodjazdy.przystanekId) as przystanek, GROUP_CONCAT( DISTINCT przystankiodjazdy.godzina order by przystankiodjazdy.godzina SEPARATOR ' | ') as czas from  przystankiodjazdy inner join odjazdy on odjazdy.id=przystankiodjazdy.odjazdId where `liniaId`='"+linia+"' and odjazdy.przystanekPoczatkowy='"+poczatkowy+"' group by przystanek  ORDER BY `czas`;";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
			TableColumnModel tcm = table.getColumnModel();
			tcm.getColumn(0).setMaxWidth(50);
			tcm.getColumn(1).setMaxWidth(150);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void fillComboBox(String linia){
		try{
			
			String query="select liniaId, przystankiodjazdy.przystanekId, (select nazwa from przystanki where przystankiodjazdy.przystanekId=przystanki.id) as poczatek  from odjazdy inner join przystankiodjazdy on odjazdy.id=przystankiodjazdy.odjazdId where (liniaId like '"+linia+"') and (przystankiodjazdy.przystanekPoczatkowy=1) group by przystankiodjazdy.przystanekId ORDER BY `odjazdy`.`id` ASC";
			//System.out.println(query);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet res = pst.executeQuery();
			while(res.next()){
				liniaa = res.getString("poczatek");
				comboBox_przystanek.addItem(liniaa);
				//System.out.println(liniaa);
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Create the frame.
	 */
	public AddOdjazdy(String string) {
		conn=Polaczenie.dbConn();
		setBounds(100, 100, 1100, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 62, 1025, 351);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(table);
		
		textLinia = new JTextField();
		textLinia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					String tekst = textLinia.getText();
					comboBox_przystanek.removeAllItems();
					fillComboBox(tekst);
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		textLinia.setBounds(106, 11, 149, 26);
		contentPane.add(textLinia);
		textLinia.setColumns(3);
		
		JLabel lblNrLinii = new JLabel("Nr. linii:");
		lblNrLinii.setBounds(50, 17, 46, 14);
		contentPane.add(lblNrLinii);
		
		JLabel lblPrzystanekPocztkowy = new JLabel("Przystanek pocz\u0105tkowy:");
		lblPrzystanekPocztkowy.setBounds(349, 17, 140, 14);
		contentPane.add(lblPrzystanekPocztkowy);
		
		comboBox_przystanek = new JComboBox();
		comboBox_przystanek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					System.out.println("Pobrano: "+comboBox_przystanek.getSelectedItem());
					int odbieram = 0;
					String pobierz_przystanek=(String) comboBox_przystanek.getSelectedItem();
					
					if(pobierz_przystanek != null){
						String query_przystanek="select id from przystanki where nazwa='"+pobierz_przystanek+"';";
						//System.out.println(query_przystanek);
						PreparedStatement pstt = conn.prepareStatement(query_przystanek);
						ResultSet ress = pstt.executeQuery();
						while(ress.next()){
							odbieram = ress.getInt("id");
							System.out.println("Wypisuje nr przystanku: "+odbieram);
						}
						
					}
					System.out.println("Nr linii: "+textLinia.getText()+", poczatkowy przystanek: "+odbieram);
					UpdateTable(textLinia.getText(),odbieram);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		comboBox_przystanek.setBounds(490, 14, 305, 26);
		contentPane.add(comboBox_przystanek);
		
			
		JOptionPane.showMessageDialog(null, string);
		
		//UpdateTable();
	}
}
