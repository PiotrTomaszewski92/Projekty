package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.DropMode;

import java.awt.SystemColor;

public class AddLinie extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	private void UpdateTable(){
		try{
			String sql = "select * from linie";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					AddLinie frame = new AddLinie(string);
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
	Connection conn = null;
	private JTextField txtSprawd;
	private JTable table;
	private static String string;
	private JLabel lblNazwa;
	private JLabel lblTyp;
	private JTextField textField_add;
	private JButton btnUsu;
	private JTextPane txtpnUwagaPiotr;
	private JLabel lblZaznaczPrzystanekDo;
	
	private int stareId;
	
	public AddLinie(String string) {
		conn=Polaczenie.dbConn();
		setBounds(100, 100, 690, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//contentPane.setLayout(null);
		
		
		
		txtSprawd = new JTextField();
		txtSprawd.setBounds(381, 39, 272, 37);
		txtSprawd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					String tekst = txtSprawd.getText();
					String query="select * from linie where (id like '%"+tekst+"%') or (typ like '%"+tekst+"%') ";
					System.out.println(query);
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet res = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(res));
					pst.close();
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(txtSprawd);
		txtSprawd.setColumns(10);
		
		JLabel lblWyszukajLinie = new JLabel("Wyszukaj linie");
		lblWyszukajLinie.setBounds(381, 11, 107, 14);
		contentPane.add(lblWyszukajLinie);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(381, 120, 272, 250);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JTextPane potwierdzenie = new JTextPane();
		potwierdzenie.setBackground(SystemColor.control);
		potwierdzenie.setBounds(381, 87, 272, 19);
		contentPane.add(potwierdzenie);
		
		//JOptionPane.showMessageDialog(null, string);
		
		UpdateTable();
		if(string=="add"){
			JLabel lblDodajLinie = new JLabel("Dodaj linie");
			lblDodajLinie.setBounds(24, 11, 107, 37);
			lblDodajLinie.setFont(new Font("Tahoma", Font.PLAIN, 20));
			contentPane.add(lblDodajLinie);
			
			lblNazwa = new JLabel("Numer");
			lblNazwa.setBounds(24, 105, 46, 14);
			lblNazwa.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPane.add(lblNazwa);
			
			lblTyp = new JLabel("Typ");
			lblTyp.setBounds(24, 145, 46, 19);
			lblTyp.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPane.add(lblTyp);
			
			textField_add = new JTextField();
			textField_add.setBounds(82, 99, 86, 31);
			contentPane.add(textField_add);
			textField_add.setColumns(10);
			
			JComboBox comboBox_add = new JComboBox();
			comboBox_add.setBounds(80, 146, 168, 31);
			comboBox_add.setModel(new DefaultComboBoxModel(new String[] {"autobus", "tramwaj"}));
			contentPane.add(comboBox_add);
			
			
			potwierdzenie.setText("Podaj numer i typ linii");
			
			
			JButton btnDodaj_add = new JButton("DODAJ");
			btnDodaj_add.setBounds(159, 213, 89, 23);
			btnDodaj_add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String ajdi = textField_add.getText();
					String combox = (String) comboBox_add.getSelectedItem();
					//int intAjdi = Integer.parseInt(ajdi);
					
					if ((ajdi.equals(""))||(combox.equals(""))){
						 potwierdzenie.setText("Uzupe³nij wszystkie pola");
						 potwierdzenie.setForeground(Color.red);
					}
					else{
						try{
							String query2="insert into linie values("+Integer.parseInt(ajdi)+",'"+combox+"')";
							System.out.println(query2);
							PreparedStatement pst2 = conn.prepareStatement(query2);
							pst2.execute();
							potwierdzenie.setText("Dodano");
							potwierdzenie.setForeground(Color.green);
						}catch(Exception ex){
							potwierdzenie.setText("Problem z dodaniem, sprawdŸ po³¹czenie z baz¹ lub sprawdŸ czy ta linia ju¿ istnieje");
							potwierdzenie.setForeground(Color.red);
						}
						UpdateTable();
					}
					
				}
			});
			contentPane.add(btnDodaj_add);
		}
	
		else if(string=="del"){

			txtpnUwagaPiotr = new JTextPane();
			txtpnUwagaPiotr.setForeground(Color.RED);
			txtpnUwagaPiotr.setText("UWAGA PIOTR \r\nLinie s\u0105 po\u0142\u0105czone ze sob\u0105 z innymi tabelami. uwa\u017Caj co usuwasz,by\u015B nie zepsu\u0142 bazy\r\n");
			txtpnUwagaPiotr.setBounds(10, 35, 118, 335);
			contentPane.add(txtpnUwagaPiotr);
		
			btnUsu = new JButton("USU\u0143");
			btnUsu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(table.getSelectedRow());
					if(table.getSelectedRow()==-1){
						potwierdzenie.setText("Zaznacz przystanek");
						potwierdzenie.setForeground(Color.red);
					}else{
						int action = JOptionPane.showConfirmDialog(null, "Na pewno chcesz usun¹æ t¹ linie?","Usuñ",JOptionPane.YES_NO_OPTION);
						if (action==0){
						try{
							
							int row = table.getSelectedRow();
							int dana = Integer.parseInt( (String) table.getValueAt(row, 0));
				            System.out.println("Usunie: "+dana);
							String query2="DELETE FROM linie where id='"+(dana)+"' ";
							System.out.println(query2);
							PreparedStatement pst2 = conn.prepareStatement(query2);
							pst2.execute();
							potwierdzenie.setText("Usuniêto");
							potwierdzenie.setForeground(Color.green);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, ex);
						}}
						UpdateTable();
					}
				}
			});
			btnUsu.setBounds(193, 39, 161, 327);
			contentPane.add(btnUsu);
			
			potwierdzenie.setText("Zaznacz przystanek do usuni\u0119cia");
			potwierdzenie.setBounds(381, 87, 272, 24);
			
		}
		else {
			
			
			JLabel lblDodajLinie = new JLabel("Edytuj linie");
			lblDodajLinie.setBounds(24, 11, 107, 37);
			lblDodajLinie.setFont(new Font("Tahoma", Font.PLAIN, 20));
			contentPane.add(lblDodajLinie);
			
			lblNazwa = new JLabel("Numer");
			lblNazwa.setBounds(24, 105, 46, 14);
			lblNazwa.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPane.add(lblNazwa);
			
			lblTyp = new JLabel("Typ");
			lblTyp.setBounds(24, 145, 46, 19);
			lblTyp.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPane.add(lblTyp);
			
			textField_add = new JTextField();
			textField_add.setBounds(82, 99, 86, 31);
			contentPane.add(textField_add);
			textField_add.setColumns(10);
			
			JComboBox comboBox_add = new JComboBox();
			comboBox_add.setBounds(80, 146, 168, 31);
			comboBox_add.setModel(new DefaultComboBoxModel(new String[] {"autobus", "tramwaj"}));
			contentPane.add(comboBox_add);
			
			potwierdzenie.setBounds(381, 77, 272, 24);
			potwierdzenie.setText("Wybierz liniê z listy ");
			contentPane.add(potwierdzenie);
			
			JButton btnDodaj_edit = new JButton("Aktualizuj");
			btnDodaj_edit.setBounds(159, 213, 89, 23);
			contentPane.add(btnDodaj_edit);
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try{
					int roww = table.getSelectedRow();
					String AjDi = (table.getModel().getValueAt(roww, 0)).toString();
					String query="select * from linie where id='"+AjDi+"' ";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()){
					stareId=Integer.parseInt(rs.getString("id"));
					textField_add.setText(rs.getString("id"));
					//comboBox_add.setText(rs.getString("typ"));
					comboBox_add.setSelectedItem(rs.getString("typ"));
					}}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}
			});
			
			btnDodaj_edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String ajdi = textField_add.getText();
					String combox = (String) comboBox_add.getSelectedItem();
					if ((ajdi.equals(""))||(combox.equals(""))){
						 potwierdzenie.setText("Uzupe³nij wszystkie pola");
						 potwierdzenie.setForeground(Color.red);
					}
					else{
						try{
							String query2 =  "UPDATE linie SET id = '"+ajdi+"', typ='"+combox+"' WHERE id = '"+stareId+"' ";
							System.out.println(query2);
							PreparedStatement pst2 = conn.prepareStatement(query2);
							pst2.execute();
							potwierdzenie.setText("Zaktualizowano");
							potwierdzenie.setForeground(Color.green);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, ex);
						}
						UpdateTable();
					}
				}
			});
		
		}
		
	}
}
