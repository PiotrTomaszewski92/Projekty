package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.SystemColor;
import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.JButton;

public class AddBilety extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	String GetButText (ButtonGroup gruppa) {
        for (Enumeration<AbstractButton> buttons = gruppa.getElements(); buttons.hasMoreElements();) {
            AbstractButton buttton = buttons.nextElement();

            if (buttton.isSelected()) {
                return buttton.getText();
            }
        }

        return null;
    }
	
	void SetButText (ButtonGroup gruppa, String ulga) {
        for (Enumeration<AbstractButton> buttons = gruppa.getElements(); buttons.hasMoreElements();) {
            AbstractButton buttton = buttons.nextElement();

            //if (buttton.isSelected()) {
            if (buttton.getText()==ulga) {
               buttton.setSelected(true);
            }
        }

       
    }
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					AddBilety frame = new AddBilety(string);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void UpdateTable(){
		try{
			String sql = "select * from bilety";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Create the frame.
	 */
	
	private static String string;
	Connection conn = null;
	private JTextField txtSprawd;
	private JTable table;
	private JTextPane potwierdzenie;
	private JLabel lblDodajBilet;
	private JTextField text_cena;
	private JTextField text_czas;
	
	private int stareId;
	
	public  AddBilety(String string) {
		conn=Polaczenie.dbConn();
		setBounds(100, 100, 690, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSprawd = new JTextField();
		txtSprawd.setColumns(10);
		txtSprawd.setBounds(369, 34, 272, 37);
		txtSprawd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					String tekst = txtSprawd.getText();
					//String query="select * from bilety";
					String query="select * from bilety where (czasTrwania like '%"+tekst+"%')  or (cena like '%"+tekst+"%') ";
					System.out.println(tekst);
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet res = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(res));
					pst.close();
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		contentPane.add(txtSprawd);
		
		JLabel lblWyszukajLinie = new JLabel("Wyszukaj bilet");
		lblWyszukajLinie.setBounds(369, 20, 107, 14);
		contentPane.add(lblWyszukajLinie);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(369, 134, 272, 243);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		potwierdzenie = new JTextPane();
		potwierdzenie.setText("Wprowad\u017A dane, by doda\u0107 bilet");
		potwierdzenie.setBackground(SystemColor.control);
		potwierdzenie.setBounds(369, 75, 272, 48);
		contentPane.add(potwierdzenie);
		
			
		//JOptionPane.showMessageDialog(null, string);
		
		UpdateTable();
		
		if(string=="add"){
			lblDodajBilet = new JLabel("DODAJ BILET");
			lblDodajBilet.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblDodajBilet.setBounds(10, 45, 233, 37);
			contentPane.add(lblDodajBilet);
			
			JLabel lblTypBiletu = new JLabel("Typ biletu");
			lblTypBiletu.setBounds(10, 109, 76, 14);
			contentPane.add(lblTypBiletu);
			
			JRadioButton rdbtnUlgowy = new JRadioButton("Ulgowy");
			rdbtnUlgowy.setBounds(20, 130, 109, 23);
			contentPane.add(rdbtnUlgowy);
			
			JRadioButton rdbtnNormalny = new JRadioButton("Normalny");
			rdbtnNormalny.setBounds(20, 156, 109, 23);
			contentPane.add(rdbtnNormalny);
			
			ButtonGroup grupa = new ButtonGroup();
			grupa.add(rdbtnUlgowy);
			grupa.add(rdbtnNormalny);
			
			JLabel lblCena = new JLabel("Cena");
			lblCena.setBounds(10, 205, 46, 14);
			contentPane.add(lblCena);
			
			text_cena = new JTextField();
			text_cena.setText("0,0");
			text_cena.setBounds(20, 230, 86, 20);
			contentPane.add(text_cena);
			text_cena.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("Czas trwania");
			lblNewLabel.setBounds(10, 280, 96, 14);
			contentPane.add(lblNewLabel);
			
			text_czas = new JTextField();
			text_czas.setText("0");
			text_czas.setBounds(20, 305, 86, 20);
			contentPane.add(text_czas);
			text_czas.setColumns(10);
			
			JButton btnDodaj = new JButton("DODAJ");
			btnDodaj.setBounds(154, 340, 205, 37);
			contentPane.add(btnDodaj);
			
			
			
			btnDodaj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String query2,number = text_cena.getText();
					double value;
					int czas;
					boolean ulga;
					
					if (number.length()==0) 
						JOptionPane.showMessageDialog(null, "Uzupe³nij pole z cen¹");
					else if ((grupa.getSelection())==null)
						JOptionPane.showMessageDialog(null, "Wybierz rodzaj biletu");
					else if((text_czas.getText()).length()==0)
						JOptionPane.showMessageDialog(null, "Wpisz wartoœæ. \nJeœli nie uwzglêdniany jest czas - wpisz 0");	
					else 
						{
						value = Double.parseDouble( number.replace(",",".") );
						czas = Integer.parseInt(text_czas.getText());
						if (GetButText (grupa)=="Ulgowy") ulga = true; else ulga=false;
						if (czas==0)
								query2="insert into bilety (id,ulgowy,cena) values(0,"+ulga+","+value+");";
							else
								query2="insert into bilety values(0,"+ulga+","+value+","+czas+");";
						try{
							
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
						
						System.out.println(GetButText (grupa)+", "+value+", "+czas);
					}
					
			}});
			
			
		
			
		}
		else if(string=="del"){
		potwierdzenie.setText("Wybierz z listy bilet do usuniêcia");
		
		JButton btnUsu = new JButton("USU\u0143");
		btnUsu.setBounds(231, 134, 107, 243);
		contentPane.add(btnUsu);
		btnUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(table.getSelectedRow());
				if(table.getSelectedRow()==-1){
					potwierdzenie.setText("Zaznacz przystanek");
					potwierdzenie.setForeground(Color.red);
				}else{
					int action = JOptionPane.showConfirmDialog(null, "Na pewno chcesz usun¹æ ten bilet?","Usuñ",JOptionPane.YES_NO_OPTION);
					if (action==0){
					try{
						
						int row = table.getSelectedRow();
						System.out.println("w try "+table.getValueAt(row, 0));
						long dana=(long) table.getValueAt(row, 0);
						//int dana = Integer.parseInt(  table.getValueAt(row, 0));
			            System.out.println("Usunie: "+dana);
						String query2="DELETE FROM bilety where id='"+(dana)+"' ";
						System.out.println(query2);
						PreparedStatement pst2 = conn.prepareStatement(query2);
						pst2.execute();
						potwierdzenie.setText("Usuniêto");
						potwierdzenie.setForeground(Color.green);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
						potwierdzenie.setText("Problem z usuniêciem, sprawdŸ po³¹czenie z baz¹ lub sprawdŸ czy ta linia ju¿ istnieje");
						potwierdzenie.setForeground(Color.red);
					}}
					UpdateTable();
				}
			}
		});
		
		
		
			
		}
		else {
		
		potwierdzenie.setText("Wybierz liniê by j¹ edytowaæ ");
		
		lblDodajBilet = new JLabel("Edytuj bilet");
		lblDodajBilet.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDodajBilet.setBounds(10, 45, 233, 37);
		contentPane.add(lblDodajBilet);
		
		JLabel lblTypBiletu = new JLabel("Typ biletu");
		lblTypBiletu.setBounds(10, 109, 76, 14);
		contentPane.add(lblTypBiletu);
		
		JRadioButton rdbtnUlgowy = new JRadioButton("Ulgowy");
		rdbtnUlgowy.setBounds(20, 130, 109, 23);
		contentPane.add(rdbtnUlgowy);
		
		JRadioButton rdbtnNormalny = new JRadioButton("Normalny");
		rdbtnNormalny.setBounds(20, 156, 109, 23);
		contentPane.add(rdbtnNormalny);
		
		ButtonGroup grupa = new ButtonGroup();
		grupa.add(rdbtnUlgowy);
		grupa.add(rdbtnNormalny);
		
		JLabel lblCena = new JLabel("Cena");
		lblCena.setBounds(10, 205, 46, 14);
		contentPane.add(lblCena);
		
		text_cena = new JTextField();
		text_cena.setText("0,0");
		text_cena.setBounds(20, 230, 86, 20);
		contentPane.add(text_cena);
		text_cena.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Czas trwania");
		lblNewLabel.setBounds(10, 280, 96, 14);
		contentPane.add(lblNewLabel);
		
		text_czas = new JTextField();
		text_czas.setText("0");
		text_czas.setBounds(20, 305, 86, 20);
		contentPane.add(text_czas);
		text_czas.setColumns(10);
		
		JButton btnDodaj_edit = new JButton("EDYTUJ");
		btnDodaj_edit.setBounds(154, 340, 205, 37);
		contentPane.add(btnDodaj_edit);
		
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
				String ulga;
				int roww = table.getSelectedRow();
				String AjDi = (table.getModel().getValueAt(roww, 0)).toString();
				String query="select * from bilety where id='"+AjDi+"' ";
				PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					if(Integer.parseInt(rs.getString("ulgowy"))==1)	ulga="Ulgowy"; else ulga="Normalny";
				stareId=Integer.parseInt(rs.getString("id"));	
				text_cena.setText(rs.getString("cena"));
				text_czas.setText(rs.getString("czasTrwania"));
				SetButText(grupa,ulga);
				}}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
				
				btnDodaj_edit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String query2,number = text_cena.getText();
						double value;
						int czas;
						boolean ulga;
						
						if (number.length()==0) 
							JOptionPane.showMessageDialog(null, "Uzupe³nij pole z cen¹");
						else if ((grupa.getSelection())==null)
							JOptionPane.showMessageDialog(null, "Wybierz rodzaj biletu");
						else if((text_czas.getText()).length()==0)
							JOptionPane.showMessageDialog(null, "Wpisz wartoœæ. \nJeœli nie uwzglêdniany jest czas - wpisz 0");	
						else 
							{
							value = Double.parseDouble( number.replace(",",".") );
							czas = Integer.parseInt(text_czas.getText());
							if (GetButText (grupa)=="Ulgowy") ulga = true; else ulga=false;
							if (czas==0)
									query2="UPDATE bilety SET ulgowy="+ulga+",cena="+value+" ,czasTrwania="+null+" where id="+stareId+";";
								else
									query2="UPDATE bilety SET ulgowy="+ulga+",cena="+value+",czasTrwania="+czas+" where id="+stareId+";";
							try{
								
								System.out.println(query2);
								PreparedStatement pst2 = conn.prepareStatement(query2);
								pst2.execute();
								potwierdzenie.setText("Zmieniono");
								potwierdzenie.setForeground(Color.green);
							}catch(Exception ex){
								potwierdzenie.setText("Problem z edytowaniem, sprawdŸ po³¹czenie z baz¹ lub sprawdŸ czy ta linia ju¿ istnieje");
								potwierdzenie.setForeground(Color.red);
							}
							UpdateTable();
							
							System.out.println(GetButText (grupa)+", "+value+", "+czas);
							}
					}
				});
				
			}
		});
		
		}
	}
}
