package login;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.DropMode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddPrzystanek extends Administrator {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	
	private void UpdateTable(){
		try{
			String sql = "select * from przystanki";
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
					AddPrzystanek frame = new AddPrzystanek(string);
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
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblNazwa;
	private JTextField textNazwa;
	private JLabel lblWsprzdnaX;
	private JLabel lblWsprzdnaY;
	private JTextField textX;
	private JTextField textY;
	private JLabel potwierdzenie;
	private JLabel lblNewLabel;
	private static String string;
	private JButton btnUsu;
	private JLabel lblZaznaczPrzystanekDo;
	
	public AddPrzystanek(String string) throws SQLException {
		this.string = string;
		conn=Polaczenie.dbConn();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*JLabel lblSprawdIstniejcePrzystanki = new JLabel("Sprawd\u017A istniej\u0105ce przystanki");
		lblSprawdIstniejcePrzystanki.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSprawdIstniejcePrzystanki.setBounds(38, 11, 268, 34);
		contentPane.add(lblSprawdIstniejcePrzystanki);
		*/
		//JOptionPane.showMessageDialog(null, string);
		
		txtSprawd = new JTextField();
		txtSprawd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{
					String tekst = txtSprawd.getText();
					String query="select * from przystanki where (nazwa like '%"+tekst+"%') or (x like '%"+tekst+"%') or (y like '%"+tekst+"%') ";
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
		
		txtSprawd.setEnabled(true);
		txtSprawd.setDropMode(DropMode.INSERT);
		txtSprawd.setBounds(318, 11, 309, 34);
		contentPane.add(txtSprawd);
		txtSprawd.setColumns(10);
		
		panel = new JPanel();
		panel.setBounds(38, 130, 589, 281);
		contentPane.add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 589, 281);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 589, 281);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(0, 0, 589, 281);
		panel_2.add(scrollPane);
		
		table = new JTable();
		
		scrollPane.setViewportView(table);
		
		try{
			String query="select * from przystanki";
			System.out.println(query);
			PreparedStatement pst = conn.prepareStatement(query);
			//pst.setString(1,txtSprawd.getText());
			ResultSet res = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
			
			//================add============================
			if(string=="add"){
			lblNazwa = new JLabel("Nazwa");
			lblNazwa.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNazwa.setBounds(38, 56, 59, 20);
			contentPane.add(lblNazwa);
			
			textNazwa = new JTextField();
			textNazwa.setBounds(107, 56, 163, 20);
			contentPane.add(textNazwa);
			textNazwa.setColumns(10);
			
			lblWsprzdnaX = new JLabel("Wsp\u00F3\u0142rz\u0119dna X");
			lblWsprzdnaX.setBounds(38, 87, 98, 14);
			contentPane.add(lblWsprzdnaX);
			
			lblWsprzdnaY = new JLabel("Wsp\u00F3\u0142rz\u0119dna Y");
			lblWsprzdnaY.setBounds(38, 105, 98, 14);
			contentPane.add(lblWsprzdnaY);
			
			textX = new JTextField();
			textX.setBounds(138, 85, 132, 17);
			textX.setText("0");
			contentPane.add(textX);
			textX.setColumns(10);
			
			textY = new JTextField();
			textY.setColumns(10);
			textY.setText("0");
			textY.setBounds(138, 103, 132, 17);
			contentPane.add(textY);
			
			JButton btnNewButton = new JButton("DODAJ");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
						String textttN = textNazwa.getText();
						String textttX = textX.getText();
						String textttY = textY.getText();
						if ((textttN.equals(""))||(textttX.equals(""))||(textttY.equals(""))){
							//JOptionPane.showMessageDialog(null, "Uzupe³nij wszystkie pola");
							 potwierdzenie.setText("Uzupe³nij wszystkie pola");
							 potwierdzenie.setForeground(Color.red);
						}
						
						
						else{
							try{
								String query2="insert into przystanki values(0,'"+textttN+"', "+Double.parseDouble(textttX)+", "+Double.parseDouble(textttY)+")";
								System.out.println(query2);
								PreparedStatement pst2 = conn.prepareStatement(query2);
								pst2.execute();
								potwierdzenie.setText("Dodano");
								potwierdzenie.setForeground(Color.green);
							}catch(Exception ex){
								JOptionPane.showMessageDialog(null, ex);
							}
							UpdateTable();
						}
					
				}
			});
			btnNewButton.setBounds(318, 56, 89, 63);
			contentPane.add(btnNewButton);
			
			}
			//======================del======================
			else if(string=="del"){
			btnUsu = new JButton("Usu\u0144");
			btnUsu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(table.getSelectedRow());
					if(table.getSelectedRow()==-1){
						potwierdzenie.setText("Zaznacz przystanek");
						potwierdzenie.setForeground(Color.red);
					}else{
						int action = JOptionPane.showConfirmDialog(null, "Na pewno chcesz usun¹æ ten przystanek?","Usuñ",JOptionPane.YES_NO_OPTION);
						if (action==0){
						try{
							
							int row = table.getSelectedRow();
				           // int col = table.getSelectedColumn();
				            long dana = (long) table.getValueAt(row, 0);
				            //System.out.println("row: "+row+" col: "+col+" dana: "+table.getValueAt(row, 0));
							String query2="DELETE FROM przystanki where id='"+(dana)+"' ";
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
			btnUsu.setBounds(129, 96, 89, 23);
			contentPane.add(btnUsu);
			
			lblZaznaczPrzystanekDo = new JLabel("Zaznacz przystanek do usuni\u0119cia");
			lblZaznaczPrzystanekDo.setFont(new Font("Tahoma", Font.ITALIC, 17));
			lblZaznaczPrzystanekDo.setBounds(38, 56, 294, 29);
			contentPane.add(lblZaznaczPrzystanekDo);
			}
			//====================end_del====================
			
			//====================aktualizuj=================
			else{
				lblNazwa = new JLabel("Nazwa");
				lblNazwa.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblNazwa.setBounds(38, 56, 59, 20);
				contentPane.add(lblNazwa);
				
				textNazwa = new JTextField();
				textNazwa.setBounds(107, 56, 163, 20);
				contentPane.add(textNazwa);
				textNazwa.setColumns(10);
				
				lblWsprzdnaX = new JLabel("Wsp\u00F3\u0142rz\u0119dna X");
				lblWsprzdnaX.setBounds(38, 87, 98, 14);
				contentPane.add(lblWsprzdnaX);
				
				lblWsprzdnaY = new JLabel("Wsp\u00F3\u0142rz\u0119dna Y");
				lblWsprzdnaY.setBounds(38, 105, 98, 14);
				contentPane.add(lblWsprzdnaY);
				
				textX = new JTextField();
				textX.setBounds(138, 85, 132, 17);
				contentPane.add(textX);
				textX.setColumns(10);
				
				textY = new JTextField();
				textY.setColumns(10);
				textY.setBounds(138, 103, 132, 17);
				contentPane.add(textY);
				
				JButton btnNewButton = new JButton("AKTUALIZUJ");
				btnNewButton.setBounds(318, 56, 109, 63);
				contentPane.add(btnNewButton);
				
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String textttN = textNazwa.getText();
						String textttX = textX.getText();
						String textttY = textY.getText();
						if ((textttN.equals(""))||(textttX.equals(""))||(textttY.equals(""))){
							//JOptionPane.showMessageDialog(null, "Uzupe³nij wszystkie pola");
							 potwierdzenie.setText("Uzupe³nij wszystkie pola");
							 potwierdzenie.setForeground(Color.red);
						}
						else{
							try{
								//String query2="insert into przystanki values(0,'"+textttN+"', "+Double.parseDouble(textttX)+", "+Double.parseDouble(textttY)+")";
								String query2 =  "UPDATE przystanki SET nazwa = '"+textttN+"', x='"+textttX+"', y='"+textttY+"' WHERE ID = '"+(table.getModel().getValueAt(table.getSelectedRow(), 0)).toString()+"' ";
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
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try{
					int roww = table.getSelectedRow();
					String AjDi = (table.getModel().getValueAt(roww, 0)).toString();
					String query="select * from przystanki where id='"+AjDi+"' ";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()){
					textNazwa.setText(rs.getString("nazwa"));
					textX.setText(rs.getString("x"));
					textY.setText(rs.getString("y"));
					}}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}
			});
			
			}
			//====================end_aktualizuj====================
			
			potwierdzenie = new JLabel("");
			potwierdzenie.setFont(new Font("Tahoma", Font.PLAIN, 15));
			potwierdzenie.setBounds(437, 67, 188, 34);
			contentPane.add(potwierdzenie);
			
			lblNewLabel = new JLabel("Wyszukaj przystanek");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNewLabel.setBounds(38, 11, 232, 34);
			contentPane.add(lblNewLabel);
			
			
			
			
			pst.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	
}
