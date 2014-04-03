import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.border.EmptyBorder;


public class DoThingsGUI extends JFrame {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	
	private JPanel contentPane;
	private JTextField textField;
	private JLabel headingLabel;
	private JPanel textPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
					frame.setVisible(true);
					frame.textArea.setText(MESSAGE_STARTUP);  
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
		setForeground(Color.BLACK);
		setFont(new Font("Consolas", Font.BOLD, 14));
		setTitle("Do-things");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setBounds(100, 100, 320, 600);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setToolTipText("");
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		headingLabel = new JLabel("Do-Things");
		headingLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		headingLabel.setOpaque(true);
		headingLabel.setBackground(new Color(255, 204, 0));
		headingLabel.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 22));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(0, 0, 320, 76);
		contentPane.add(headingLabel);
		
		textField = new JTextField();
		textField.setOpaque(false);
		textField.setBounds(10, 75, 300, 41);
		textField.setBorder(null);
		textField.setBackground(new Color(255, 255, 255));
		textField.setForeground(new Color(0, 0, 0));
		textField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		contentPane.add(textField);
		
		textPanel = new JPanel();
		textPanel.setBackground(SystemColor.window);
		textPanel.setBounds(0, 75, 320, 41);
		contentPane.add(textPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(null);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(0, 115, 320, 485);
		contentPane.add(scrollPane);
		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(SystemColor.windowBorder);
		backgroundPanel.setBounds(0, 0, 320, 600);
		contentPane.add(backgroundPanel);
		
			
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				int key = arg0.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){
					String text = textField.getText();
					Feedback feedback = DoThings.readCommand(text);
					
					
					if(feedback.getExitFlag()){
						System.exit(0);
					} 
					/*
					else if(doThingsFeedback.equalsIgnoreCase(COMMAND_HIDE)) {
						
					} else if(doThingsFeedback.contains("ERROR")) {
						textField.selectAll();
						textArea.append(doThingsFeedback);
					}
					*/
					else{ 
						textArea.append(feedback.toString());
						textField.setText("");  
					}
					
				}
			}
		});
		
	}
}
