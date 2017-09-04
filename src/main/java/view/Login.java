package view;

import model.DBMS;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private DBMS dbms = new DBMS();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("Login");
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 237, 163);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("username :");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setForeground(Color.GREEN);
		lblUsername.setBackground(Color.WHITE);
		lblUsername.setBounds(10, 11, 82, 27);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("password :");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setForeground(Color.GREEN);
		lblPassword.setBounds(10, 51, 82, 27);
		contentPane.add(lblPassword);

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(102, 16, 98, 20);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				User user = new User(textFieldUserName.getText(), passwordField.getText());
				if (dbms.userExists(user)) {
					if (dbms.isPasswordCorrect(user)) {
						Login.this.setVisible(false);
						GUI.enter(user);
					}
					else
						JOptionPane.showMessageDialog(null, "wrong password");
				}
				else
					JOptionPane.showMessageDialog(null, "no such user");
			}
		});
		btnNewButton.setBounds(10, 90, 92, 23);
		contentPane.add(btnNewButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(102, 56, 98, 20);
		contentPane.add(passwordField);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("deprecation")
                User user= new User(textFieldUserName.getText(), passwordField.getText());
				if (dbms.userExists(user))
					JOptionPane.showMessageDialog(null, "this username is already taken");
				else {
					if (dbms.addUser(user))
						JOptionPane.showMessageDialog(null, user + " is successfully registered");
				}
			}
		});
		btnRegister.setBounds(102, 90, 98, 23);
		contentPane.add(btnRegister);
	}
}
