package login;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection conn =null;		//dodane
	private JTextField textField;
	private JPasswordField passwordField;
	public Login() {
		initialize();
		conn=Polaczenie.dbConn();	//dodane
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 254, 243);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogowanie = new JLabel("LOGOWANIE");
		lblLogowanie.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblLogowanie.setBounds(61, 11, 113, 14);
		frame.getContentPane().add(lblLogowanie);
		
		textField = new JTextField();
		textField.setBounds(79, 75, 133, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(79, 119, 133, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(23, 78, 46, 14);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblHaso = new JLabel("Has\u0142o");
		lblHaso.setBounds(23, 122, 46, 14);
		frame.getContentPane().add(lblHaso);
		
		JButton btnZaloguj = new JButton("ZALOGUJ");
		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query="select * from login where login=? and haslo=?";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, textField.getText());		//pobiera info z formularza i zamiast pytajników dodaje je do zapytania
					pst.setString(2, passwordField.getText());	
					
					ResultSet rs = pst.executeQuery();
					int count=0;
					while(rs.next()){
						count++;						
					}
					if(count==1){
						JOptionPane.showMessageDialog(null, "Zalogowano");
						frame.dispose();
						Administrator ap = new Administrator();
						ap.setVisible(true);
					}
					else if (count>1){
						JOptionPane.showMessageDialog(null, "Duplikat kont");
					}
					else {
						JOptionPane.showMessageDialog(null, "Nie poprawne dane");
					}
					rs.close();
					pst.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
				
				
			}
		});
		btnZaloguj.setBounds(123, 170, 89, 23);
		frame.getContentPane().add(btnZaloguj);
	}

	
}
