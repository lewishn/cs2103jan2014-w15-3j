import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.JTextArea;

import java.awt.*;
import java.awt.event.*;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TextField;
import java.awt.TextArea;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;


public class DoThingsGUI extends JFrame {

	private JPanel contentPane;
	private TextField textField;
	private TextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
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
	public DoThingsGUI() {
		
		setUndecorated(true);
		setType(Type.UTILITY);
		setForeground(Color.BLACK);
		setFont(new Font("Consolas", Font.BOLD, 14));
		setTitle("Do-things");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textField = new TextField();
		textField.setFont(new Font("Consolas", Font.PLAIN, 14));
		contentPane.add(textField, BorderLayout.SOUTH);
		
		textArea = new TextArea();
		textArea.setFocusTraversalKeysEnabled(false);
		textArea.setFocusable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Consolas", Font.BOLD, 14));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setBackground(Color.BLACK);
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);
		
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				int key = arg0.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){
					String text = textField.getText();
					
					String doThingsFeedback = DoThings.run(text);
					
					if(text.equalsIgnoreCase("exit")){
						System.exit(0);
					}
					
					if(text.equalsIgnoreCase("hide")){
						
					}
					
					String feedback = text + "\n";
					
					if(feedback.substring(0, 5).equals("ERROR")){
					    textField.selectAll();
					}else{ 
						textArea.setCaretPosition(textArea.getParent().getWidth());
						textArea.append(doThingsFeedback);
						textField.setText("");  
					}
				}
			}
		});
	}
}
