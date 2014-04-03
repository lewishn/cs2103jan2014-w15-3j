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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.ScrollPane;


public class DoThingsGUI extends JFrame {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	
	private JPanel contentPane;
	private JTextField inputField;
	private JLabel headingLabel;
	private JPanel textPanel;
	private JLabel feedbackLabel;
	private ArrayList<String>input = new ArrayList<String>();
	private JScrollPane taskPanel;
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
		
		taskPanel = new JScrollPane();
		taskPanel.setOpaque(false);
		taskPanel.setBounds(0, 115, 320, 485);
		contentPane.add(taskPanel);
		
		
		
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
					
					String userInput = inputField.getText();
					input.add(userInput);
					inputField.setText("");  
					JPanel messagePanel[] = new JPanel[input.size()];
					Label startDate[] = new Label[input.size()];
					Label startTime[] = new Label[input.size()];
					Label endDate[] = new Label[input.size()];
					Label endTime[] = new Label[input.size()];
					JTextArea taskDescription[] = new JTextArea[input.size()];
					int change=0;
					for(int i=0; i<input.size(); i++) {	
						//----- one task ----//
						messagePanel[i] = new JPanel();
						messagePanel[i].setBorder(new LineBorder(new Color(153, 153, 204)));
						messagePanel[i].setBackground(new Color(255, 255, 255));
						messagePanel[i].setBounds(0, 0+change, 320, 116);
						taskPanel.add(messagePanel[i]);
						messagePanel[i].setLayout(null);
						
						startDate[i] = new Label("22/04/2014");
						startDate[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 16));
						startDate[i].setBounds(222, 10, 88, 27);
						messagePanel[i].add(startDate[i]);
						
						startTime[i] = new Label("StartTime");
						startTime[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
						startTime[i].setBounds(222, 32, 88, 22);
						messagePanel[i].add(startTime[i]);
						
						endDate[i] = new Label("22/04/2014");
						endDate[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 16));
						endDate[i].setBounds(222, 60, 88, 27);
						messagePanel[i].add(endDate[i]);
						
						endTime[i] = new Label("End Time");
						endTime[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
						endTime[i].setBounds(222, 84, 88, 22);
						messagePanel[i].add(endTime[i]);
						
						taskDescription[i] = new JTextArea();
						taskDescription[i].setFont(new Font("Pluto Sans Thin", Font.PLAIN, 14));
						taskDescription[i].setLineWrap(true);
						taskDescription[i].setWrapStyleWord(true);
						taskDescription[i].setBounds(10, 11, 206, 95);
						messagePanel[i].add(taskDescription[i]);
						taskDescription[i].setEditable(false);
						//----------//
						taskDescription[i].append(input.get(i));
						change +=116;
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
