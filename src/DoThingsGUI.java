import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class DoThingsGUI extends JFrame {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	
	private JPanel contentPane;
	private JTextField inputField;
	private JLabel headingLabel;
	private JPanel textPanel;
	private JLabel feedbackLabel;
	private final JPanel taskPanel = new JPanel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
					frame.setVisible(true);
					frame.feedbackLabel.setText(MESSAGE_STARTUP);  
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
		
		feedbackLabel = new JLabel("");
		feedbackLabel.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 14));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBounds(10, 47, 300, 29);
		contentPane.add(feedbackLabel);
		
		headingLabel = new JLabel("Do-Things");
		headingLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		headingLabel.setOpaque(true);
		headingLabel.setBackground(new Color(255, 204, 0));
		headingLabel.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 22));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(0, 0, 320, 76);
		contentPane.add(headingLabel);
		
		inputField = new JTextField();
		inputField.setOpaque(false);
		inputField.setBounds(10, 75, 300, 41);
		inputField.setBorder(null);
		inputField.setBackground(new Color(255, 255, 255));
		inputField.setForeground(new Color(0, 0, 0));
		inputField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		contentPane.add(inputField);
		
		textPanel = new JPanel();
		textPanel.setBackground(SystemColor.window);
		textPanel.setBounds(0, 75, 320, 41);
		contentPane.add(textPanel);
		taskPanel.setBounds(0, 116, 320, 484);
		contentPane.add(taskPanel);
		taskPanel.setOpaque(false);
		taskPanel.setLayout(null);
		
		
		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(SystemColor.windowBorder);
		backgroundPanel.setBounds(0, 0, 320, 600);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
			
		inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				int key = arg0.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){
					ArrayList<String>input = new ArrayList<String>();
					input.add("hello");
					JPanel messagePanel[] = new JPanel[input.size()];
					for(int i=0; i<input.size(); i++) {
											
												
						//----- one task ----//
					
						messagePanel.clone()[.setBorder(new LineBorder(new Color(153, 153, 204)));
						messagePanel.setBackground(new Color(255, 255, 255));
						messagePanel.setBounds(0, 0, 320, 116);
						taskPanel.add(messagePanel);
						messagePanel.setLayout(null);
						
						Label startDate = new Label("22/04/2014");
						startDate.setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 16));
						startDate.setBounds(222, 10, 88, 27);
						messagePanel.add(startDate);
						
						Label startTime = new Label("StartTime");
						startTime.setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
						startTime.setBounds(222, 32, 88, 22);
						messagePanel.add(startTime);
						
						Label endDate = new Label("22/04/2014");
						endDate.setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 16));
						endDate.setBounds(222, 60, 88, 27);
						messagePanel.add(endDate);
						
						Label endTime = new Label("End Time");
						endTime.setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
						endTime.setBounds(222, 84, 88, 22);
						messagePanel.add(endTime);
						
						JTextArea taskDescription = new JTextArea();
						taskDescription.setFont(new Font("Pluto Sans Thin", Font.PLAIN, 14));
						taskDescription.setLineWrap(true);
						taskDescription.setWrapStyleWord(true);
						taskDescription.setBounds(10, 11, 206, 95);
						messagePanel.add(taskDescription);
						taskDescription.setEditable(false);
						//----------//
						taskDescription.append(input.get(i));
					}
					//String text = textField.getText();
					//taskDescription.append(text);
					/*Feedback feedback = DoThings.readCommand(text);
					
					
					if(feedback.getExitFlag()){
						System.exit(0);
					} */
					/*
					else if(doThingsFeedback.equalsIgnoreCase(COMMAND_HIDE)) {
						
					} else if(doThingsFeedback.contains("ERROR")) {
						textField.selectAll();
						textArea.append(doThingsFeedback);
					}
					*//*
					else{ 
						TaskDescription.append(feedback.toString());
						textField.setText("");  
					}
					*/
				}
			}
		});
		
	}
}
